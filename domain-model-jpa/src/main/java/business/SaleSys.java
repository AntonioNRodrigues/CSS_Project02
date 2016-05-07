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
	
	private MakePaymentHandler makePaymentHandler;
	
	private GetCustomerTransactionHandler getCustomerTransactionsHandler;
	
	/**
	 * Performs the start up use case
	 */
	public SaleSys(EntityManagerFactory emf) {
		CustomerCatalog customerCatalog = new CustomerCatalog(emf);
		this.addCustomerHandler = new AddCustomerHandler(customerCatalog, new DiscountCatalog(emf));
		this.processSaleHandler = new ProcessSaleHandler(
				new SaleCatalog(emf), customerCatalog, 
				new ProductCatalog(emf),
				new TransactionCatalog(emf));
		this.makePaymentHandler = new MakePaymentHandler(
				new SaleCatalog(emf), new TransactionCatalog(emf), customerCatalog);
		this.getCustomerTransactionsHandler = new GetCustomerTransactionHandler(customerCatalog, new TransactionCatalog(emf));
	}
	
	public GetCustomerTransactionHandler getCusTomerTransactionHandler(){
		return this.getCustomerTransactionsHandler;
	}
	
	public MakePaymentHandler getMakePaymentHandler(){
		return this.makePaymentHandler;
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
}
