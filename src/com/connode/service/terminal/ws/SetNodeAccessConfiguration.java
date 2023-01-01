
package com.connode.service.terminal.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for setNodeAccessConfiguration complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="setNodeAccessConfiguration">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="nodeAccessConfiguration" type="{http://ws.terminal.service.connode.com/}wsNodeAccessConfiguration"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "setNodeAccessConfiguration", propOrder = {
    "nodeAccessConfiguration"
})
public class SetNodeAccessConfiguration {

    @XmlElement(required = true)
    protected WsNodeAccessConfiguration nodeAccessConfiguration;

    /**
     * Gets the value of the nodeAccessConfiguration property.
     * 
     * @return
     *     possible object is
     *     {@link WsNodeAccessConfiguration }
     *     
     */
    public WsNodeAccessConfiguration getNodeAccessConfiguration() {
        return nodeAccessConfiguration;
    }

    /**
     * Sets the value of the nodeAccessConfiguration property.
     * 
     * @param value
     *     allowed object is
     *     {@link WsNodeAccessConfiguration }
     *     
     */
    public void setNodeAccessConfiguration(WsNodeAccessConfiguration value) {
        this.nodeAccessConfiguration = value;
    }

}
