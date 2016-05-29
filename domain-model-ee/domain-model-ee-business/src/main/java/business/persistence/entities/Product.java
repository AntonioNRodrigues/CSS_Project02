package business.persistence.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * A product 
 * 
 * @author fmartins
 * @version 1.1 (17/04/2015)
 *
 */
@Entity  
@NamedQueries({
	@NamedQuery(name=Product.FIND_BY_PRODUCT_CODE, query="SELECT p FROM Product p WHERE p.prodCod = :" + Product.PRODUCT_CODE),
	@NamedQuery(name=Product.FIND_AVAILABLE, query="SELECT p FROM Product p WHERE p.qty > 0")
})
public class Product implements Serializable{

	// Named query name constants

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String FIND_BY_PRODUCT_CODE = "Product.findByProdCod";
	public static final String PRODUCT_CODE = "prodCod";
	
	public static final String FIND_AVAILABLE = "Product.findAvailable";

	
	// Customer attributes 

	/**
	 * Customer primary key. Needed by JPA. Notice that it is not part of the
	 * original domain model.
	 */
	@Id @GeneratedValue private int id;
 
	/**
	 * The product code. This code is the one present in the product that customers have access to.
	 */	
	private int prodCod;
	
	/**
	 * Product's description 
	 */
	@SuppressWarnings("unused") private String description;
	
	/**
	 * Product's face value
	 */
	private double faceValue;

	/**
	 * Product's stock quantity
	 */
	private double qty;

	/**
	 * If the product is eligible for a discount
	 */
	private boolean discountEligibility;

	/**
	 * Product's units
	 */
	@ManyToOne private Unit unit;
	

	// 1. constructor 
	
	/**
	 * Constructor needed by JPA.
	 */
	public Product() {
	}
	
	/**
	 * Creates a new product given its code, description, face value, 
	 * stock quantity, if it is eligible for discount, and its units.
	 * 
	 * @param prodCod The product code
	 * @param description The product description
	 * @param faceValue The value by which the product should be sold
	 * @param qty The number of units available in stock
	 * @param discountEligibility If the product is eligible for discount
	 * @param unitId The units of the product quantity.	
	 */
	public Product(int prodCod, String description,
			double faceValue, double qty, boolean discountEligibility,
			Unit unit) {
		this.prodCod = prodCod;
		this.description = description;
		this.faceValue = faceValue;
		this.qty = qty;
		this.discountEligibility = discountEligibility;
		this.unit = unit;
	}
	

	// 2. getters and setters
	
	/**
	 * Comment: there is a business rule to not allow product code changes.
	 * That is why there is no method for updating the product code.
	 * 
	 * @return The code of the product.
	 */
	public int getProdCod() {
		return prodCod;
	}

	/**
	 * @return The product's face value
	 */
	public double getFaceValue() {
		return faceValue;
	}

	/**
	 * @return The product's quantity
	 */
	public double getQty() {
		return qty;
	}
	
	public int getId(){
		return this.id;
	}

	/**
	 * Updates the product's stock quantity
	 * 
	 * @param qty The new stock quantity
	 */
	public void setQty(double qty) {
		this.qty = qty;
	}

	/**
	 * @return whether the product is eligible for discount
	 */
	public boolean isEligibleForDiscount() {
		return discountEligibility;
	}	
	
	public String getDescription(){
		return this.description;
	}
}
