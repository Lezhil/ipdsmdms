
package com.connode.service.terminal.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getNodeMetrics complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getNodeMetrics">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="startId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="itemCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getNodeMetrics", propOrder = {
    "startId",
    "itemCount"
})
public class GetNodeMetrics {

    protected long startId;
    protected int itemCount;

    /**
     * Gets the value of the startId property.
     * 
     */
    public long getStartId() {
        return startId;
    }

    /**
     * Sets the value of the startId property.
     * 
     */
    public void setStartId(long value) {
        this.startId = value;
    }

    /**
     * Gets the value of the itemCount property.
     * 
     */
    public int getItemCount() {
        return itemCount;
    }

    /**
     * Sets the value of the itemCount property.
     * 
     */
    public void setItemCount(int value) {
        this.itemCount = value;
    }

}
