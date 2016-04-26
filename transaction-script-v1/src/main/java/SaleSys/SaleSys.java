package SaleSys;

import business.AccountTransactionScripts;
import business.ApplicationException;
import business.CustomerTransactionScripts;
import business.SaleTransactionScripts;
import dataaccess.DataSource;
import dataaccess.PersistenceException;
import presentation.AccountService;
import presentation.CustomerService;
import presentation.SaleService;

public class SaleSys {

	private CustomerService customerService;
	private SaleService saleService;
	private AccountService accountService;

	public void run() throws ApplicationException {
		// Connects to the database
		try {
			DataSource.INSTANCE.connect("jdbc:derby:data/derby/cssdb;create=false", "SaleSys", "");
			customerService = new CustomerService(new CustomerTransactionScripts());
			saleService = new SaleService(new SaleTransactionScripts());
			accountService = new AccountService(new AccountTransactionScripts());
		} catch (PersistenceException e) {
			throw new ApplicationException("Error connecting database", e);
		}
	}
	
	public void stopRun()  {
		// Closes the database connection
		DataSource.INSTANCE.close();
	}

	public CustomerService getCustomerService() {
		return customerService;
	}
	
	public SaleService getSaleService() {
		return saleService;
	}

	public AccountService getAccountService() {
		return this.accountService;
	}
}
