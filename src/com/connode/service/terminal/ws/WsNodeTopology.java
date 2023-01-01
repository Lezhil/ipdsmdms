
package com.connode.service.terminal.ws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for wsNodeTopology complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="wsNodeTopology">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="children" type="{http://ws.terminal.service.connode.com/}wsNodeTopology" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="node" type="{http://ws.terminal.service.connode.com/}wsNode" minOccurs="0"/>
 *         &lt;element name="numberOfChildren" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numberOfHops" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "wsNodeTopology", propOrder = {
    "children",
    "node",
    "numberOfChildren",
    "numberOfHops"
})
public class WsNodeTopology {

    @XmlElement(nillable = true)
    protected List<WsNodeTopology> children;
    protected WsNode node;
    protected Integer numberOfChildren;
    protected Integer numberOfHops;

    /**
     * Gets the value of the children property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the children property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getChildren().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link WsNodeTopology }
     * 
     * 
     */
    public List<WsNodeTopology> getChildren() {
        if (children == null) {
            children = new ArrayList<WsNodeTopology>();
        }
        return this.children;
    }

    /**
     * Gets the value of the node property.
     * 
     * @return
     *     possible object is
     *     {@link WsNode }
     *     
     */
    public WsNode getNode() {
        return node;
    }

    /**
     * Sets the value of the node property.
     * 
     * @param value
     *     allowed object is
     *     {@link WsNode }
     *     
     */
    public void setNode(WsNode value) {
        this.node = value;
    }

    /**
     * Gets the value of the numberOfChildren property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumberOfChildren() {
        return numberOfChildren;
    }

    /**
     * Sets the value of the numberOfChildren property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumberOfChildren(Integer value) {
        this.numberOfChildren = value;
    }

    /**
     * Gets the value of the numberOfHops property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumberOfHops() {
        return numberOfHops;
    }

    /**
     * Sets the value of the numberOfHops property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumberOfHops(Integer value) {
        this.numberOfHops = value;
    }

}
