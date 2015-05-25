// Generated by delombok at Mon Mar 24 00:10:06 EET 2014
package org.digijava.module.categorymanager.util;

import org.digijava.module.aim.dbentity.AmpOrgGroup;
import org.digijava.module.categorymanager.dbentity.AmpCategoryValue;

/**
 * shim of if (id, value) used for JSPs
 * @author Dolghier Constantin
 */
public class IdWithValueShim {
	public Long id;
	public String value;
	
	public IdWithValueShim(Long id, String value) {
		this.id = id;
		this.value = value;
	}
	
	public IdWithValueShim(AmpCategoryValue acv) {
		this(acv.getId(), acv.getValue());
	}
	
	public IdWithValueShim(AmpOrgGroup grp) {
		this(grp.getAmpOrgGrpId(), grp.getOrgGrpName());
	}
	
	@java.lang.SuppressWarnings("all")
	public Long getId() {
		return this.id;
	}
	
	@java.lang.SuppressWarnings("all")
	public String getValue() {
		return this.value;
	}
	
	@java.lang.SuppressWarnings("all")
	public void setId(final Long id) {
		this.id = id;
	}
	
	@java.lang.SuppressWarnings("all")
	public void setValue(final String value) {
		this.value = value;
	}
	
	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	public boolean equals(final java.lang.Object o) {
		if (o == this) return true;
		if (!(o instanceof IdWithValueShim)) return false;
		final IdWithValueShim other = (IdWithValueShim)o;
		if (!other.canEqual((java.lang.Object)this)) return false;
		final java.lang.Object this$id = this.getId();
		final java.lang.Object other$id = other.getId();
		if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
		final java.lang.Object this$value = this.getValue();
		final java.lang.Object other$value = other.getValue();
		if (this$value == null ? other$value != null : !this$value.equals(other$value)) return false;
		return true;
	}
	
	@java.lang.SuppressWarnings("all")
	public boolean canEqual(final java.lang.Object other) {
		return other instanceof IdWithValueShim;
	}
	
	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		final java.lang.Object $id = this.getId();
		result = result * PRIME + ($id == null ? 0 : $id.hashCode());
		final java.lang.Object $value = this.getValue();
		result = result * PRIME + ($value == null ? 0 : $value.hashCode());
		return result;
	}
	
	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	public java.lang.String toString() {
		return "IdWithValueShim(id=" + this.getId() + ", value=" + this.getValue() + ")";
	}
}