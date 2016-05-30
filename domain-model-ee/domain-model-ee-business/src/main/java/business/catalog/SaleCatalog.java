package business.catalog;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import business.persistence.entities.Product;
import business.persistence.entities.Sale;
import facade.exceptions.ApplicationException;

/**
 * This class represents an information specialist
 * Knows all about sales persistence
 * 
 * @author JoaoR, Simao Neves and Antonio Rodrigues
 *
 */
@Stateless
public class SaleCatalog{

	/**
	 * Entity manager based on DS
	 */
	@PersistenceContext
	private EntityManager em;
	
	/**
	 * Adds a new sale
	 * 
	 * @param sale, sale to be added
	 * @throws ApplicationException
	 */
	public void addSale(Sale sale) throws ApplicationException{	
		try{
			em.persist(sale);
		} catch (Exception e) {
			throw new ApplicationException("Error adding sale", e);
		}
		
	}
	
	/**
	 * Gets a specific sale based on its id
	 * 
	 * @param saleId, sale id
	 * @return the corresponding sale
	 * 
	 * @throws ApplicationException
	 */
	public Sale getSale(int saleId) throws ApplicationException{
		
		try {
			return em.find(Sale.class, saleId);
		} catch (Exception e) {
			throw new ApplicationException("Error getting sale", e);
		}
		
	}
	
	/**
	 * Gets all customer sales based on customer id
	 * 
	 * @param id, customer id
	 * @return a list of sales
	 * 
	 * @throws ApplicationException
	 */
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
	 * Updates a specific sale
	 * 
	 * @param sale, sale to bu updated
	 * @throws ApplicationException
	 */
	public void update(Sale sale) throws ApplicationException{
		
		try {
			em.persist(sale);
		} catch (Exception e) {
			throw new ApplicationException("Error updating sale", e);
		}
		
	}
	
	/**
	 * Adds a product to a sale
	 * 
	 * @param product, product to be added
	 * @param saleId, sale id to be considered
	 * 
	 * @throws ApplicationException
	 */
	@Transactional(Transactional.TxType.REQUIRES_NEW)
	public void addProductToSale(Product product, int saleId) throws ApplicationException{
		
		try {
			
			// get sale by id
			Sale sale = this.getSale(saleId);
			
			// persist
			em.persist(sale);
			sale.addProductToSale(product, 1);
			
		} catch (Exception e) {
			
			throw new ApplicationException("Erro ao adicionar um product a sale", e);
			
		}
		
	}
	
}
