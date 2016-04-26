package business;

import java.util.List;

import dataaccess.AccountRowDataGateway;
import dataaccess.CustomerRowDataGateway;
import dataaccess.PersistenceException;

/**
 * This class represents the transaction scripts responsible
 * for business logic when it comes to deal with Accounts
 * 
 * @author Joao Rodrigues
 *
 */
public class AccountTransactionScripts {

	/**
	 * Inserts a new customer account
	 * 
	 * @param customerId, account owner
	 * @param description, account description
	 * @return new account's id
	 * @throws ApplicationException
	 */
	public int insertNewAccount(int vat, String description) throws ApplicationException{
		
		try{
			// get customer by vat number
			CustomerRowDataGateway customer = CustomerRowDataGateway.getCustomerByVATNumber(vat);

			// create new customer account 
			AccountRowDataGateway account = new AccountRowDataGateway(customer.getCustomerId(), description);
			return account.insert();	
			
		}catch(PersistenceException e){
			throw new ApplicationException("Error getting customer by vat", e);
			
		}catch(ApplicationException e){
			throw new ApplicationException("Error inserting new customers account", e);
		}
		
	}
	
	/**
	 * Gets all customer accounts
	 * 
	 * @param customerId, customer id
	 * @return a list with all available customer accounts
	 * @throws ApplicationException
	 */
	public List<AccountRowDataGateway> getCustomerAccounts(int vat) throws ApplicationException{
		try{
			// get customer by vat number
			CustomerRowDataGateway customer = CustomerRowDataGateway.getCustomerByVATNumber(vat);
			
			System.out.println("CUSTOMER ID: " + customer.getCustomerId());
			
			// get customer accounts list
			List<AccountRowDataGateway> accounts = AccountRowDataGateway.
					getAccountRowDataGatewayByCustomerId(customer.getCustomerId()); 
			
			System.out.println("Accounts length: " + accounts.size());
			
			return accounts;
			
		}catch(PersistenceException e){
			throw new ApplicationException("Error getting customer by vat", e);
		}catch(ApplicationException e){
			throw new ApplicationException("Error getting customer accounts", e);
		}
		

	}
	
}
