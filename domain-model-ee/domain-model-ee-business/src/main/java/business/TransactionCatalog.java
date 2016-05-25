package business;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import facade.exceptions.ApplicationException;

@Stateless
public class TransactionCatalog {

	@PersistenceContext
	private EntityManager em;
	
	public void addTransaction(Transaction t) throws ApplicationException{
		
		try{
			em.persist(t);
		} catch (Exception e) {
			throw new ApplicationException("Error adding transaction", e);
		}
		
	}
	
}
