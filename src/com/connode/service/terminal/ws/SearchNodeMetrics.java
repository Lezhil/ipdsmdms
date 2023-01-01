
package com.connode.service.terminal.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for searchNodeMetrics complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="searchNodeMetrics">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="searchCriteria" type="{http://ws.terminal.service.connode.com/}wsNodeMetricSearchCriteria"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "searchNodeMetrics", propOrder = {
    "searchCriteria"
})
public class SearchNodeMetrics {

    @XmlElement(required = true)
    protected WsNodeMetricSearchCriteria searchCriteria;

    /**
     * Gets the value of the searchCriteria property.
     * 
     * @return
     *     possible object is
     *     {@link WsNodeMetricSearchCriteria }
     *     
     */
    public WsNodeMetricSearchCriteria getSearchCriteria() {
        return searchCriteria;
    }

    /**
     * Sets the value of the searchCriteria property.
     * 
     * @param value
     *     allowed object is
     *     {@link WsNodeMetricSearchCriteria }
     *     
     */
    public void setSearchCriteria(WsNodeMetricSearchCriteria value) {
        this.searchCriteria = value;
    }

}
