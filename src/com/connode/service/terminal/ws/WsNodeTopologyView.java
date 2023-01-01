
package com.connode.service.terminal.ws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for wsNodeTopologyView complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="wsNodeTopologyView">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="originNode" type="{http://ws.terminal.service.connode.com/}wsNodeTopology" minOccurs="0"/>
 *         &lt;element name="topologies" type="{http://ws.terminal.service.connode.com/}wsNodeTopology" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "wsNodeTopologyView", propOrder = {
    "originNode",
    "topologies"
})
public class WsNodeTopologyView {

    protected WsNodeTopology originNode;
    @XmlElement(nillable = true)
    protected List<WsNodeTopology> topologies;

    /**
     * Gets the value of the originNode property.
     * 
     * @return
     *     possible object is
     *     {@link WsNodeTopology }
     *     
     */
    public WsNodeTopology getOriginNode() {
        return originNode;
    }

    /**
     * Sets the value of the originNode property.
     * 
     * @param value
     *     allowed object is
     *     {@link WsNodeTopology }
     *     
     */
    public void setOriginNode(WsNodeTopology value) {
        this.originNode = value;
    }

    /**
     * Gets the value of the topologies property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the topologies property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTopologies().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link WsNodeTopology }
     * 
     * 
     */
    public List<WsNodeTopology> getTopologies() {
        if (topologies == null) {
            topologies = new ArrayList<WsNodeTopology>();
        }
        return this.topologies;
    }

}
