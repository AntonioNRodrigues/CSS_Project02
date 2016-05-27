package business;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.STRING;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import facade.exceptions.ApplicationException;
import facade.interfaces.ISale;

/**
 * A sale
 *	
 * @author fmartins
 * @version 1.1 (17/04/2015)
 * 
 */
@Entity 
@NamedQueries({
	@NamedQuery(name=Sale.FIND_CUSTOMER_SALES, query="SELECT s FROM Sale s WHERE s.customer.id =:"+Sale.CUSTOMER_ID)
})
public class Sale implements ISale, Serializable {

	
	
	public final static String FIND_CUSTOMER_SALES = "Sales.customerSales";
	public final static String CUSTOMER_ID = "customerID";
	
	/**
	 * Sale primary key. Needed by JPA. Notice that it is not part of the
	 * original domain model.
	 */
	@Id	@GeneratedValue private int id;
	
	/**
	 * The date the sale was made 
	 */
	@Temporal(TemporalType.DATE) private Date date;

	/**
	 * Whether the sale is open or closed. 
	 */
	@Enumerated(STRING) private SaleStatus status;
	
	/**
	 * Sale owner
	 */
	@ManyToOne private Customer customer;
	
	private double totalValue;
	
	private double discountValue;
	
	/**
	 * The products of the sale
	 */
	@OneToMany(cascade = ALL) @JoinColumn
	private List<SaleProduct> saleProducts;
		
	@OneToMany(mappedBy="sale")
	private List<Transaction> transactions;
	
	// 1. constructor

	/**
	 * Constructor needed by JPA.
	 */
	Sale () {
	}
	
	/**
	 * Creates a new sale given the date it occurred and the customer that
	 * made the purchase.
	 * 
	 * @param date The date that the sale occurred
	 * @param customer The customer that made the purchase
	 */
	public Sale(Date date, Customer customer) {
		this.date = date;
		this.customer = customer;
		this.status = SaleStatus.OPEN;
		this.saleProducts = new LinkedList<SaleProduct>();
	}

	
	// 2. getters and setters
	
	public void setTotalValue(double totalValue){
		this.totalValue = totalValue;
	}
	public double getTotalValue(){
		return this.totalValue;
	}
	
	public void setDiscountValue(double discountValue){
		this.discountValue = discountValue;
	}
	public double getDiscountValue(){
		return this.discountValue;
	}
	
	/**
	 * @return The sale's total 
	 */
	public double total() {
		double total = 0;
		for (SaleProduct sp : saleProducts)
			total += sp.getSubTotal();
		return total;
	}

	/**
	 * @return The sale's amount eligible for discount
	 */
	public double eligibleDiscountTotal () {
		double total = 0;
		for (SaleProduct sp : saleProducts)
			total += sp.getEligibleSubtotal();
		return total;
	}
	
	/**
	 * @return Computes the sale's discount amount based on the discount type of the customer.
	 */
	public double discount () {
		Discount discount = customer.getDiscountType();
		return discount.computeDiscount(this);
	}

	@Override
	public Customer getCustomer() {
		return customer;
	}
	/**
	 * @return Whether the sale is open
	 */
	public boolean isOpen() {
		return status == SaleStatus.OPEN;
	}
	
	public boolean isClosed() {
		return status == SaleStatus.CLOSED;
	}
	
	public boolean isPayed() {
		return status == SaleStatus.PAYED;
	}

	/**
	 * Adds a product to the sale
	 * 
	 * @param product The product to sale
	 * @param qty The amount of the product being sold
	 * @throws ApplicationException 
	 */
	public void addProductToSale(Product product, double qty) 
			throws ApplicationException {
		if (!isOpen())
			throw new ApplicationException("Cannot add products to a closed sale.");

		// if there is enough stock
		if (product.getQty() >= qty) {
			// adds product to sale
			product.setQty(product.getQty() - qty);
			saleProducts.add(new SaleProduct(product, qty));
		} else
			throw new ApplicationException("Product " + product.getProdCod() + " has stock ("  + 
							product.getQty() + ") which is insuficient for the current sale");
	}

	public int getId() {
		return id;
	}
	
	public void setTransactions(List<Transaction> transactions){
		this.transactions = transactions;
	}
	public List<Transaction> getTransactions(){
		return this.transactions;
	}
	
	public void setStatus(SaleStatus status){
		this.status = status;
	}
	
	public double calculateTotal(){
		
		double total = 0;
		
		List<SaleProduct> saleProducts = this.saleProducts;
		for(SaleProduct sp : saleProducts)
			total += sp.getQty() * sp.getProduct().getFaceValue();
		
		return total;
		
	}
}
