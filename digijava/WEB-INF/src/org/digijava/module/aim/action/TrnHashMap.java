/**
 * @author dan
 *
 * 
 */
package org.digijava.module.aim.action;

import java.util.ArrayList;

/**
 * @author dan
 *
 */
public class TrnHashMap {
	String lang=new String();
	ArrayList translations= new ArrayList();
	
	public TrnHashMap() {
		super();
		
		// TODO Auto-generated constructor stub
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public ArrayList getTranslations() {
		return translations;
	}
	public void setTranslations(ArrayList translations) {
		this.translations = translations;
	}

}