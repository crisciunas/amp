package org.digijava.module.aim.form;

import org.apache.struts.action.*;
import java.util.Collection;
import java.io.Serializable;

public class UpdateComponentsForm extends ActionForm implements Serializable{
	
	Long Id = null;
	String compTitle = null;
	String compDes = null;
	String compType = null;
	String compCode = null;
	String check;
	
	/**
	 * @return the check
	 */
	public String getCheck() {
		return check;
	}
	/**
	 * @param check the check to set
	 */
	public void setCheck(String check) {
		this.check = check;
	}
	/**
	 * @return Returns the compCode.
	 */
	public String getCompCode() {
		return compCode;
	}
	/**
	 * @param compCode The compCode to set.
	 */
	public void setCompCode(String compCode) {
		this.compCode = compCode;
	}
	/**
	 * @return Returns the compDes.
	 */
	public String getCompDes() {
		return compDes;
	}
	/**
	 * @param compDes The compDes to set.
	 */
	public void setCompDes(String compDes) {
		this.compDes = compDes;
	}
	/**
	 * @return Returns the compTitle.
	 */
	public String getCompTitle() {
		return compTitle;
	}
	/**
	 * @param compTitle The compTitle to set.
	 */
	public void setCompTitle(String compTitle) {
		this.compTitle = compTitle;
	}
	/**
	 * @return Returns the compType.
	 */
	public String getCompType() {
		return compType;
	}
	/**
	 * @param compType The compType to set.
	 */
	public void setCompType(String compType) {
		this.compType = compType;
	}
	/**
	 * @return Returns the id.
	 */
	public Long getId() {
		return Id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(Long id) {
		Id = id;
	}
	
}