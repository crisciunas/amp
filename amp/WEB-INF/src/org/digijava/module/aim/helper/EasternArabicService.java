package org.digijava.module.aim.helper;

import java.util.Locale;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang.StringUtils;

public final class EasternArabicService {
    
    private static EasternArabicService service;
    
    public static final String CODE_ARABIC_LANGUAGE = "ar";
    public static final String NUMERIC_ARABIC_EXTENSION = "nu-arab";
    
    public static final Map<Character, Character> ARABIC_NUMERALS = new ImmutableMap.Builder<Character, Character>()
            .put('0', '\u0660')
            .put('1', '\u0661')
            .put('2', '\u0662')
            .put('3', '\u0663')
            .put('4', '\u0664')
            .put('5', '\u0665')
            .put('6', '\u0666')
            .put('7', '\u0667')
            .put('8', '\u0668')
            .put('9', '\u0669')
            .build();
    
    private EasternArabicService() {
    
    }
    
    public static EasternArabicService getInstance() {
        if (service == null) {
            service = new EasternArabicService();
        }
        
        return service;
    }
    
    public String convertWesternArabicToEasternArabic(String text) {
        for (Map.Entry<Character, Character> entry : ARABIC_NUMERALS.entrySet()) {
            text = text.replace(entry.getKey(), entry.getValue());
        }

        return text;
    }
    
    public String convertEasternArabicToWasternArabic(String text) {
        for (Map.Entry<Character, Character> entry : ARABIC_NUMERALS.entrySet()) {
            text = text.replace(entry.getValue(), entry.getKey());
        }
    
        return text;
    }
    
    public boolean isLocaleEasternArabic(Locale locale) {
        return StringUtils.equals(locale.getLanguage(), CODE_ARABIC_LANGUAGE)
                && StringUtils.equals(locale.getExtension(Locale.UNICODE_LOCALE_EXTENSION), NUMERIC_ARABIC_EXTENSION);
    }
    
}
