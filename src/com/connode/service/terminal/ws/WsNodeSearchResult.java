
package com.connode.service.terminal.ws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for wsNodeSearchResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="wsNodeSearchResult">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.terminal.service.connode.com/}wsAbstractSearchResult">
 *       &lt;sequence>
 *         &lt;element name="items" type="{http://ws.terminal.service.connode.com/}wsNode" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="search" type="{http://ws.terminal.service.connode.com/}wsNodeSearch" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "wsNodeSearchResult", propOrder = {
    "items",
    "search"
})
public class WsNodeSearchResult
    extends WsAbstractSearchResult
{

    @XmlElement(nillable = true)
    protected List<WsNode> items;
    protected WsNodeSearch search;

    /**
     * Gets the value of the items property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the items property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getItems().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link WsNode }
     * 
     * 
     */
    public List<WsNode> getItems() {
        if (items == null) {
            items = new ArrayList<WsNode>();
        }
        return this.items;
    }

    /**
     * Gets the value of the search property.
     * 
     * @return
     *     possible object is
     *     {@link WsNodeSearch }
     *     
     */
    public WsNodeSearch getSearch() {
        return search;
    }

    /**
     * Sets the value of the search property.
     * 
     * @param value
     *     allowed object is
     *     {@link WsNodeSearch }
     *     
     */
    public void setSearch(WsNodeSearch value) {
        this.search = value;
    }

}
