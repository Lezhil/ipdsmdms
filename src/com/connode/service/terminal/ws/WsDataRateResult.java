
package com.connode.service.terminal.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for wsDataRateResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="wsDataRateResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="durationMillis" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="maxRoundtripTimeMillis" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="minRoundtripTimeMillis" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="missingBlocks" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="receivedSize" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "wsDataRateResult", propOrder = {
    "durationMillis",
    "maxRoundtripTimeMillis",
    "minRoundtripTimeMillis",
    "missingBlocks",
    "receivedSize"
})
public class WsDataRateResult {

    protected long durationMillis;
    protected long maxRoundtripTimeMillis;
    protected long minRoundtripTimeMillis;
    protected long missingBlocks;
    protected long receivedSize;

    /**
     * Gets the value of the durationMillis property.
     * 
     */
    public long getDurationMillis() {
        return durationMillis;
    }

    /**
     * Sets the value of the durationMillis property.
     * 
     */
    public void setDurationMillis(long value) {
        this.durationMillis = value;
    }

    /**
     * Gets the value of the maxRoundtripTimeMillis property.
     * 
     */
    public long getMaxRoundtripTimeMillis() {
        return maxRoundtripTimeMillis;
    }

    /**
     * Sets the value of the maxRoundtripTimeMillis property.
     * 
     */
    public void setMaxRoundtripTimeMillis(long value) {
        this.maxRoundtripTimeMillis = value;
    }

    /**
     * Gets the value of the minRoundtripTimeMillis property.
     * 
     */
    public long getMinRoundtripTimeMillis() {
        return minRoundtripTimeMillis;
    }

    /**
     * Sets the value of the minRoundtripTimeMillis property.
     * 
     */
    public void setMinRoundtripTimeMillis(long value) {
        this.minRoundtripTimeMillis = value;
    }

    /**
     * Gets the value of the missingBlocks property.
     * 
     */
    public long getMissingBlocks() {
        return missingBlocks;
    }

    /**
     * Sets the value of the missingBlocks property.
     * 
     */
    public void setMissingBlocks(long value) {
        this.missingBlocks = value;
    }

    /**
     * Gets the value of the receivedSize property.
     * 
     */
    public long getReceivedSize() {
        return receivedSize;
    }

    /**
     * Sets the value of the receivedSize property.
     * 
     */
    public void setReceivedSize(long value) {
        this.receivedSize = value;
    }

}
