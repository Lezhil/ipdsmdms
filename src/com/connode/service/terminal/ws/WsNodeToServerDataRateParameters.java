
package com.connode.service.terminal.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for wsNodeToServerDataRateParameters complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="wsNodeToServerDataRateParameters">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="size" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="blockSize" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="numConcurrentRequests" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "wsNodeToServerDataRateParameters", propOrder = {
    "size",
    "blockSize",
    "numConcurrentRequests"
})
public class WsNodeToServerDataRateParameters {

    protected int size;
    protected int blockSize;
    protected int numConcurrentRequests;

    /**
     * Gets the value of the size property.
     * 
     */
    public int getSize() {
        return size;
    }

    /**
     * Sets the value of the size property.
     * 
     */
    public void setSize(int value) {
        this.size = value;
    }

    /**
     * Gets the value of the blockSize property.
     * 
     */
    public int getBlockSize() {
        return blockSize;
    }

    /**
     * Sets the value of the blockSize property.
     * 
     */
    public void setBlockSize(int value) {
        this.blockSize = value;
    }

    /**
     * Gets the value of the numConcurrentRequests property.
     * 
     */
    public int getNumConcurrentRequests() {
        return numConcurrentRequests;
    }

    /**
     * Sets the value of the numConcurrentRequests property.
     * 
     */
    public void setNumConcurrentRequests(int value) {
        this.numConcurrentRequests = value;
    }

}
