
package com.connode.service.terminal.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for wsNodeMetric complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="wsNodeMetric">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.terminal.service.connode.com/}wsNotification">
 *       &lt;sequence>
 *         &lt;element name="metricTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="numericCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="textCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "wsNodeMetric", propOrder = {
    "metricTime",
    "numericCode",
    "textCode",
    "value"
})
public class WsNodeMetric
    extends WsNotification
{

    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar metricTime;
    protected String numericCode;
    protected String textCode;
    protected String value;

    /**
     * Gets the value of the metricTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMetricTime() {
        return metricTime;
    }

    /**
     * Sets the value of the metricTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMetricTime(XMLGregorianCalendar value) {
        this.metricTime = value;
    }

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
