
package business.handler.sale;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for sale complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="sale">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="customer" type="{http://sale.handler.business/}customer" minOccurs="0"/>
 *         &lt;element name="discountValue" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="saleProducts" type="{http://sale.handler.business/}saleProduct" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="totalValue" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="transactions" type="{http://sale.handler.business/}transaction" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sale", propOrder = {
    "customer",
    "discountValue",
    "saleProducts",
    "totalValue",
    "transactions"
})
public class Sale {

    protected Customer customer;
    protected double discountValue;
    @XmlElement(nillable = true)
    protected List<SaleProduct> saleProducts;
    protected double totalValue;
    @XmlElement(nillable = true)
    protected List<Transaction> transactions;

    /**
     * Gets the value of the customer property.
     * 
     * @return
     *     possible object is
     *     {@link Customer }
     *     
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Sets the value of the customer property.
     * 
     * @param value
     *     allowed object is
     *     {@link Customer }
     *     
     */
    public void setCustomer(Customer value) {
        this.customer = value;
    }

    /**
     * Gets the value of the discountValue property.
     * 
     */
    public double getDiscountValue() {
        return discountValue;
    }

    /**
     * Sets the value of the discountValue property.
     * 
     */
    public void setDiscountValue(double value) {
        this.discountValue = value;
    }

    /**
     * Gets the value of the saleProducts property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the saleProducts property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSaleProducts().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SaleProduct }
     * 
     * 
     */
    public List<SaleProduct> getSaleProducts() {
        if (saleProducts == null) {
            saleProducts = new ArrayList<SaleProduct>();
        }
        return this.saleProducts;
    }

    /**
     * Gets the value of the totalValue property.
     * 
     */
    public double getTotalValue() {
        return totalValue;
    }

    /**
     * Sets the value of the totalValue property.
     * 
     */
    public void setTotalValue(double value) {
        this.totalValue = value;
    }

    /**
     * Gets the value of the transactions property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the transactions property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTransactions().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Transaction }
     * 
     * 
     */
    public List<Transaction> getTransactions() {
        if (transactions == null) {
            transactions = new ArrayList<Transaction>();
        }
        return this.transactions;
    }

}
