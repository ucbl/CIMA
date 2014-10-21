//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2014.01.25 at 05:56:27 PM CET
//


package org.eclipse.om2m.commons.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for Container complex type.
 *
 * <p> Container resource is a generic resource that shall be used to exchange
 * date between applications and/or SCLs by using the container as a mediator
 * that takes care of buffering the data. Exchange of data between applications
 * (e.g. on device and network side) is abstracted from the need to set up direct
 * connections and allows for scenarios where both parties in the exchange are not
 * online at the same time.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="Container">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://uri.etsi.org/m2m}expirationTime" minOccurs="0"/>
 *         &lt;element ref="{http://uri.etsi.org/m2m}accessRightID" minOccurs="0"/>
 *         &lt;element ref="{http://uri.etsi.org/m2m}searchStrings" minOccurs="0"/>
 *         &lt;element ref="{http://uri.etsi.org/m2m}creationTime" minOccurs="0"/>
 *         &lt;element ref="{http://uri.etsi.org/m2m}lastModifiedTime" minOccurs="0"/>
 *         &lt;element ref="{http://uri.etsi.org/m2m}announceTo" minOccurs="0"/>
 *         &lt;element ref="{http://uri.etsi.org/m2m}maxNrOfInstances" minOccurs="0"/>
 *         &lt;element ref="{http://uri.etsi.org/m2m}maxByteSize" minOccurs="0"/>
 *         &lt;element ref="{http://uri.etsi.org/m2m}maxInstanceAge" minOccurs="0"/>
 *         &lt;element ref="{http://uri.etsi.org/m2m}contentInstancesReference" minOccurs="0"/>
 *         &lt;element ref="{http://uri.etsi.org/m2m}subscriptionsReference" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute ref="{http://uri.etsi.org/m2m}id"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({
    LocationContainer.class
})
@XmlRootElement
public class Container extends Resource {

    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar expirationTime;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar creationTime;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastModifiedTime;
    protected AnnounceTo announceTo;
    protected Long maxNrOfInstances;
    protected Long maxByteSize;
    protected Long maxInstanceAge;
    @XmlSchemaType(name = "anyURI")
    protected String contentInstancesReference;
    @XmlSchemaType(name = "anyURI")
    protected String subscriptionsReference;
    @XmlAttribute(name = "id", namespace = "http://uri.etsi.org/m2m")
    @XmlSchemaType(name = "anyURI")
    protected String id;

    /**
     * Container Constructor
     */
    public Container() {
        super();
    }

    /**
     * Container Constructor
     * @param id - The id of the container
     */
    public Container(String id) {
        super();
        this.id = id;
    }

    /**
     * Gets the value of the expirationTime property.
     *
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getExpirationTime() {
        return expirationTime;
    }

    /**
     * Sets the value of the expirationTime property.
     *
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public void setExpirationTime(XMLGregorianCalendar value) {
        this.expirationTime = value;
    }

    /**
     * Gets the value of the searchStrings property.
     *
     * @return
     *     possible object is
     *     {@link SearchStrings }
     *
     */
    public SearchStrings getSearchStrings() {
        return searchStrings;
    }

    /**
     * Sets the value of the searchStrings property.
     *
     * @param value
     *     allowed object is
     *     {@link SearchStrings }
     *
     */
    public void setSearchStrings(SearchStrings value) {
        this.searchStrings = value;
    }

    /**
     * Gets the value of the creationTime property.
     *
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getCreationTime() {
        return creationTime;
    }

    /**
     * Sets the value of the creationTime property.
     *
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public void setCreationTime(XMLGregorianCalendar value) {
        this.creationTime = value;
    }

    /**
     * Gets the value of the lastModifiedTime property.
     *
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getLastModifiedTime() {
        return lastModifiedTime;
    }

    /**
     * Sets the value of the lastModifiedTime property.
     *
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public void setLastModifiedTime(XMLGregorianCalendar value) {
        this.lastModifiedTime = value;
    }

    /**
     * Gets the value of the announceTo property.
     *
     * @return
     *     possible object is
     *     {@link AnnounceTo }
     *
     */
    public AnnounceTo getAnnounceTo() {
        return announceTo;
    }

    /**
     * Sets the value of the announceTo property.
     *
     * @param value
     *     allowed object is
     *     {@link AnnounceTo }
     *
     */
    public void setAnnounceTo(AnnounceTo value) {
        this.announceTo = value;
    }

    /**
     * Gets the value of the maxNrOfInstances property.
     *
     * @return
     *     possible object is
     *     {@link Long }
     *
     */
    public Long getMaxNrOfInstances() {
        return maxNrOfInstances;
    }

    /**
     * Sets the value of the maxNrOfInstances property.
     *
     * @param value
     *     allowed object is
     *     {@link Long }
     *
     */
    public void setMaxNrOfInstances(Long value) {
        this.maxNrOfInstances = value;
    }

    /**
     * Gets the value of the maxByteSize property.
     *
     * @return
     *     possible object is
     *     {@link Long }
     *
     */
    public Long getMaxByteSize() {
        return maxByteSize;
    }

    /**
     * Sets the value of the maxByteSize property.
     *
     * @param value
     *     allowed object is
     *     {@link Long }
     *
     */
    public void setMaxByteSize(Long value) {
        this.maxByteSize = value;
    }

    /**
     * Gets the value of the maxInstanceAge property.
     *
     * @return
     *     possible object is
     *     {@link Long }
     *
     */
    public Long getMaxInstanceAge() {
        return maxInstanceAge;
    }

    /**
     * Sets the value of the maxInstanceAge property.
     *
     * @param value
     *     allowed object is
     *     {@link Long }
     *
     */
    public void setMaxInstanceAge(Long value) {
        this.maxInstanceAge = value;
    }

    /**
     * Gets the value of the contentInstancesReference property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getContentInstancesReference() {
        return contentInstancesReference;
    }

    /**
     * Sets the value of the contentInstancesReference property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setContentInstancesReference(String value) {
        this.contentInstancesReference = value;
    }

    /**
     * Gets the value of the subscriptionsReference property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getSubscriptionsReference() {
        return subscriptionsReference;
    }

    /**
     * Sets the value of the subscriptionsReference property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setSubscriptionsReference(String value) {
        this.subscriptionsReference = value;
    }

    /**
     * Gets the value of the id property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setId(String value) {
        this.id = value;
    }

    public String toString() {
        return "Container [expirationTime=" + expirationTime
                + ", accessRightID=" + accessRightID + ", searchStrings="
                + searchStrings + ", creationTime=" + creationTime
                + ", lastModifiedTime=" + lastModifiedTime + ", announceTo="
                + announceTo + ", maxNrOfInstances=" + maxNrOfInstances
                + ", maxByteSize=" + maxByteSize + ", maxInstanceAge="
                + maxInstanceAge + ", contentInstancesReference="
                + contentInstancesReference + ", subscriptionsReference="
                + subscriptionsReference + ", id=" + id + ", uri=" + uri + "]";
    }

}
