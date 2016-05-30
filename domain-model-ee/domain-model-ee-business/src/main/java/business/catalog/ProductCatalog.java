package business.catalog;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import business.persistence.entities.Product;
import facade.exceptions.ApplicationException;

/**
 * This class represents an information specialist
 * Knows all about products persistence
 * 
 * @author JoaoR, Simao Neves and Antonio Rodrigues
 *
 */
@Stateless
public class ProductCatalog {

	@PersistenceContext
	private EntityManager em;
	
	/**
	 * Gets all available products
	 * 
	 * @return a list with all available products
	 * @throws ApplicationException
	 */
	public List<Product> getAvailableProducts() throws ApplicationException{
		
		try {
			
			TypedQuery<Product> query = em.createNamedQuery(Product.FIND_AVAILABLE, Product.class);
			return query.getResultList();
			
		} catch (Exception e) {
			throw new ApplicationException("", e);
		}
		
	}

	/**
	 * Gets a specific product based on its product code
	 * 
	 * @param prodCod, product code
	 * @return the corresponding product
	 * 
	 * @throws ApplicationException
	 */
	public Product getProductByCod(int prodCod) throws ApplicationException{
		
		try {
			
			// prepare query
			TypedQuery<Product> query = em.createNamedQuery(Product.FIND_BY_PRODUCT_CODE, Product.class);
			query.setParameter(Product.PRODUCT_CODE, prodCod);
			
			// execute it
			Product product = query.getSingleResult();
			return product;
		} catch (Exception e) {
			throw new ApplicationException("Erro ao obter product by code", e);
		}
		
	}
	
}
