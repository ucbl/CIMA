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
import javax.xml.bind.annotation.XmlType;



/**
 * <p>Java class for ExecInstances complex type.
 *
 * <p>ExecInstances resource contains all execution instances of the same management
 * command {@link mgmtCmd}.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="ExecInstances">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://uri.etsi.org/m2m}creationTime" minOccurs="0"/>
 *         &lt;element ref="{http://uri.etsi.org/m2m}lastModifiedTime" minOccurs="0"/>
 *         &lt;element ref="{http://uri.etsi.org/m2m}execInstanceCollection" minOccurs="0"/>
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
@XmlType(name = "ExecInstances", propOrder = {
        "creationTime",
        "lastModifiedTime",
        "execInstanceCollection",
        "subscriptionsReference"
})
@XmlRootElement
public class ExecInstances extends Resource{

    @XmlSchemaType(name = "dateTime")
    protected String creationTime;
    @XmlSchemaType(name = "dateTime")
    protected String lastModifiedTime;
    protected NamedReferenceCollection execInstanceCollection;
    @XmlSchemaType(name = "anyURI")
    protected String subscriptionsReference;
    @XmlAttribute(name = "id", namespace = "http://uri.etsi.org/m2m")
    @XmlSchemaType(name = "anyURI")
    protected String id;

    /**
     * Gets the value of the creationTime property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCreationTime() {
        return creationTime;
    }

    /**
     * Sets the value of the creationTime property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCreationTime(String value) {
        this.creationTime = value;
    }

    /**
     * Gets the value of the lastModifiedTime property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getLastModifiedTime() {
        return lastModifiedTime;
    }

    /**
     * Sets the value of the lastModifiedTime property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setLastModifiedTime(String value) {
        this.lastModifiedTime = value;
    }

    /**
     * Gets the value of the execInstanceCollection property.
     *
     * @return
     *     possible object is
     *     {@link NamedReferenceCollection }
     *
     */
    public NamedReferenceCollection getExecInstanceCollection() {
        if (execInstanceCollection == null){
            execInstanceCollection = new NamedReferenceCollection();
        }

        return execInstanceCollection;
    }

    /**
     * Sets the value of the execInstanceCollection property.
     *
     * @param value
     *     allowed object is
     *     {@link NamedReferenceCollection }
     *
     */
    public void setExecInstanceCollection(NamedReferenceCollection value) {
        this.execInstanceCollection = value;
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
        return "ExecInstances [creationTime=" + creationTime
                + ", lastModifiedTime=" + lastModifiedTime
                + ", execInstanceCollection=" + execInstanceCollection
                + ", subscriptionsReference=" + subscriptionsReference
                + ", id=" + id + "]";
    }


}
