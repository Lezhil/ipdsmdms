
package com.connode.service.terminal.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for wsAbstractSearchResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="wsAbstractSearchResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="totalItemCount" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="numPages" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "wsAbstractSearchResult", propOrder = {
    "totalItemCount",
    "numPages"
})
@XmlSeeAlso({
    WsNodeMetricSearchResult.class,
    WsNodeEventSearchResult.class,
    WsNodeSearchResult.class
})
public abstract class WsAbstractSearchResult {

    protected long totalItemCount;
    protected int numPages;

    /**
     * Gets the value of the totalItemCount property.
     * 
     */
    public long getTotalItemCount() {
        return totalItemCount;
    }

    /**
     * Sets the value of the totalItemCount property.
     * 
     */
    public void setTotalItemCount(long value) {
        this.totalItemCount = value;
    }

    /**
     * Gets the value of the numPages property.
     * 
     */
    public int getNumPages() {
        return numPages;
    }

    /**
     * Sets the value of the numPages property.
     * 
     */
    public void setNumPages(int value) {
        this.numPages = value;
    }

}
