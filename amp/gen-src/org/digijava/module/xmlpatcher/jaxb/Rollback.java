//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.08.23 at 03:12:35 PM EEST 
//


package org.digijava.module.xmlpatcher.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for rollback complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="rollback">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="prepare" type="{http://docs.ampdev.net/schemas/xmlpatcher}scriptGroup" minOccurs="0"/>
 *         &lt;element name="apply" type="{http://docs.ampdev.net/schemas/xmlpatcher}scriptGroup"/>
 *         &lt;element name="cleanup" type="{http://docs.ampdev.net/schemas/xmlpatcher}scriptGroup" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "rollback", propOrder = {
    "prepare",
    "apply",
    "cleanup"
})
public class Rollback {

    protected ScriptGroup prepare;
    @XmlElement(required = true)
    protected ScriptGroup apply;
    protected ScriptGroup cleanup;

    /**
     * Gets the value of the prepare property.
     * 
     * @return
     *     possible object is
     *     {@link ScriptGroup }
     *     
     */
    public ScriptGroup getPrepare() {
        return prepare;
    }

    /**
     * Sets the value of the prepare property.
     * 
     * @param value
     *     allowed object is
     *     {@link ScriptGroup }
     *     
     */
    public void setPrepare(ScriptGroup value) {
        this.prepare = value;
    }

    /**
     * Gets the value of the apply property.
     * 
     * @return
     *     possible object is
     *     {@link ScriptGroup }
     *     
     */
    public ScriptGroup getApply() {
        return apply;
    }

    /**
     * Sets the value of the apply property.
     * 
     * @param value
     *     allowed object is
     *     {@link ScriptGroup }
     *     
     */
    public void setApply(ScriptGroup value) {
        this.apply = value;
    }

    /**
     * Gets the value of the cleanup property.
     * 
     * @return
     *     possible object is
     *     {@link ScriptGroup }
     *     
     */
    public ScriptGroup getCleanup() {
        return cleanup;
    }

    /**
     * Sets the value of the cleanup property.
     * 
     * @param value
     *     allowed object is
     *     {@link ScriptGroup }
     *     
     */
    public void setCleanup(ScriptGroup value) {
        this.cleanup = value;
    }

}
