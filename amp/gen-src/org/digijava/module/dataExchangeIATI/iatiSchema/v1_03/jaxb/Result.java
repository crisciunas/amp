//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.08.23 at 03:12:34 PM EEST 
//


package org.digijava.module.dataExchangeIATI.iatiSchema.v1_03.jaxb;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.w3c.dom.Element;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element ref="{}title"/>
 *         &lt;element ref="{}description"/>
 *         &lt;element name="indicator">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;choice maxOccurs="unbounded" minOccurs="0">
 *                   &lt;element ref="{}title"/>
 *                   &lt;element ref="{}description"/>
 *                   &lt;element name="baseline">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;choice maxOccurs="unbounded" minOccurs="0">
 *                             &lt;element ref="{}comment"/>
 *                             &lt;any processContents='lax' namespace='##other'/>
 *                           &lt;/choice>
 *                           &lt;attribute name="year" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
 *                           &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;anyAttribute processContents='lax' namespace='##other'/>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="period">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;choice maxOccurs="unbounded" minOccurs="0">
 *                             &lt;element name="period-start" type="{}dateType"/>
 *                             &lt;element name="period-end" type="{}dateType"/>
 *                             &lt;element name="target">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;choice maxOccurs="unbounded" minOccurs="0">
 *                                       &lt;element ref="{}comment"/>
 *                                       &lt;any processContents='lax' namespace='##other'/>
 *                                     &lt;/choice>
 *                                     &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="actual">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;choice maxOccurs="unbounded" minOccurs="0">
 *                                       &lt;element ref="{}comment"/>
 *                                       &lt;any processContents='lax' namespace='##other'/>
 *                                     &lt;/choice>
 *                                     &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;any processContents='lax' namespace='##other'/>
 *                           &lt;/choice>
 *                           &lt;anyAttribute processContents='lax' namespace='##other'/>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;any processContents='lax' namespace='##other'/>
 *                 &lt;/choice>
 *                 &lt;attribute name="measure" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="ascending" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *                 &lt;anyAttribute processContents='lax' namespace='##other'/>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;any processContents='lax' namespace='##other'/>
 *       &lt;/choice>
 *       &lt;attribute ref="{}type use="required""/>
 *       &lt;attribute name="aggregation-status" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "titleOrDescriptionOrIndicator"
})
@XmlRootElement(name = "result")
public class Result {

    @XmlElementRefs({
        @XmlElementRef(name = "description", type = Description.class),
        @XmlElementRef(name = "indicator", type = JAXBElement.class),
        @XmlElementRef(name = "title", type = JAXBElement.class)
    })
    @XmlAnyElement(lax = true)
    protected List<Object> titleOrDescriptionOrIndicator;
    @XmlAttribute(name = "type", required = true)
    protected String type;
    @XmlAttribute(name = "aggregation-status")
    protected Boolean aggregationStatus;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    /**
     * Gets the value of the titleOrDescriptionOrIndicator property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the titleOrDescriptionOrIndicator property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTitleOrDescriptionOrIndicator().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Description }
     * {@link JAXBElement }{@code <}{@link TextType }{@code >}
     * {@link Object }
     * {@link JAXBElement }{@code <}{@link Result.Indicator }{@code >}
     * {@link Element }
     * 
     * 
     */
    public List<Object> getTitleOrDescriptionOrIndicator() {
        if (titleOrDescriptionOrIndicator == null) {
            titleOrDescriptionOrIndicator = new ArrayList<Object>();
        }
        return this.titleOrDescriptionOrIndicator;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Gets the value of the aggregationStatus property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAggregationStatus() {
        return aggregationStatus;
    }

    /**
     * Sets the value of the aggregationStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAggregationStatus(Boolean value) {
        this.aggregationStatus = value;
    }

    /**
     * Gets a map that contains attributes that aren't bound to any typed property on this class.
     * 
     * <p>
     * the map is keyed by the name of the attribute and 
     * the value is the string value of the attribute.
     * 
     * the map returned by this method is live, and you can add new attribute
     * by updating the map directly. Because of this design, there's no setter.
     * 
     * 
     * @return
     *     always non-null
     */
    public Map<QName, String> getOtherAttributes() {
        return otherAttributes;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;choice maxOccurs="unbounded" minOccurs="0">
     *         &lt;element ref="{}title"/>
     *         &lt;element ref="{}description"/>
     *         &lt;element name="baseline">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;choice maxOccurs="unbounded" minOccurs="0">
     *                   &lt;element ref="{}comment"/>
     *                   &lt;any processContents='lax' namespace='##other'/>
     *                 &lt;/choice>
     *                 &lt;attribute name="year" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
     *                 &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;anyAttribute processContents='lax' namespace='##other'/>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="period">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;choice maxOccurs="unbounded" minOccurs="0">
     *                   &lt;element name="period-start" type="{}dateType"/>
     *                   &lt;element name="period-end" type="{}dateType"/>
     *                   &lt;element name="target">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;choice maxOccurs="unbounded" minOccurs="0">
     *                             &lt;element ref="{}comment"/>
     *                             &lt;any processContents='lax' namespace='##other'/>
     *                           &lt;/choice>
     *                           &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="actual">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;choice maxOccurs="unbounded" minOccurs="0">
     *                             &lt;element ref="{}comment"/>
     *                             &lt;any processContents='lax' namespace='##other'/>
     *                           &lt;/choice>
     *                           &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;any processContents='lax' namespace='##other'/>
     *                 &lt;/choice>
     *                 &lt;anyAttribute processContents='lax' namespace='##other'/>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;any processContents='lax' namespace='##other'/>
     *       &lt;/choice>
     *       &lt;attribute name="measure" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="ascending" type="{http://www.w3.org/2001/XMLSchema}boolean" />
     *       &lt;anyAttribute processContents='lax' namespace='##other'/>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "titleOrDescriptionOrBaseline"
    })
    public static class Indicator {

        @XmlElementRefs({
            @XmlElementRef(name = "description", type = Description.class),
            @XmlElementRef(name = "period", type = JAXBElement.class),
            @XmlElementRef(name = "title", type = JAXBElement.class),
            @XmlElementRef(name = "baseline", type = JAXBElement.class)
        })
        @XmlAnyElement(lax = true)
        protected List<Object> titleOrDescriptionOrBaseline;
        @XmlAttribute(name = "measure", required = true)
        protected String measure;
        @XmlAttribute(name = "ascending")
        protected Boolean ascending;
        @XmlAnyAttribute
        private Map<QName, String> otherAttributes = new HashMap<QName, String>();

        /**
         * Gets the value of the titleOrDescriptionOrBaseline property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the titleOrDescriptionOrBaseline property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getTitleOrDescriptionOrBaseline().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Description }
         * {@link JAXBElement }{@code <}{@link Result.Indicator.Period }{@code >}
         * {@link JAXBElement }{@code <}{@link TextType }{@code >}
         * {@link Object }
         * {@link JAXBElement }{@code <}{@link Result.Indicator.Baseline }{@code >}
         * {@link Element }
         * 
         * 
         */
        public List<Object> getTitleOrDescriptionOrBaseline() {
            if (titleOrDescriptionOrBaseline == null) {
                titleOrDescriptionOrBaseline = new ArrayList<Object>();
            }
            return this.titleOrDescriptionOrBaseline;
        }

        /**
         * Gets the value of the measure property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMeasure() {
            return measure;
        }

        /**
         * Sets the value of the measure property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMeasure(String value) {
            this.measure = value;
        }

        /**
         * Gets the value of the ascending property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isAscending() {
            return ascending;
        }

        /**
         * Sets the value of the ascending property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setAscending(Boolean value) {
            this.ascending = value;
        }

        /**
         * Gets a map that contains attributes that aren't bound to any typed property on this class.
         * 
         * <p>
         * the map is keyed by the name of the attribute and 
         * the value is the string value of the attribute.
         * 
         * the map returned by this method is live, and you can add new attribute
         * by updating the map directly. Because of this design, there's no setter.
         * 
         * 
         * @return
         *     always non-null
         */
        public Map<QName, String> getOtherAttributes() {
            return otherAttributes;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;choice maxOccurs="unbounded" minOccurs="0">
         *         &lt;element ref="{}comment"/>
         *         &lt;any processContents='lax' namespace='##other'/>
         *       &lt;/choice>
         *       &lt;attribute name="year" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
         *       &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;anyAttribute processContents='lax' namespace='##other'/>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "commentOrAny"
        })
        public static class Baseline {

            @XmlElementRef(name = "comment", type = JAXBElement.class)
            @XmlAnyElement(lax = true)
            protected List<Object> commentOrAny;
            @XmlAttribute(name = "year", required = true)
            @XmlSchemaType(name = "positiveInteger")
            protected BigInteger year;
            @XmlAttribute(name = "value", required = true)
            protected String value;
            @XmlAnyAttribute
            private Map<QName, String> otherAttributes = new HashMap<QName, String>();

            /**
             * Gets the value of the commentOrAny property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the commentOrAny property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getCommentOrAny().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link JAXBElement }{@code <}{@link TextType }{@code >}
             * {@link Element }
             * {@link Object }
             * 
             * 
             */
            public List<Object> getCommentOrAny() {
                if (commentOrAny == null) {
                    commentOrAny = new ArrayList<Object>();
                }
                return this.commentOrAny;
            }

            /**
             * Gets the value of the year property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getYear() {
                return year;
            }

            /**
             * Sets the value of the year property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setYear(BigInteger value) {
                this.year = value;
            }

            /**
             * Gets the value of the value property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getValue() {
                return value;
            }

            /**
             * Sets the value of the value property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setValue(String value) {
                this.value = value;
            }

            /**
             * Gets a map that contains attributes that aren't bound to any typed property on this class.
             * 
             * <p>
             * the map is keyed by the name of the attribute and 
             * the value is the string value of the attribute.
             * 
             * the map returned by this method is live, and you can add new attribute
             * by updating the map directly. Because of this design, there's no setter.
             * 
             * 
             * @return
             *     always non-null
             */
            public Map<QName, String> getOtherAttributes() {
                return otherAttributes;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;choice maxOccurs="unbounded" minOccurs="0">
         *         &lt;element name="period-start" type="{}dateType"/>
         *         &lt;element name="period-end" type="{}dateType"/>
         *         &lt;element name="target">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;choice maxOccurs="unbounded" minOccurs="0">
         *                   &lt;element ref="{}comment"/>
         *                   &lt;any processContents='lax' namespace='##other'/>
         *                 &lt;/choice>
         *                 &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="actual">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;choice maxOccurs="unbounded" minOccurs="0">
         *                   &lt;element ref="{}comment"/>
         *                   &lt;any processContents='lax' namespace='##other'/>
         *                 &lt;/choice>
         *                 &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;any processContents='lax' namespace='##other'/>
         *       &lt;/choice>
         *       &lt;anyAttribute processContents='lax' namespace='##other'/>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "periodStartOrPeriodEndOrTarget"
        })
        public static class Period {

            @XmlElementRefs({
                @XmlElementRef(name = "target", type = JAXBElement.class),
                @XmlElementRef(name = "period-start", type = JAXBElement.class),
                @XmlElementRef(name = "actual", type = JAXBElement.class),
                @XmlElementRef(name = "period-end", type = JAXBElement.class)
            })
            @XmlAnyElement(lax = true)
            protected List<Object> periodStartOrPeriodEndOrTarget;
            @XmlAnyAttribute
            private Map<QName, String> otherAttributes = new HashMap<QName, String>();

            /**
             * Gets the value of the periodStartOrPeriodEndOrTarget property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the periodStartOrPeriodEndOrTarget property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getPeriodStartOrPeriodEndOrTarget().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link JAXBElement }{@code <}{@link DateType }{@code >}
             * {@link Object }
             * {@link JAXBElement }{@code <}{@link Result.Indicator.Period.Target }{@code >}
             * {@link JAXBElement }{@code <}{@link DateType }{@code >}
             * {@link JAXBElement }{@code <}{@link Result.Indicator.Period.Actual }{@code >}
             * {@link Element }
             * 
             * 
             */
            public List<Object> getPeriodStartOrPeriodEndOrTarget() {
                if (periodStartOrPeriodEndOrTarget == null) {
                    periodStartOrPeriodEndOrTarget = new ArrayList<Object>();
                }
                return this.periodStartOrPeriodEndOrTarget;
            }

            /**
             * Gets a map that contains attributes that aren't bound to any typed property on this class.
             * 
             * <p>
             * the map is keyed by the name of the attribute and 
             * the value is the string value of the attribute.
             * 
             * the map returned by this method is live, and you can add new attribute
             * by updating the map directly. Because of this design, there's no setter.
             * 
             * 
             * @return
             *     always non-null
             */
            public Map<QName, String> getOtherAttributes() {
                return otherAttributes;
            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;choice maxOccurs="unbounded" minOccurs="0">
             *         &lt;element ref="{}comment"/>
             *         &lt;any processContents='lax' namespace='##other'/>
             *       &lt;/choice>
             *       &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "commentOrAny"
            })
            public static class Actual {

                @XmlElementRef(name = "comment", type = JAXBElement.class)
                @XmlAnyElement(lax = true)
                protected List<Object> commentOrAny;
                @XmlAttribute(name = "value", required = true)
                protected String value;

                /**
                 * Gets the value of the commentOrAny property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the commentOrAny property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getCommentOrAny().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link JAXBElement }{@code <}{@link TextType }{@code >}
                 * {@link Element }
                 * {@link Object }
                 * 
                 * 
                 */
                public List<Object> getCommentOrAny() {
                    if (commentOrAny == null) {
                        commentOrAny = new ArrayList<Object>();
                    }
                    return this.commentOrAny;
                }

                /**
                 * Gets the value of the value property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getValue() {
                    return value;
                }

                /**
                 * Sets the value of the value property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setValue(String value) {
                    this.value = value;
                }

            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;choice maxOccurs="unbounded" minOccurs="0">
             *         &lt;element ref="{}comment"/>
             *         &lt;any processContents='lax' namespace='##other'/>
             *       &lt;/choice>
             *       &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "commentOrAny"
            })
            public static class Target {

                @XmlElementRef(name = "comment", type = JAXBElement.class)
                @XmlAnyElement(lax = true)
                protected List<Object> commentOrAny;
                @XmlAttribute(name = "value", required = true)
                protected String value;

                /**
                 * Gets the value of the commentOrAny property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the commentOrAny property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getCommentOrAny().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link JAXBElement }{@code <}{@link TextType }{@code >}
                 * {@link Element }
                 * {@link Object }
                 * 
                 * 
                 */
                public List<Object> getCommentOrAny() {
                    if (commentOrAny == null) {
                        commentOrAny = new ArrayList<Object>();
                    }
                    return this.commentOrAny;
                }

                /**
                 * Gets the value of the value property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getValue() {
                    return value;
                }

                /**
                 * Sets the value of the value property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setValue(String value) {
                    this.value = value;
                }

            }

        }

    }

}
