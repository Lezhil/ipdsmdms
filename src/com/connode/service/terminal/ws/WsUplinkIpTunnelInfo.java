
package com.connode.service.terminal.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for wsUplinkIpTunnelInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="wsUplinkIpTunnelInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="establishTime" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="prefix" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="remoteAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="remotePort" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="rxBytes" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="txBytes" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "wsUplinkIpTunnelInfo", propOrder = {
    "establishTime",
    "prefix",
    "remoteAddress",
    "remotePort",
    "rxBytes",
    "txBytes"
})
public class WsUplinkIpTunnelInfo {

    protected int establishTime;
    protected String prefix;
    protected String remoteAddress;
    protected int remotePort;
    protected long rxBytes;
    protected long txBytes;

    /**
     * Gets the value of the establishTime property.
     * 
     */
    public int getEstablishTime() {
        return establishTime;
    }

    /**
     * Sets the value of the establishTime property.
     * 
     */
    public void setEstablishTime(int value) {
        this.establishTime = value;
    }

    /**
     * Gets the value of the prefix property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * Sets the value of the prefix property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrefix(String value) {
        this.prefix = value;
    }

    /**
     * Gets the value of the remoteAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemoteAddress() {
        return remoteAddress;
    }

    /**
     * Sets the value of the remoteAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemoteAddress(String value) {
        this.remoteAddress = value;
    }

    /**
     * Gets the value of the remotePort property.
     * 
     */
    public int getRemotePort() {
        return remotePort;
    }

    /**
     * Sets the value of the remotePort property.
     * 
     */
    public void setRemotePort(int value) {
        this.remotePort = value;
    }

    /**
     * Gets the value of the rxBytes property.
     * 
     */
    public long getRxBytes() {
        return rxBytes;
    }

    /**
     * Sets the value of the rxBytes property.
     * 
     */
    public void setRxBytes(long value) {
        this.rxBytes = value;
    }

    /**
     * Gets the value of the txBytes property.
     * 
     */
    public long getTxBytes() {
        return txBytes;
    }

    /**
     * Sets the value of the txBytes property.
     * 
     */
    public void setTxBytes(long value) {
        this.txBytes = value;
    }

}
