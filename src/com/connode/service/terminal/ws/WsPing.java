
package com.connode.service.terminal.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for wsPing complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="wsPing">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="roundtripTimeMillis" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "wsPing", propOrder = {
    "roundtripTimeMillis"
})
public class WsPing {

    protected long roundtripTimeMillis;

    /**
     * Gets the value of the roundtripTimeMillis property.
     * 
     */
    public long getRoundtripTimeMillis() {
        return roundtripTimeMillis;
    }

    /**
     * Sets the value of the roundtripTimeMillis property.
     * 
     */
    public void setRoundtripTimeMillis(long value) {
        this.roundtripTimeMillis = value;
    }

}
