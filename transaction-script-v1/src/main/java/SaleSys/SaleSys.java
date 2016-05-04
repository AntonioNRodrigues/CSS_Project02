package SaleSys;

import business.ApplicationException;
import business.CustomerTransactionScripts;
import business.SaleTransactionScripts;
import business.SaleTransactionTransactionScripts;
import dataaccess.DataSource;
import dataaccess.PersistenceException;
import presentation.CustomerService;
import presentation.SaleService;
import presentation.TransactionService;

public class SaleSys {

	private CustomerService customerService;
	private SaleService saleService;
	private TransactionService transactionService;

	public void run() throws ApplicationException {
		// Connects to the database
		try {
			DataSource.INSTANCE.connect("jdbc:derby:data/derby/cssdb;create=false", "SaleSys", "");
			customerService = new CustomerService(new CustomerTransactionScripts());
			saleService = new SaleService(new SaleTransactionScripts());
			transactionService = new TransactionService(new SaleTransactionTransactionScripts());
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
	
	public TransactionService getTransactionService(){
		return transactionService;
	}
}
