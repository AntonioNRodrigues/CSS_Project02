package business.catalog;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import business.persistence.entities.Sale;
import facade.exceptions.ApplicationException;
import facade.handlers.ISaleHandlerRemote;
import facade.interfaces.ISale;

@Stateless
public class SaleCatalog{

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
	
	public List<Sale> getCustomerSalesByID(int id) throws ApplicationException{
		
		try {
			TypedQuery<Sale> q = em.createNamedQuery(Sale.FIND_CUSTOMER_SALES, Sale.class);
			q.setParameter(Sale.CUSTOMER_ID, id);
			return q.getResultList();
			
		} catch (Exception e) {
			throw new ApplicationException("Erro ao obter customer sales por id", e);
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
