//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2014.01.25 at 05:56:27 PM CET
//


package org.eclipse.om2m.commons.resource;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for FilterCriteriaType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="FilterCriteriaType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ifModifiedSince" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="ifUnmodifiedSince" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="ifMatch" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ifNoneMatch" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FilterCriteriaType", propOrder = {
        "ifModifiedSince",
        "ifUnmodifiedSince",
        "ifMatch",
        "ifNoneMatch"
})
public class FilterCriteriaType {

    @XmlElement(namespace = "")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar ifModifiedSince;
    @XmlElement(namespace = "")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar ifUnmodifiedSince;
    @XmlElement(namespace = "")
    protected List<String> ifMatch;
    @XmlElement(namespace = "")
    protected List<String> ifNoneMatch;

    public FilterCriteriaType(){
        super();
    }

    /**
     * Gets the value of the ifModifiedSince property.
     *
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getIfModifiedSince() {
        return ifModifiedSince;
    }

    /**
     * Sets the value of the ifModifiedSince property.
     *
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public void setIfModifiedSince(XMLGregorianCalendar value) {
        this.ifModifiedSince = value;
    }

    /**
     * Gets the value of the ifUnmodifiedSince property.
     *
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getIfUnmodifiedSince() {
        return ifUnmodifiedSince;
    }

    /**
     * Sets the value of the ifUnmodifiedSince property.
     *
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public void setIfUnmodifiedSince(XMLGregorianCalendar value) {
        this.ifUnmodifiedSince = value;
    }

    /**
     * Gets the value of the ifMatch property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ifMatch property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIfMatch().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     *
     *
     */
    public List<String> getIfMatch() {
        if (ifMatch == null) {
            ifMatch = new ArrayList<String>();
        }
        return this.ifMatch;
    }

    /**
     * Gets the value of the ifNoneMatch property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ifNoneMatch property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIfNoneMatch().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     *
     *
     */
    public List<String> getIfNoneMatch() {
        if (ifNoneMatch == null) {
            ifNoneMatch = new ArrayList<String>();
        }
        return this.ifNoneMatch;
    }

}
