
package com.connode.service.terminal.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for wsRestart complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="wsRestart">
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
@XmlType(name = "wsRestart", propOrder = {
    "roundtripTimeMillis"
})
public class WsRestart {

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
