
package com.connode.service.terminal.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for wsNodeMetricSearchCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="wsNodeMetricSearchCriteria">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.terminal.service.connode.com/}wsNotificationSearchCriteria">
 *       &lt;sequence>
 *         &lt;element name="numericCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="textCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="startMetricTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="endMetricTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
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
@XmlType(name = "wsNodeMetricSearchCriteria", propOrder = {
    "numericCode",
    "textCode",
    "startMetricTime",
    "endMetricTime",
    "sortOrder"
})
public class WsNodeMetricSearchCriteria
    extends WsNotificationSearchCriteria
{

    protected String numericCode;
    protected String textCode;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar startMetricTime;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar endMetricTime;
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
     * Gets the value of the startMetricTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartMetricTime() {
        return startMetricTime;
    }

    /**
     * Sets the value of the startMetricTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartMetricTime(XMLGregorianCalendar value) {
        this.startMetricTime = value;
    }

    /**
     * Gets the value of the endMetricTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndMetricTime() {
        return endMetricTime;
    }

    /**
     * Sets the value of the endMetricTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndMetricTime(XMLGregorianCalendar value) {
        this.endMetricTime = value;
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
