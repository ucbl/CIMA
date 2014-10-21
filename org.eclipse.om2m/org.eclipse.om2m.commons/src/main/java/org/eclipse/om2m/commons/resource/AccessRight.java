//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2014.01.25 at 04:25:45 PM CET
//


package org.eclipse.om2m.commons.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 *
 * <p>
 * Java class for AccessRight complex type.
 *
 * <p>
 * Access rights are defined as "white lists" or permissions, i.e. each permission defines
 * "allowed" entities (defined in the permissionHolders) for certain access modes (permissionFlags).
 * Sets of permissions are handled such that the resulting permissions for a group of permissions
 * are the sum of the individual permissions. I.e. an action is permitted if it is permitted by
 * some / any permission in the set
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="AccessRight">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://uri.etsi.org/m2m}expirationTime" minOccurs="0"/>
 *         &lt;element ref="{http://uri.etsi.org/m2m}searchStrings" minOccurs="0"/>
 *         &lt;element ref="{http://uri.etsi.org/m2m}creationTime" minOccurs="0"/>
 *         &lt;element ref="{http://uri.etsi.org/m2m}lastModifiedTime" minOccurs="0"/>
 *         &lt;element ref="{http://uri.etsi.org/m2m}announceTo" minOccurs="0"/>
 *         &lt;element ref="{http://uri.etsi.org/m2m}permissions" minOccurs="0"/>
 *         &lt;element ref="{http://uri.etsi.org/m2m}selfPermissions"/>
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
@XmlRootElement
public class AccessRight extends Resource {

    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar expirationTime;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar creationTime;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastModifiedTime;
    protected AnnounceTo announceTo;
    protected PermissionListType permissions;
    @XmlElement(required = true)
    protected PermissionListType selfPermissions;
    @XmlSchemaType(name = "anyURI")
    protected String subscriptionsReference;
    @XmlAttribute(name = "id", namespace = "http://uri.etsi.org/m2m")
    @XmlSchemaType(name = "anyURI")
    protected String id;

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
     * Gets the value of the permissions property.
     *
     * @return
     *     possible object is
     *     {@link PermissionListType }
     *
     */
    public PermissionListType getPermissions() {
        return permissions;
    }

    /**
     * Sets the value of the permissions property.
     *
     * @param value
     *     allowed object is
     *     {@link PermissionListType }
     *
     */
    public void setPermissions(PermissionListType value) {
        this.permissions = value;
    }

    /**
     * Gets the value of the selfPermissions property.
     *
     * @return
     *     possible object is
     *     {@link PermissionListType }
     *
     */
    public PermissionListType getSelfPermissions() {
        return selfPermissions;
    }

    /**
     * Sets the value of the selfPermissions property.
     *
     * @param value
     *     allowed object is
     *     {@link PermissionListType }
     *
     */
    public void setSelfPermissions(PermissionListType value) {
        this.selfPermissions = value;
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
     * Gets the value of id property.
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
        return "AccessRight [expirationTime=" + expirationTime
                + ", searchStrings=" + searchStrings + ", creationTime="
                + creationTime + ", lastModifiedTime=" + lastModifiedTime
                + ", announceTo=" + announceTo + ", permissions=" + permissions
                + ", selfPermissions=" + selfPermissions
                + ", subscriptionsReference=" + subscriptionsReference
                + ", id=" + id + ", uri=" + uri + "]";
    }
}
