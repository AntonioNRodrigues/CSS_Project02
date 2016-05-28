package business.catalog;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import business.persistence.entities.Product;
import facade.exceptions.ApplicationException;

@Stateless
public class ProductCatalog {

	@PersistenceContext
	private EntityManager em;
	
	public List<Product> getAvailableProducts() throws ApplicationException{
		
		try {
			
			TypedQuery<Product> query = em.createNamedQuery(Product.FIND_AVAILABLE, Product.class);
			return query.getResultList();
			
		} catch (Exception e) {
			throw new ApplicationException("", e);
		}
		
	}
	
}
