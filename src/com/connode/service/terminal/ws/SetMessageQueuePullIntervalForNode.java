
package com.connode.service.terminal.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for setMessageQueuePullIntervalForNode complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="setMessageQueuePullIntervalForNode">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="nodeId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="pullIntervalSeconds" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "setMessageQueuePullIntervalForNode", propOrder = {
    "nodeId",
    "pullIntervalSeconds"
})
public class SetMessageQueuePullIntervalForNode {

    @XmlElement(required = true)
    protected String nodeId;
    protected int pullIntervalSeconds;

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
     * Gets the value of the pullIntervalSeconds property.
     * 
     */
    public int getPullIntervalSeconds() {
        return pullIntervalSeconds;
    }

    /**
     * Sets the value of the pullIntervalSeconds property.
     * 
     */
    public void setPullIntervalSeconds(int value) {
        this.pullIntervalSeconds = value;
    }

}
