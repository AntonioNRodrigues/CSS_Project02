package business;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import facade.exceptions.ApplicationException;

@Stateless
public class SaleCatalog {

	@PersistenceContext
	private EntityManager em;
	
	public void addSale(Sale sale) throws ApplicationException{	
		try{
			em.persist(sale);
		} catch (Exception e) {
			throw new ApplicationException("Error adding sale", e);
		}
		
	}
	
	public Sale getSale(int saleId) throws ApplicationException{
		
		try {
			return em.find(Sale.class, saleId);
		} catch (Exception e) {
			throw new ApplicationException("Error getting sale", e);
		}
		
	}
	
	/**
	 * 
	 * @param sale
	 * @throws ApplicationException
	 */
	public void update(Sale sale) throws ApplicationException{
		
		try {
			em.persist(sale);
		} catch (Exception e) {
			throw new ApplicationException("Error updating sale", e);
		}
		
	}
	
}
