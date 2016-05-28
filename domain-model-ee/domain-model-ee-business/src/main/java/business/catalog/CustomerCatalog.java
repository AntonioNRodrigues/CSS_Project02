package business.catalog;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import business.persistence.entities.Account;
import business.persistence.entities.Customer;
import business.persistence.entities.Discount;
import facade.exceptions.ApplicationException;

/**
 * A catalog of customers
 * 
 * @author fmartins
 * @version 1.1 (17/04/2015)
 *
 */
@Stateless
public class CustomerCatalog {

	/**
	 * Entity manager for accessing the persistence service 
	 */
	@PersistenceContext
	private EntityManager em;
	
	/**
	 * Finds a customer given its VAT number.
	 * 
	 * @param vat The VAT number of the customer to fetch from the repository
	 * @return The Customer object corresponding to the customer with the vat number.
	 * @throws ApplicationException When the customer with the vat number is not found.
	 */
	public Customer getCustomer (int vat) throws ApplicationException {
		TypedQuery<Customer> query = em.createNamedQuery(Customer.FIND_BY_VAT_NUMBER, Customer.class);
		query.setParameter(Customer.NUMBER_VAT_NUMBER, vat);
		try {
			return query.getSingleResult();
		} catch (PersistenceException e) {
			throw new ApplicationException ("Customer not found.");
		}
	}
	
	/**
	 * Adds a new customer
	 * 
	 * @param vat The customer's VAT number
	 * @param designation The customer's designation
	 * @param phoneNumber The customer's phone number
	 * @param discountType The customer's discount type
	 * @throws ApplicationException When the customer is already in the repository or the 
	 * vat number is invalid.
	 */
	@Transactional(Transactional.TxType.REQUIRES_NEW)
	public void addCustomer (int vat, String designation, int phoneNumber, Discount discountType) 
			throws ApplicationException {
		Customer customer = new Customer(vat, designation, phoneNumber, discountType);
		Account account = new Account(customer, null, null);
		customer.setAccount(account);
		em.persist(customer);
		em.persist(account);
	}
	
	/**
	 * Get all customers 
	 * 
	 * @return a list of a customer
	 * @throws ApplicationException
	 */
	public List<Customer> getAll() throws ApplicationException{
		
		try{
			
			// get all customers
			TypedQuery<Customer> customersQuery = em.createNamedQuery(Customer.FIND_ALL, Customer.class);
			List<Customer> customers = customersQuery.getResultList();
			
			return customers;
			
		} catch (Exception e) {
			System.out.println("========================================");
			e.printStackTrace();
			throw new ApplicationException("Error getting customers", e);
		}
		
	}
	
	public Customer getCustomerById(int id) throws ApplicationException{
		
		try {
			
			return em.find(Customer.class, id);
			
		} catch (Exception e) {
			throw new ApplicationException("Catalog: Erro ao obter customer por id", e);
		}
		
	}
}
