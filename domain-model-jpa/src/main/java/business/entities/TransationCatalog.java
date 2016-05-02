package business.entities;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import business.ApplicationException;

public class TransationCatalog {

	private EntityManagerFactory emf;

	/**
	 * 
	 * @param emf
	 */
	public TransationCatalog(EntityManagerFactory emf) {
		this.emf = emf;
	}

	/**
	 * 
	 * @param trans
	 * @throws ApplicationException
	 */
	public void addTransation(Transation trans) throws ApplicationException {
		EntityManager em = emf.createEntityManager();

		try {
			em.getTransaction().begin();
			// The object trans has passed to managed
			em.merge(trans);
			// commit the transation
			em.getTransaction().commit();
		} catch (Exception e) {
			throw new ApplicationException("Error adding the transation", e);
		} finally {
			em.close();
		}
	}

	/**
	 * 
	 * @param ctr
	 * @param value
	 * @param date
	 * @throws ApplicationException
	 */
	public void addTransation(String ctr, double value, Date date) throws ApplicationException {
		Transation trans = Transation.factory(ctr, value, date);
		addTransation(trans);
	}

}
