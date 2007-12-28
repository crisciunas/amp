/**
 * FieldPermissionsForm.java
 * (c) 2007 Development Gateway Foundation
 */
package org.digijava.module.aim.form;

import org.apache.struts.action.ActionForm;

/**
 * FieldPermissionsForm.java
 * TODO description here
 * @author mihai
 * @package org.digijava.module.aim.form
 * @since 02.11.2007
 */
public class FieldPermissionsForm extends ActionForm {
    
    private Integer fieldId;
  
    //everyone
    private String evRead;
    private String evEdit;
  
    //guest
    private String guRead;
    private String guEdit;
  
    
    private String baRead;
    private String baEdit;
    
    private String caRead;
    private String caEdit;
    
    private String eaRead;
    private String eaEdit;
    
    private String faRead;
    private String faEdit;
    
    private String iaRead;
    private String iaEdit;
    
    private String rgRead;
    private String rgEdit;
    
    private String sgRead;
    private String sgEdit;
    
    private String fieldName;
    
    public String getFieldName() {
        return fieldName;
    }
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
    public String getBaEdit() {
        return baEdit;
    }
    public void setBaEdit(String baEdit) {
        this.baEdit = baEdit;
    }
    public String getBaRead() {
        return baRead;
    }
    public void setBaRead(String baRead) {
        this.baRead = baRead;
    }
    public String getCaEdit() {
        return caEdit;
    }
    public void setCaEdit(String caEdit) {
        this.caEdit = caEdit;
    }
    public String getCaRead() {
        return caRead;
    }
    public void setCaRead(String caRead) {
        this.caRead = caRead;
    }
    public String getEaEdit() {
        return eaEdit;
    }
    public void setEaEdit(String eaEdit) {
        this.eaEdit = eaEdit;
    }
    public String getEaRead() {
        return eaRead;
    }
    public void setEaRead(String eaRead) {
        this.eaRead = eaRead;
    }
    public String getFaEdit() {
        return faEdit;
    }
    public void setFaEdit(String faEdit) {
        this.faEdit = faEdit;
    }
    public String getFaRead() {
        return faRead;
    }
    public void setFaRead(String faRead) {
        this.faRead = faRead;
    }
    public String getIaEdit() {
        return iaEdit;
    }
    public void setIaEdit(String iaEdit) {
        this.iaEdit = iaEdit;
    }
    public String getIaRead() {
        return iaRead;
    }
    public void setIaRead(String iaRead) {
        this.iaRead = iaRead;
    }
    public Integer getFieldId() {
        return fieldId;
    }
    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
    }
    public String getEvEdit() {
        return evEdit;
    }
    public void setEvEdit(String evEdit) {
        this.evEdit = evEdit;
    }
    public String getEvRead() {
        return evRead;
    }
    public void setEvRead(String evRead) {
        this.evRead = evRead;
    }
    public String getGuEdit() {
        return guEdit;
    }
    public void setGuEdit(String guEdit) {
        this.guEdit = guEdit;
    }
    public String getGuRead() {
        return guRead;
    }
    public void setGuRead(String guRead) {
        this.guRead = guRead;
    }
	public String getRgRead() {
		return rgRead;
	}
	public void setRgRead(String rgRead) {
		this.rgRead = rgRead;
	}
	public String getRgEdit() {
		return rgEdit;
	}
	public void setRgEdit(String rgEdit) {
		this.rgEdit = rgEdit;
	}
	public String getSgRead() {
		return sgRead;
	}
	public void setSgRead(String sgRead) {
		this.sgRead = sgRead;
	}
	public String getSgEdit() {
		return sgEdit;
	}
	public void setSgEdit(String sgEdit) {
		this.sgEdit = sgEdit;
	}
 
}
