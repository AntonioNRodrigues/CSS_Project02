package business.catalog;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import business.persistence.entities.Discount;
import facade.exceptions.ApplicationException;

@Stateless
public class DiscountCatalog {

	/**
	 * Entity manager for accessing the persistence service 
	 */
	@PersistenceContext
	private EntityManager em;
	
	
	/**
	 * Finds a Discount given its type
	 * 
	 * @param discountType The discount id to find
	 * @return The discount associated with the id
	 * @throws ApplicationException When the discount type is not found
	 */
	public Discount getDiscount (int discountType) throws ApplicationException {
		try {
			TypedQuery<Discount> query = em.createNamedQuery(Discount.FIND_BY_TYPE, Discount.class);
			query.setParameter(Discount.FIND_BY_TYPE_ID, discountType);
			return query.getSingleResult();
		} catch (Exception e) {
			throw new ApplicationException ("Discount type " + discountType + " not found.");
		}
	}

	/**
	 * @return The available discounts
	 * @throws ApplicationException When there is an error obtaining the discount list.
	 */
	public List<Discount> getDiscounts() throws ApplicationException {
		try {
			TypedQuery<Discount> query = em.createNamedQuery(Discount.FIND_ALL, Discount.class);
			return query.getResultList();
		} catch (Exception e) {
			throw new ApplicationException ("Error obtaining the discounts list", e);
		}
	}
}
