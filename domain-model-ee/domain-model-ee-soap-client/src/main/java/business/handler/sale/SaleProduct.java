
package business.handler.sale;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for saleProduct complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="saleProduct">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="qty" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="toString" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "saleProduct", propOrder = {
    "qty",
    "toString"
})
public class SaleProduct {

    protected double qty;
    protected String toString;

    /**
     * Gets the value of the qty property.
     * 
     */
    public double getQty() {
        return qty;
    }

    /**
     * Sets the value of the qty property.
     * 
     */
    public void setQty(double value) {
        this.qty = value;
    }

    /**
     * Gets the value of the toString property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToString() {
        return toString;
    }

    /**
     * Sets the value of the toString property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToString(String value) {
        this.toString = value;
    }

}
