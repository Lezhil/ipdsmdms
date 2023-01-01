
package com.connode.service.terminal.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for wsSearch complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="wsSearch">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="itemStart" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="itemCount" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="countTotalItems" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="startCreateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="endCreateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="startUpdateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="endUpdateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="sortDirection" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "wsSearch", propOrder = {
    "itemStart",
    "itemCount",
    "countTotalItems",
    "startCreateTime",
    "endCreateTime",
    "startUpdateTime",
    "endUpdateTime",
    "sortDirection"
})
@XmlSeeAlso({
    WsNodeSearch.class,
    WsNotificationSearchCriteria.class
})
public abstract class WsSearch {

    protected Integer itemStart;
    protected Integer itemCount;
    protected Boolean countTotalItems;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar startCreateTime;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar endCreateTime;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar startUpdateTime;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar endUpdateTime;
    protected String sortDirection;

    /**
     * Gets the value of the itemStart property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getItemStart() {
        return itemStart;
    }

    /**
     * Sets the value of the itemStart property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setItemStart(Integer value) {
        this.itemStart = value;
    }

    /**
     * Gets the value of the itemCount property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getItemCount() {
        return itemCount;
    }

    /**
     * Sets the value of the itemCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setItemCount(Integer value) {
        this.itemCount = value;
    }

    /**
     * Gets the value of the countTotalItems property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCountTotalItems() {
        return countTotalItems;
    }

    /**
     * Sets the value of the countTotalItems property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCountTotalItems(Boolean value) {
        this.countTotalItems = value;
    }

    /**
     * Gets the value of the startCreateTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartCreateTime() {
        return startCreateTime;
    }

    /**
     * Sets the value of the startCreateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartCreateTime(XMLGregorianCalendar value) {
        this.startCreateTime = value;
    }

    /**
     * Gets the value of the endCreateTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndCreateTime() {
        return endCreateTime;
    }

    /**
     * Sets the value of the endCreateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndCreateTime(XMLGregorianCalendar value) {
        this.endCreateTime = value;
    }

    /**
     * Gets the value of the startUpdateTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartUpdateTime() {
        return startUpdateTime;
    }

    /**
     * Sets the value of the startUpdateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartUpdateTime(XMLGregorianCalendar value) {
        this.startUpdateTime = value;
    }

    /**
     * Gets the value of the endUpdateTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndUpdateTime() {
        return endUpdateTime;
    }

    /**
     * Sets the value of the endUpdateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndUpdateTime(XMLGregorianCalendar value) {
        this.endUpdateTime = value;
    }

    /**
     * Gets the value of the sortDirection property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSortDirection() {
        return sortDirection;
    }

    /**
     * Sets the value of the sortDirection property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSortDirection(String value) {
        this.sortDirection = value;
    }

}
