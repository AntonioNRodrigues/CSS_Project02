package business;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

public class TransactionCatalog {

	private EntityManagerFactory emf;
	
	public TransactionCatalog(EntityManagerFactory emf){
		this.emf = emf;
	}
	
	/**
	 * Adds a transaction based on sale information
	 * 
	 * @param sale, sale to be considered
	 * @param type, transaction type to be inserted
	 */
	public void addCreditTransaction(Sale sale, TransactionType type){
		
		EntityManager em = emf.createEntityManager();
		
		try{
			// begin transaction
			em.getTransaction().begin();
			
			// create transaction object
			Transaction transaction = new Transaction();
			transaction.setSale(sale);
			transaction.setType(type);
			transaction.setDate(new Date());
			transaction.setValue(sale.getTotal() - sale.getDiscount());
			em.persist(transaction);
			
			// commit
			em.getTransaction().commit();
			
		} catch (Exception e) {
			if(em.getTransaction().isActive())
				em.getTransaction().rollback();
			System.out.println("Error inserting transaction");
		}
		
	}

	public void addDebitTransaction(Sale sale, double value) {
		
		EntityManager em = emf.createEntityManager();
		
		try{
			
			em.getTransaction().begin();
			
			Transaction transaction = new Transaction();
			transaction.setDate(new Date());
			transaction.setSale(sale);
			transaction.setType(TransactionType.DEBIT);
			transaction.setValue(value);
			em.persist(transaction);
			em.getTransaction().commit();
			
		} catch (Exception e) {}
		
	}
	
	
	public Transaction findById(int transactionId) throws ApplicationException{
		
		EntityManager em = this.emf.createEntityManager();
		
		try{
			
			TypedQuery<Transaction> query = em.createNamedQuery(Transaction.GET_BY_ID, Transaction.class);
			query.setParameter(Transaction.ID, transactionId);
			return query.getResultList().get(0);
			
		} catch (Exception e) {
			throw new ApplicationException("", e);
		}
		
	}
	
}
