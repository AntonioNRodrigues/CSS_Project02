package business.entities;

import java.util.Collection;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import org.omg.CORBA.TRANSACTION_MODE;

import business.ApplicationException;
import business.Customer;

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
	/**
	 * 
	 * @param id
	 * @return
	 * @throws ApplicationException 
	 */
	public Transation getTransation(int id) throws ApplicationException{
		EntityManager em = emf.createEntityManager();
		TypedQuery<Transation> query = em.createNamedQuery(Transation.FIND_ID, Transation.class);
		query.setParameter(Transation.FIND_ID, id);
		try {
			return query.getSingleResult();
		} catch (PersistenceException e) {
			throw new ApplicationException("Error adding the transation", e);
		} finally {
			em.close();
		}
	}
	
	public Collection<Transation> getTransations() throws ApplicationException{
		EntityManager em = emf.createEntityManager();
		try{
			TypedQuery<Transation> query = em.createQuery(Transation.FIND_ALL, Transation.class);
			return query.getResultList();
		}catch(Exception e){
			throw new ApplicationException ("Error obtaining the Transation list", e);
		}finally {
			em.close();
		}
	}
	
	public Collection<Transation> getTransations(int id_Account) throws ApplicationException{
		EntityManager em = emf.createEntityManager();
		try{
			TypedQuery<Transation> query = (TypedQuery<Transation>) em.createQuery("SELECT * FROM Transations t WHERE t.account = :"+ id_Account);
			return query.getResultList();
		}catch(Exception e){
			throw new ApplicationException ("Error obtaining the Transation list", e);
		}finally {
			em.close();
		}
	}
	

}
