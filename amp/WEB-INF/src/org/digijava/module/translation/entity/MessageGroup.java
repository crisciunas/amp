package org.digijava.module.translation.entity;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.DatatypeFactory;

import org.apache.log4j.Logger;
import org.digijava.kernel.entity.Message;
import org.digijava.kernel.persistence.WorkerException;
import org.digijava.kernel.request.Site;
import org.digijava.kernel.translator.TranslatorWorker;
import org.digijava.module.translation.jaxb.Language;
import org.digijava.module.translation.jaxb.ObjectFactory;
import org.digijava.module.translation.jaxb.Trn;
import org.digijava.module.translation.util.HashKeyPatch;

/**
 * Group of messages with same translation key.
 * They also should have same hash key but generated preferably from English translation
 * @author Irakli Kobiashvili
 * @see HashKeyPatch
 *
 */
public class MessageGroup {
	
    private static Logger logger = Logger.getLogger(MessageGroup.class);	
	
	private String key = null;
	private String keyWords = null;
	private String siteId = null;
	private Map<String, Message> messages = null;
	
	/**
	 * Constructs group for particular key.
	 * @param key
	 */
	public MessageGroup(String key){
		this.key = key;
		this.messages = new HashMap<String, Message>();
	}

	/**
	 * Constructs group using first message which will be added in this group.
	 * @param message
	 */
	public MessageGroup(Message message){
		this(message.getKey());
		this.setKeyWords(message.getKeyWords());
		this.setSiteId(message.getSiteId());
		addMessage(message);
	}

	/**
	 * Creates message group from XML trn tag.
	 * @param trn tag object
	 */
	public MessageGroup(Trn trn){
		this(trn,null);
	}
	
	/**
	 * Creates message group from XML trn tag for specified site.
	 * @param trn tag object.
	 * @param currentSiteId site ID in string form. can be retrieved from request.
	 */
	public MessageGroup(Trn trn,String currentSiteId){
		this.messages = new HashMap<String, Message>();
		
		//KeyWords
		this.keyWords = trn.getKeywords();
		
		//SiteID
		this.siteId = (currentSiteId==null)?trn.getSiteId():currentSiteId;
		if (this.siteId == null){
			try {
				Site site = TranslatorWorker.getInstance("").getDefaultSite();
				this.siteId = site.getId().toString();//unfortunately in AMP we use PK instead of string siteID value.
			} catch (WorkerException e) {
				e.printStackTrace();
				logger.warn("Using hardcoded siteId=3 because of previouse error for translation with key="+key);
				this.siteId="3";
			}
		}
		
		//key
		this.key = trn.getKey();
		if (this.key == null || "".equals(this.key.trim())){
			Language englishLang = getEnglishLanguageTag(trn.getLang());
			if (englishLang == null){
				logger.warn("Trn tag does not cntain key attribute or English lang tag. using first available lang tag contents to generate key.");
				//trn tag should have at least one lang tag. 
				englishLang = trn.getLang().get(0);
			}
			this.key = TranslatorWorker.generateTrnKey(englishLang.getValue());
		}
		
		//Languages
		for (Language lang  : trn.getLang()) {
			Message msg = new Message();
			
			msg.setSiteId(this.getSiteId());
			msg.setKey(this.key);
			msg.setLocale(lang.getCode());
			
			msg.setMessage(lang.getValue());

			if (lang.getUpdated()!=null){
				msg.setCreated(new Timestamp(lang.getUpdated().toGregorianCalendar().getTime().getTime()));
			}
			
			msg.setKeyWords(this.getKeyWords());
			
			this.addMessage(msg);
		}
	}

	
	/**
	 * Adds message to the group.
	 * If one tries to add message with different key then exception is thrown.
	 * @param message
	 */
	public void addMessage(Message message){
		if (message==null || !message.getKey().equals(this.key)){
			throw new IllegalArgumentException("Cannot add null message or message with different key");
		}
		doPutMessage(message);
	}
	
	/**
	 * puts message in group.
	 * This protected method does not check if keys are same. This is required for hash code grouping
	 * @param message
	 */
	protected void doPutMessage(Message message){
		getMessages().put(message.getLocale(), message);
	}

	/**
	 * Returns key of the group and all messages.
	 * @return
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Return key because key is the hash code itself.
	 * @return
	 */
	public String getHashKey() {
		return key;
	}

	public boolean equals(Object obj) {
		if (obj==null) return false;
		if (!(obj instanceof MessageGroup)) return false;
		MessageGroup anotherGroup = (MessageGroup) obj;
		return this.hashCode()==anotherGroup.hashCode();
	}

	/**
	 * Returns hash code of key which itself is hash code of english message.
	 * TODO check carefully if this is not buggy.
	 */
	public int hashCode() {
		//TODO this is not good idea because key generation may change in TranslatorWorker.generateKey()
		return key.hashCode();
	}

	/**
	 * Returns map of messages where keys are their language codes.
	 * @return
	 */
	protected Map<String, Message> getMessages() {
		return messages;
	}
	
	/**
	 * Rertives message by language code from the group.
	 * @param locale language code of the message to retrive from this group.
	 * @return
	 */
	public Message getMessageByLocale(String locale){
		return messages.get(locale);
	}
	
	/**
	 * Returns list of all messages in the group.
	 * @return
	 */
	public Collection<Message> getAllMessages(){
		return this.messages.values();
	}
	
	/**
	 * Adds all messages from other group to this on.
	 * Uses {@link #doPutMessage(Message)} method for adding to run adding logic 
	 * of the class or subclasses if they override adding logic.
	 * @param group
	 */
	public void addMessagesFrom(MessageGroup group){
		Collection<Message> otherMessages = group.getAllMessages();
		for (Message otherMessage : otherMessages) {
			this.doPutMessage(otherMessage);
		}
	}
	
	/**
	 * Retrives lang tag with language code set to English.
	 * @param languages list of lang tags.
	 * @return English lang tag, or null of not found.
	 */
	private Language getEnglishLanguageTag(List<Language> languages){
		Language retValue=null;
		for (Language language : languages) {
			if(language.getCode().equalsIgnoreCase("en")){
				retValue=language;
				break;
			}
		}
		return retValue;
	}
	
	public Trn createTrn() throws Exception{
		ObjectFactory of=new ObjectFactory();
		Trn trn=of.createTrn();
		trn.setKey(this.getKey());
		for(Map.Entry<String ,Message> entry : messages.entrySet()){
			Message msg=entry.getValue();
			if(trn.getKeywords()==null){
				trn.setKeywords(msg.getKeyWords());
			}
			if(trn.getSiteId()==null){
				trn.setSiteId(msg.getSiteId());
			}
			//creating Language
			Language lang=of.createLanguage();
			lang.setCode(msg.getLocale());
			lang.setValue(msg.getMessage());
			//created
			Calendar cal_u = Calendar.getInstance();
			cal_u.setTime(msg.getCreated());
			lang.setUpdated(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(cal_u.get(Calendar.YEAR),cal_u.get(Calendar.MONTH),cal_u.get(Calendar.DAY_OF_MONTH),cal_u.get(Calendar.HOUR),cal_u.get(Calendar.MINUTE),cal_u.get(Calendar.SECOND))));
			//last accessed			
			if(msg.getLastAccessed()!=null){
				Calendar lastAccessed = Calendar.getInstance();
				lastAccessed.setTime(msg.getLastAccessed());						
				lang.setLastAccessed(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(lastAccessed.get(Calendar.YEAR),lastAccessed.get(Calendar.MONTH),lastAccessed.get(Calendar.DAY_OF_MONTH),lastAccessed.get(Calendar.HOUR),lastAccessed.get(Calendar.MINUTE),lastAccessed.get(Calendar.SECOND))));						
			}
			trn.getLang().add(lang);
		}
		return trn;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public String getKeyWords() {
		return keyWords;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getSiteId() {
		return siteId;
	}
}
