package business.persistence.entities;

import javax.persistence.Entity;

/**
 * No discount whatsoever
 * 
 * @author fmartins
 * @version 1.1 (17/04/2015)
 *
 */
@Entity
public class NoDiscount extends Discount {

	/**
	 * Serialization Id
	 */
	private static final long serialVersionUID = 7526557935138337494L;

	/**
	 * Constructor needed by JPA 
	 */
	NoDiscount() {
	}
	
	/**
	 * Creates a discount that always computes to 0 irrespectively of 
	 * the sale.
	 * 
	 * @param discountId The id of the discount
	 * @param description The discount description
	 */
	public NoDiscount (int discountId, String description) {
		super(discountId, description);
	}
	
	@Override
	public double computeDiscount(Sale sale) {	
		return 0;	
	}
	
}
