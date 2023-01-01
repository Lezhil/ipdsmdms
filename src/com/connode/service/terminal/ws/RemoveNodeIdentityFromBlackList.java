
package com.connode.service.terminal.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for removeNodeIdentityFromBlackList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="removeNodeIdentityFromBlackList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="nodeIdentity" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "removeNodeIdentityFromBlackList", propOrder = {
    "nodeIdentity"
})
public class RemoveNodeIdentityFromBlackList {

    @XmlElement(required = true)
    protected String nodeIdentity;

    /**
     * Gets the value of the nodeIdentity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNodeIdentity() {
        return nodeIdentity;
    }

    /**
     * Sets the value of the nodeIdentity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNodeIdentity(String value) {
        this.nodeIdentity = value;
    }

}
