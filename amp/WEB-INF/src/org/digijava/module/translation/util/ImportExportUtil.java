package org.digijava.module.translation.util;

import java.io.File;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.digijava.kernel.entity.Message;
import org.digijava.kernel.exception.DgException;
import org.digijava.kernel.persistence.PersistenceManager;
import org.digijava.kernel.persistence.WorkerException;
import org.digijava.kernel.translator.TranslatorWorker;
import org.digijava.kernel.translator.util.TrnUtil;
import org.digijava.module.admin.util.DbUtil;
import org.digijava.module.aim.exception.AimException;
import org.digijava.module.translation.action.ImportExportTranslations;
import org.digijava.module.translation.entity.MessageGroup;
import org.digijava.module.translation.importexport.ImportExportOption;
import org.digijava.module.translation.importexport.ImportType;
import org.digijava.module.translation.importexport.TranslationSearcher;
import org.digijava.module.translation.jaxb.Language;
import org.digijava.module.translation.jaxb.Translations;
import org.digijava.module.translation.jaxb.Trn;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Utilities related to translation import and export.
 * Initially XML tags were named not very well but basic idea is that
 * translations are grouped by key - for each key we may have several records 
 * for different languages. Lets define in more details:
 * In XML there is root tag called {@link Translations} and it contains
 * groups called {@link Trn}. In java we have {@link MessageGroup} helper class
 * with same function and it can be constructed directly from Trn JAXB class object.
 * Translation key is defined at group level.
 * Each group contains list of messages in different languages. At JAXB layer this
 * class is  {@link Language} and we also have old hibernate bean {@link Message} which
 * defines one translation record in AMP.
 * All methods below work with these classes.
 * @see ImportExportTranslations 
 * @author Irakli Kobiashvili ikobiashvili@dgfoundation.org
 *
 */
public class ImportExportUtil {

	private static Logger logger = Logger.getLogger(ImportExportUtil.class);

	/**
	 * Imports data from JAXB translations instance to database.
	 * Also updates translation cache with only those messages that were affected.
	 * Cache is updated only it database transaction was successful. 
	 * @param translations
	 * @param option
	 * @throws DgException
	 */
	public static void importTranslations(Translations translations, ImportExportOption option) throws DgException{
		List<Trn> groups = translations.getTrn();
		Session session = null;
		Transaction tx = null;
		if (groups!=null && groups.size() > 0){
			try {
				session = PersistenceManager.getRequestDBSession();
				//set session in parameter
				option.setDbSession(session);
				//set list of affected messages 
				option.setAffectedMessages(new LinkedList<Message>());
				
//beginTransaction();
				for (Trn xmlGroup : groups) {
					processTranslationGroup(xmlGroup, option);
				}
				//tx.commit();
				
				//update translation cache after commit is success.
				List<Message> messages = option.getAffectedMessages();
				TranslatorWorker worker = TranslatorWorker.getInstance("");
				for (Message message : messages) {
					worker.refresh(message);
				}
				logger.info("number of affected messages (update or insert) = "+ messages.size());
			} catch (HibernateException e) {
				logger.error(e);
				if (tx!=null){
					try {
						tx.rollback();
					} catch (HibernateException e1) {
						logger.error(e1);
						throw new DgException("Cannot rollback translation import changes!",e1);
					}
				}
				throw new DgException("Cannot commit translation import changes",e);
			} catch (WorkerException e) {
				logger.error(e);
				throw new DgException("Couldnot refresh cache after successfull import. you will need restart",e);
			}
		}
	}
	
	/**
	 * Converts XML group node to messages group and saves it.
	 * message group is bean of {@link MessageGroup} type.
	 * @param xmlGroup
	 * @param option
	 */
	private static void processTranslationGroup(Trn xmlGroup, ImportExportOption option){
		MessageGroup group = new MessageGroup(xmlGroup);
		saveGroup(group, option);
	}
	
	/**
	 * Saves message group to database.
	 * If allowed languages in option is set then translations in group with
	 * those languages will be saved. If that option is not set (NULL) then all
	 * translations in group will be saved. 
	 * @param group
	 * @param option
	 */
	private static void saveGroup(MessageGroup group, ImportExportOption option){
		if (option.getLocalesToSave()==null){
			//no languages specified - saving all.
			Collection<Message> messages = group.getAllMessages();
			for (Message message : messages) {
				saveMessage(message, option);
			}
		}else{
			//languages are selected - saving only specified languages
			for (String local : option.getLocalesToSave()) {
				Message message = group.getMessageByLocale(local);
				if (message != null) saveMessage(message, option);
			}
			
		}
	}
	
	/**
	 * Saves message in db according to rules specified in option.
	 * Also tries to find already existing record with same key (key,locale,siteId) in db.
	 * This already existing record is used when checking save rules. 
	 * @param message
	 * @param option
	 */
	private static void saveMessage(Message message, ImportExportOption option){
		if (message == null) return;
		try {
			String msgKey 		= message.getKey();
			String msgLang 		= message.getLocale();
			String msgSiteId 	= message.getSiteId();
			
			//find same record in db
			Message existingMessage = option.getSearcher().get(msgKey,msgLang,msgSiteId);
			
			//default type in case we do not have specified types.
			//This default type means that we will not skip any record. If this is not goo didea, change with return;
			ImportType type = ImportType.UPDATE;
			if (option.getTypeByLanguage() != null && option.getTypeByLanguage().get(msgLang)!=null){
				//get real import type
				type = option.getTypeByLanguage().get(msgLang);
			}
			Session dbSession = option.getDbSession();
			List<Message> affected = option.getAffectedMessages();
			
			//TODO improve by defining interface and getting it from enumeration directly.
			if (type.equals(ImportType.OVERWRITE)){
				overwrite(message, existingMessage, dbSession, affected);
			}else if (type.equals(ImportType.UPDATE)){
				updateByTimestamp(message, existingMessage, dbSession, affected);
			}else if (type.equals(ImportType.ONLY_NEW)){
				saveIfNew(message, existingMessage, dbSession, affected);
			}
		} catch (Exception e) {
			logger.error(e);
		}
	}
	
	/**
	 * Updates existing message with imported one only if imported message is newer then existing one.
	 * If there is no existing message (null specified) then imported one will be saved as new message. 
	 * @param message
	 * @param existingMessage
	 * @param session
	 * @param affected
	 * @throws Exception 
	 */
	private static void updateByTimestamp(Message message, Message existingMessage, Session session, List<Message> affected) throws Exception{
		if (existingMessage==null){
			session.save(message);
			affected.add(message);
		} else {
			Timestamp timeOfExisting = existingMessage.getCreated();
			Timestamp timeOfNew = message.getCreated();
                    if (timeOfNew.compareTo(timeOfExisting) > 0) {
                        existingMessage.setCreated(message.getCreated());
                        existingMessage.setKeyWords(message.getKeyWords());
                        existingMessage.setLastAccessed(message.getLastAccessed());
                        existingMessage.setMessage(message.getMessage());
                        session.update(existingMessage);
                        affected.add(existingMessage);
                    }
		}
	}
	
	/**
	 * Overwrites existing message with imported one without checking time stamps.
	 * If there is no existing message in db then imported one is saved as new.
	 * @param message
	 * @param existingMessage
	 * @param session
	 * @param affected
	 * @throws Exception 
	 */
	private static void overwrite(Message message, Message existingMessage, Session session, List<Message> affected) throws Exception{
		if (existingMessage != null){
			existingMessage.setCreated(message.getCreated());
			existingMessage.setKeyWords(message.getKeyWords());
			existingMessage.setLastAccessed(message.getLastAccessed());
			existingMessage.setMessage(message.getMessage());
			session.update(existingMessage);
		}else{
			session.save(message);
		}
		affected.add(message);
	}
	
	/**
	 * Saves only if imported message is new - there is no message with same key (key,locale,siteId) in database.
	 * @param message
	 * @param existingMessage
	 * @param session
	 * @param affected
	 * @return true if record has been saved, false if not saved because we have existing record in database.
	 * @throws Exception 
	 */
	private static boolean saveIfNew(Message message, Message existingMessage, Session session, List<Message> affected) throws Exception{
		if (existingMessage == null){
			session.save(message);
			affected.add(message);
			return true;
		}
		return false;
	}
	
	/**
	 * Imports translations from file if it exists in specific folder.
	 * IMPORTANT: DO NOT CALL this method from regular code.
	 * Only place where it is called from is startup code before initialization of translation cache.
	 * Method checks for XML file with specific name in specific folder. 
	 * If that file is found then tries to import translations form it.
	 * That XML file must be product of export function for translations export-import wizard in AMP admin menu.  
	 */
	public static void importIfFileExists(){
		try {
			File xmlFile = getFle();
			if (xmlFile == null) return;
			Translations rootNode = getRootNode(xmlFile);
			if (rootNode == null) return;

			ImportExportOption param = new ImportExportOption();
			param.setSearcher(getDbSearcher());
			param.setLocalesToSave(new HashSet<String>(TranslatorWorker.getAllUsedLanguages()));
			//Do work
			importTranslations(rootNode, param);
			xmlFile.delete();
		} catch (Exception e) {
			logger.error("Error during import!!!",e);
		}
	}
	
	/**
	 * Returns file for importing.
	 * File should exists and be readable.
	 * @return file if it exists on disk and is readable otherwise null.
	 */
	private static File getFle(){
		final String IMPORT_FILE_NAME = "translationImport.xml"; 
		File file = new File(IMPORT_FILE_NAME);
		if (file.exists() && file.canRead()) return file;
		return null;
	}
	
	/**
	 * Retrieves root node from file.
	 * @param file
	 * @return translations root node.
	 */
	private static Translations getRootNode(File file){
		Translations root = null;
		try {
			Unmarshaller unmarshaller = getUnmarshaler();
			root = (Translations) unmarshaller.unmarshal(file);
		} catch (JAXBException e) {
			logger.error(e);
		}
		return root;
	}
	
	/**
	 * Implements searcher that searches translation cache.
	 */
	private static class CacheSearcher implements TranslationSearcher{
		private TranslatorWorker worker = TranslatorWorker.getInstance("");
		@Override
		public Message get(String key,String locale,String siteId) throws Exception{
			return worker.getByKey(key, locale, siteId);
		}
	}
	
	/**
	 * Returns searcher which searches translation cache.
	 * Use this in actions or in any place when where cache is already initialized after startup. 
	 * @return searcher for cache
	 */
	public static TranslationSearcher getCacheSearcher(){
		return new CacheSearcher();
	}

	/**
	 * Returns searcher which bypasses translations and searches directly in db.
	 * Use this when cache is not initialized yet at startup time.
	 * DO NOT USE after cache initialization because its slow 
	 * and if you make changes to returned messages then cache will not "see" it.
	 * NOTE: Currently not implemented - returns null.
	 * @return searcher for db
	 */
	public static TranslationSearcher getDbSearcher(){
		//TODO implement
		return null;
	}
	
	/**
	 * Returns set of language codes appearing in translation.
	 * @param root unmarshalled XML
	 * @return set of language codes.
	 */
	public static Set<String> extractUsedLangages(Translations root){
		Set<String> langCodes = new HashSet<String>();
		List<Trn> groups = root.getTrn();
		for (Trn group : groups) {
			List<Language> messages = group.getLang();
			for (Language message : messages) {
				langCodes.add(message.getCode());
			}
		}
		return langCodes;
	}

	/**
	 * Returns JAXB context.
	 * Just to have this in one place in case schema is changed.
	 * @return JAXB context for translations.
	 * @throws JAXBException
	 */
	private static JAXBContext getJAXBcontext() throws JAXBException{
		return JAXBContext.newInstance("org.digijava.module.translation.jaxb");
	}
	
	/**
	 * Returns JAXB unmarshaller for translations.
	 * @return JAXB unmarshaller for translations.
	 * @throws JAXBException
	 */
	public static Unmarshaller getUnmarshaler() throws JAXBException{
		JAXBContext jaxbContext = getJAXBcontext();
		return jaxbContext.createUnmarshaller();
	}
	
	/**
	 * ReturnJAXB  marshaller for translations.
	 * @return JAXB marshaller
	 * @throws JAXBException
	 */
	public static Marshaller getMarshaller() throws JAXBException{
		return getJAXBcontext().createMarshaller();
	}
	
	/**
	 * Exports translations in JAXB Translations instance.
	 * @param translations root object(tag) where should groups and its members go. Cannot be null. 
	 * @param languagesToExport set of language codes. If null then all languages are loaded and exported.
	 * @throws Exception
	 */
	public static void exportTranslations(Translations translations, Set<String> languagesToExport) throws Exception{
		List<MessageGroup> groups = loadMessageGroups(languagesToExport);
		if (groups != null){
			for (MessageGroup group : groups) {
				//Creates JAXB Trn instance which represents group
				Trn xmlGroup = group.createTrn();
				translations.getTrn().add(xmlGroup);
			}
		}
	}
	
	/**
	 * Loads message groups for specified languages.
	 * Loads messages filtered by languages codes and then groups them in {@link MessageGroup} beans.
	 * @param languagesToLoad set of language codes. If null then all messages will be grouped.
	 * @return list of message groups
	 * @throws AimException
	 */
	@SuppressWarnings("unchecked")
	public static List<MessageGroup> loadMessageGroups(Set<String> languagesToLoad) throws AimException{
		List<MessageGroup> result = null;
		try {
			Session session = PersistenceManager.getRequestDBSession();
			String oql = "from "+Message.class.getName();
			if (languagesToLoad!=null){
				oql += " as m where m.locale in (:LANG_CODES)";
			}
			Query query = session.createQuery(oql);
			if (languagesToLoad != null){
				query.setParameterList("LANG_CODES", languagesToLoad);
			}
			List<Message> messages = (List<Message>) query.list();
			Collection<MessageGroup> groups = TrnUtil.groupByKey(messages);
			result = new ArrayList<MessageGroup>(groups);
		} catch (Exception e) {
			logger.error(e);
			throw new AimException("Cannot load messages for expot.",e);
		}
		return result;
	}

	/**
	 * Loads message for specified languages.
	 * @param languagesToLoad set of language codes. If null then all messages will be grouped.
	 * @return list of message 
	 * @throws AimException
	 */
	@SuppressWarnings("unchecked")
	public static List<Message> loadMessages(Set<String> languagesToLoad) throws AimException{
		List<Message> messages = null;
		try {
			Session session = PersistenceManager.getRequestDBSession();
			String oql = "from "+Message.class.getName();
			if (languagesToLoad!=null){
				oql += " as m where m.locale in (:LANG_CODES) order by m.key";
			}
			Query query = session.createQuery(oql);
			if (languagesToLoad != null){
				query.setParameterList("LANG_CODES", languagesToLoad);
			}
			messages = (List<Message>) query.list();
			
		} catch (Exception e) {
			logger.error(e);
			throw new AimException("Cannot load messages for expot.",e);
		}
		return messages;
	}
	/**
	 * Used to import translations from xsl file.
	 * @param inputStreame 
	 * @param msgSiteId 
	 * @return list target language 
	 * @throws AimException
	 */
	public static String importExcelFile(InputStream inputStreame, String msgSiteId)  throws AimException{
		String targetLanguage=null;
		try {
			Session session = PersistenceManager.getRequestDBSession();
			POIFSFileSystem fsFileSystem = new POIFSFileSystem(inputStreame);
			TranslatorWorker worker = TranslatorWorker.getInstance("");
			HSSFWorkbook workBook = new HSSFWorkbook(fsFileSystem);
			HSSFSheet hssfSheet = workBook.getSheetAt(0);
			Iterator<Row> rowIterator = hssfSheet.rowIterator();
			targetLanguage=null;
			while (rowIterator.hasNext()) {
				Row hssfRow = rowIterator.next();
				if(hssfRow.getRowNum()==0){
					targetLanguage=hssfRow.getCell(2).getStringCellValue();
					if(!DbUtil.isAvailableLanguage(targetLanguage)){
						return null;
					}
					continue;
				}
				
				String key=(hssfRow.getCell(0).getCellType()==HSSFCell.CELL_TYPE_NUMERIC)?hssfRow.getCell(0).getNumericCellValue()+"":hssfRow.getCell(0).getStringCellValue();
				Message existingMessageInTargetLang =ImportExportUtil.getCacheSearcher().get(key,targetLanguage,msgSiteId);
				Message existingMessageInEnglish =ImportExportUtil.getCacheSearcher().get(key,"en",msgSiteId);
				String englishText=(hssfRow.getCell(1)==null)?"":hssfRow.getCell(1).getStringCellValue();
				String targetText=(hssfRow.getCell(2)==null)?"":hssfRow.getCell(2).getStringCellValue();
				//save only new trns
				if (existingMessageInTargetLang == null) {
					saveMsg(msgSiteId, session, worker, targetLanguage,
							targetText, key);
				}
				if(existingMessageInEnglish==null){
					saveMsg(msgSiteId, session, worker, "en",
							englishText, key);
				}
				
			}
		}
		catch(NullPointerException e){
			logger.error("file is not ok");
			throw new AimException("Cannot import messages",e);
		}
		catch (Exception e) {
			logger.error(e);
			throw new AimException("Cannot import messages",e);
		}
		return targetLanguage;

	}

	private static void saveMsg(String msgSiteId, Session session,
			TranslatorWorker worker, String targetLanguage, String msgBodyText,
			String key) throws WorkerException {
		Message message = new Message();
		message.setKey(key);
		message.setLocale(targetLanguage);
		message.setSiteId(msgSiteId);
		message.setMessage(msgBodyText);
		message.setCreated(new java.sql.Timestamp(System.currentTimeMillis()));
		message.setLastAccessed(message.getCreated());
		session.save(message);
		worker.refresh(message);
	}
}
