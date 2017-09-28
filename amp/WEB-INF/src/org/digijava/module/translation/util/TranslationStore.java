package org.digijava.module.translation.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Class to guard all operations on the translation store and to be
 * sure that the store is not misused or values are forgotten there
 *
 * @author aartimon@developmentgateway.org
 */
public class TranslationStore {
    //map between id generated by the sequence field and the FTP it stores
    private static final HashMap<Long, FieldTranslationPack> trnStore = new HashMap<Long, FieldTranslationPack>();
    //map between new object class and a map between the object id and a list of FTPs for it
    private static final HashMap<String, HashMap<Long, List<FieldTranslationPack>>> toSaveStore = new HashMap<String, HashMap<Long, List<FieldTranslationPack>>>();
    //sequence to generate id's for the trnStore
    private static final AtomicLong sequence = new AtomicLong();

    /**
     * Insert a new FTP in the translation storea and return the id reference for it
     * @param pack translation pack
     * @return reference for FTP
     */
    public static Long insert(FieldTranslationPack pack){
        Long id = sequence.incrementAndGet();
        trnStore.put(id, pack);
        return id;
    }

    /**
     * Moves the current FTP for the current field in the save store
     * @param ftpId id for the FTP
     * @param objectClass class representing the object
     * @param objId id for the object
     * @return current FTP
     */
    public static FieldTranslationPack prepareForSave(Long ftpId, String objectClass, Long objId){
        FieldTranslationPack ftp = trnStore.remove(ftpId);
        //see if we have packs of FTPs for the current object class
        HashMap<Long, List<FieldTranslationPack>> idMap = toSaveStore.get(objectClass);
        if (idMap == null){
            idMap = new HashMap<Long, List<FieldTranslationPack>>();
            toSaveStore.put(objectClass, idMap);
        }
        //check if we have a pack of FPTs for the current object
        List<FieldTranslationPack> list = idMap.get(objId);
        if (list == null){
            list = new ArrayList<FieldTranslationPack>();
            idMap.put(objId, list);
        }
        list.add(ftp);
        return ftp;
    }

    /**
     * Retrieves the list of FTPs for the current object, that need saving
     * @param objectClass class for the object
     * @param objId if of the object
     * @return list of FTPs
     */
    public static List<FieldTranslationPack> saveAndRemove(String objectClass, Long objId){
        HashMap<Long, List<FieldTranslationPack>> idMap = toSaveStore.get(objectClass);
        if (idMap == null)
            return null;
        return idMap.remove(objId);
    }
}
