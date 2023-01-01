
package com.connode.service.terminal.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for wsNodeAccessConfiguration complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="wsNodeAccessConfiguration">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="whiteListEnabled" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "wsNodeAccessConfiguration", propOrder = {
    "whiteListEnabled"
})
public class WsNodeAccessConfiguration {

    protected boolean whiteListEnabled;

    /**
     * Gets the value of the whiteListEnabled property.
     * 
     */
    public boolean isWhiteListEnabled() {
        return whiteListEnabled;
    }

    /**
     * Sets the value of the whiteListEnabled property.
     * 
     */
    public void setWhiteListEnabled(boolean value) {
        this.whiteListEnabled = value;
    }

}
