package business.persistence.entities;

import static javax.persistence.InheritanceType.SINGLE_TABLE;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlSeeAlso;

import facade.interfaces.IDiscount;

/**
 * A customer discount
 * 
 * @author fmartins
 * @version 1.1 (17/04/2015)
 */
@Entity
@NamedQueries({
	@NamedQuery(name=Discount.FIND_BY_TYPE, query="SELECT d FROM Discount d WHERE d.id = :" + Discount.FIND_BY_TYPE_ID),
	@NamedQuery(name=Discount.FIND_ALL, query="SELECT d FROM Discount d")
})
@Inheritance(strategy = SINGLE_TABLE)
@XmlSeeAlso({EligibleProductsDiscount.class, NoDiscount.class, ThresholdPercentageDiscount.class})
public abstract class Discount implements IDiscount {
	
	/**
	 * Serialization Id
	 */
	private static final long serialVersionUID = -8960974792466945423L;
	
	// Named query name constants
	public static final String FIND_BY_TYPE = "Discount.findByType";
	public static final String FIND_ALL = "Discount.findAll";
	public static final String FIND_BY_TYPE_ID = "id";
	
	/**
	 * The discount type. 
	 */
	@Id	private int id;

	/**
	 * Discount description
	 */
	private String description;
	
	/**
	 * Constructor needed by JPA 
	 */
	public Discount() {
	}

	/**
	 * Creates a discount type given its description
	 * 
	 * @param description The discount description
	 */
	public Discount(int id, String description) {
		this.id = id;
		this.description = description;
	}

	/**
	 * @return The discount description
	 */
	public String getDescription() {
		return description;
	}

	public int getId() {
		return id;
	}
	
	/**
	 * @param sale The sale to compute the discount on
	 * @return The discount amount using the current discount type
	 */
	public abstract double computeDiscount(Sale sale);
}
 