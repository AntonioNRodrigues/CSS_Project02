package business.persistence.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import facade.interfaces.ISaleProduct;

/**
 * A sale product.
 */
@Entity
public class SaleProduct implements ISaleProduct, Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * Sale product primary key. Needed by JPA. Notice that it is not part of
	 * the original domain model.
	 */
	@Id
	@GeneratedValue
	private int id;

	/**
	 * The product of the sale
	 */
	@ManyToOne
	private Product product;

	/**
	 * The number of items purchased
	 */
	private double qty;

	// 1. constructors

	/**
	 * Constructor needed by JPA.
	 */
	public SaleProduct() {
	}

	/**
	 * Creates a product that is part of a sale. The qty is the quantity of
	 * items in the sale.
	 * 
	 * @param produto
	 *            The product to be associated with the sale.
	 * @param qty
	 *            The number of products sold.
	 */
	public SaleProduct(Product produto, double qty) {
		this.product = produto;
		this.qty = qty;
	}

	// 2. getters and setters

	/**
	 * @return The product of the product sale
	 */
	public Product getProduct() {
		return product;
	}

	/**
	 * @return The quantity of the product sale
	 */
	public double getQty() {
		return qty;
	}
	public void setQty(double qty){
		this.qty = qty;
	}

	/**
	 * @return The sub total of the product sale
	 */
	public double getSubTotal() {
		return qty * product.getFaceValue();
	}

	/**
	 * @return The eligible sub total of the product sale
	 */
	public double getEligibleSubtotal() {
		return product.isEligibleForDiscount() ? getSubTotal() : 0;
	}


	public void setToString(String str){}
	public String getToString(){
		return this.toString();
	}

	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		String seperator = " | ";
		
		sb.append(this.product.getProdCod()); sb.append(seperator);
		sb.append(this.product.getDescription()); sb.append(seperator);
		sb.append(this.product.getFaceValue()); sb.append(seperator);
		 
		sb.append(getQty());
		sb.append(seperator);
		sb.append(this.getSubTotal());
		return sb.toString();
	}

}
