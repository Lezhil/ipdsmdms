
package com.connode.service.terminal.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for wsNotificationSearchCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="wsNotificationSearchCriteria">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.terminal.service.connode.com/}wsSearch">
 *       &lt;sequence>
 *         &lt;element name="startId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="endId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="nodeId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="startSequenceNumber" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="endSequenceNumber" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "wsNotificationSearchCriteria", propOrder = {
    "startId",
    "endId",
    "nodeId",
    "startSequenceNumber",
    "endSequenceNumber"
})
@XmlSeeAlso({
    WsNodeEventSearchCriteria.class,
    WsNodeMetricSearchCriteria.class
})
public abstract class WsNotificationSearchCriteria
    extends WsSearch
{

    protected Long startId;
    protected Long endId;
    protected String nodeId;
    protected Integer startSequenceNumber;
    protected Integer endSequenceNumber;

    /**
     * Gets the value of the startId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getStartId() {
        return startId;
    }

    /**
     * Sets the value of the startId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setStartId(Long value) {
        this.startId = value;
    }

    /**
     * Gets the value of the endId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getEndId() {
        return endId;
    }

    /**
     * Sets the value of the endId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setEndId(Long value) {
        this.endId = value;
    }

    /**
     * Gets the value of the nodeId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNodeId() {
        return nodeId;
    }

    /**
     * Sets the value of the nodeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNodeId(String value) {
        this.nodeId = value;
    }

    /**
     * Gets the value of the startSequenceNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getStartSequenceNumber() {
        return startSequenceNumber;
    }

    /**
     * Sets the value of the startSequenceNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setStartSequenceNumber(Integer value) {
        this.startSequenceNumber = value;
    }

    /**
     * Gets the value of the endSequenceNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getEndSequenceNumber() {
        return endSequenceNumber;
    }

    /**
     * Sets the value of the endSequenceNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setEndSequenceNumber(Integer value) {
        this.endSequenceNumber = value;
    }

}
