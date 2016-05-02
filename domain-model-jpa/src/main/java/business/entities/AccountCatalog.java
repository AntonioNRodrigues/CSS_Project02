package business.entities;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import business.ApplicationException;

public class AccountCatalog {
	private EntityManagerFactory emf;

	/**
	 * 
	 * @param emf
	 */
	public AccountCatalog(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public void addAccount(double balance) throws ApplicationException {
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			Account c = new Account(balance);
			em.persist(c);
			em.getTransaction().commit();
		} catch (Exception e) {
			throw new ApplicationException("Adding Account has failed", e);
		} finally {
			em.close();
		}
	}

}
