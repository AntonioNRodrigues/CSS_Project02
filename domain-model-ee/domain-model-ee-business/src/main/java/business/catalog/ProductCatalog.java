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
	
	/**
	 * 
	 * @return
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
	 * 
	 * @param prodCod
	 * @return
	 * @throws ApplicationException
	 */
	public Product getProductByCod(int prodCod) throws ApplicationException{
		
		try {
			TypedQuery<Product> query = em.createNamedQuery(Product.FIND_BY_PRODUCT_CODE, Product.class);
			query.setParameter(Product.PRODUCT_CODE, prodCod);
			Product product = query.getSingleResult();
			return product;
		} catch (Exception e) {
			throw new ApplicationException("Erro ao obter product by code", e);
		}
		
	}
	
}
