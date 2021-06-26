package org.menta.model;

import java.util.Locale;

public enum Language {

	ENGLISH(1, "en_US"), PORTUGUESE(2, "pt_BR");
	
	private final int code;
	private final Locale loc;

    private Language(int code, String locale) {
    	this.code = code;
    	this.loc = getLocaleFromString(locale);
    	
    }

    public int getCode() {
    	return code;
    }
    
    public Locale getLocale() {
    	return loc;
    }
    
    public static Language fromCode(int code) {
    	for(Language l : Language.values()) {
    		if (l.getCode() == code) return l;
    	}
    	return null;
    }
    
    public static Locale getLocaleFromString(String s) {
        String[] temp = s.split("_");
        if (temp.length == 1) {
            return new Locale(temp[0]);
        } else if (temp.length == 2) {
            return new Locale(temp[0], temp[1]);
        } else if (temp.length == 3) {
            return new Locale(temp[0], temp[1], temp[2]);
        }
        return null;
    }
}
