/*
 * Created on 1/03/2006
 * 
 * @author akashs
 * 
 */
package org.digijava.module.aim.dbentity;

public class AmpAhsurveyQuestionType {

	private Long ampTypeId;
	private String name;	// 'yes-no', 'calculated'
	private String desc;
	
	/**
	 * @return Returns the desc.
	 */
	public String getDesc() {
		return desc;
	}
	/**
	 * @param desc The desc to set.
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return Returns the typeId.
	 */
	public Long getAmpTypeId() {
		return ampTypeId;
	}
	/**
	 * @param typeId The typeId to set.
	 */
	public void setAmpTypeId(Long ampTypeId) {
		this.ampTypeId = ampTypeId;
	}

}