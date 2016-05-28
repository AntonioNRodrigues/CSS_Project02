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
