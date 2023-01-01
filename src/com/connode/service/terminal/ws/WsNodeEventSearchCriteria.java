
package com.connode.service.terminal.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for wsNodeEventSearchCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="wsNodeEventSearchCriteria">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.terminal.service.connode.com/}wsNotificationSearchCriteria">
 *       &lt;sequence>
 *         &lt;element name="numericCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="textCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="startEventTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="endEventTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="sortOrder" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "wsNodeEventSearchCriteria", propOrder = {
    "numericCode",
    "textCode",
    "startEventTime",
    "endEventTime",
    "sortOrder"
})
public class WsNodeEventSearchCriteria
    extends WsNotificationSearchCriteria
{

    protected String numericCode;
    protected String textCode;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar startEventTime;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar endEventTime;
    protected String sortOrder;

    /**
     * Gets the value of the numericCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumericCode() {
        return numericCode;
    }

    /**
     * Sets the value of the numericCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumericCode(String value) {
        this.numericCode = value;
    }

    /**
     * Gets the value of the textCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTextCode() {
        return textCode;
    }

    /**
     * Sets the value of the textCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTextCode(String value) {
        this.textCode = value;
    }

    /**
     * Gets the value of the startEventTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartEventTime() {
        return startEventTime;
    }

    /**
     * Sets the value of the startEventTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartEventTime(XMLGregorianCalendar value) {
        this.startEventTime = value;
    }

    /**
     * Gets the value of the endEventTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndEventTime() {
        return endEventTime;
    }

    /**
     * Sets the value of the endEventTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndEventTime(XMLGregorianCalendar value) {
        this.endEventTime = value;
    }

    /**
     * Gets the value of the sortOrder property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSortOrder() {
        return sortOrder;
    }

    /**
     * Sets the value of the sortOrder property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSortOrder(String value) {
        this.sortOrder = value;
    }

}
