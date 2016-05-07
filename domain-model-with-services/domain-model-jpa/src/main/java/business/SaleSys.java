package business;

import javax.persistence.EntityManagerFactory;

/**
 * The initial object. The first domain object built during the startup.
 * 
 * @author fmartins
 * @version 1.0 (17/04/2015)
 *
 */
public class SaleSys {

	/**
	 * The add customer use case handler
	 */
	private AddCustomerHandler addCustomerHandler;
	
	/**
	 * The process sale use case handler 
	 */
	private ProcessSaleHandler processSaleHandler;
	
	
	private GetCustomerTransactionHandler getCustomerTransactionsHandler;
	
	private RegistTransactionHandler registTransactionHandler;
	
	/**
	 * Performs the start up use case
	 */
	public SaleSys(EntityManagerFactory emf) {
		CustomerCatalog customerCatalog = new CustomerCatalog(emf);
		SaleCatalog saleCatalog = new SaleCatalog(emf);
		
		// transactions handler
		TransactionCatalog transactionCatalog = new TransactionCatalog(emf);
		this.registTransactionHandler = new RegistTransactionHandler(transactionCatalog, saleCatalog, customerCatalog);

		this.getCustomerTransactionsHandler = new GetCustomerTransactionHandler(customerCatalog, new TransactionCatalog(emf));
		
		// add customer handler
		this.addCustomerHandler = new AddCustomerHandler(customerCatalog, new DiscountCatalog(emf));
		
		// process sale handler
		this.processSaleHandler = new ProcessSaleHandler(
				saleCatalog, customerCatalog, 
				new ProductCatalog(emf),
				new TransactionCatalog(emf));
	
		
	}
	
	public GetCustomerTransactionHandler getCusTomerTransactionHandler(){
		return this.getCustomerTransactionsHandler;
	}
	
	
	/**
	 * @return The add customer use case handler
	 */
	public AddCustomerHandler getAddCustomerHandler () {
		return addCustomerHandler;
	}

	/**
	 * @return The process sale use case handler
	 */
	public ProcessSaleHandler getProcessSaleHandler() {
		return processSaleHandler;
	}
	
	public RegistTransactionHandler getRegistTransactionHandler(){
		return this.registTransactionHandler;
	}
}
