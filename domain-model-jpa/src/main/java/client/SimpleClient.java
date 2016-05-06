package client;

import presentation.AddCustomerService;
import presentation.CurrentAccountService;
import presentation.ProcessSaleService;
import business.ApplicationException;
import business.CurrentAccountHandler;
import business.Customer;
import business.DiscountCatalog;
import business.entities.Account;
import business.entities.AccountCatalog;

/**
 * A simple application client that uses both application handlers.
 * 
 * @author fmartins
 * @version 1.1 (17/04/2015)
 */
public class SimpleClient {

	private AddCustomerService addCustomerService;
	private ProcessSaleService processSaleService;
	private CurrentAccountService currentAccountService;
	public SimpleClient(AddCustomerService addCustomerService, ProcessSaleService processSaleService, CurrentAccountService currentAccountService) {
		this.addCustomerService = addCustomerService;
		this.processSaleService = processSaleService;
		this.currentAccountService = currentAccountService;
	}

	/**
	 * A simple interaction with the application services
	 */
	public void createASale() {
		System.out.println("ADD_CUSTOMER_SERVICE: "+addCustomerService);

		System.out.println("PROCES_SSALE_SERVICE: "+ processSaleService);
		
		// the interaction
		try {
			// adds a customer.
		
			//addCustomerService.addCustomer(168027852, "Customer 1", 217500255, 1, new Account());
			
			// starts a new sale
			processSaleService.newSale(168027852);
			// adds two products to the sale
			processSaleService.addProductToSale(123, 50);
			processSaleService.addProductToSale(124, 0);
			processSaleService.addProductToSale(123, 3);

			// gets the discount amount
			System.out.println(processSaleService.getSaleDiscount());

			// close's the sale
			System.out.println("The Sale has been closed: " + processSaleService.closeSale(168027852));
			
			paySale();

		} catch (ApplicationException e) {
			System.out.println("Error: " + e.getMessage());
			// for debugging purposes only. Typically, in the application
			// this information can be associated with a "details" button when
			// the error message is displayed.
			if (e.getCause() != null)
				System.out.println("Cause: ");
			e.printStackTrace();
		}
	}
	public void checkAccount() throws ApplicationException{
		System.out.println("Running the CheckAccount use case");
		Customer c = currentAccountService.getCustomer(224531700);
		System.out.println(c);
		//currentAccountService.getAccount(1);
		
	}
	public void paySale()throws ApplicationException{
		System.out.println("Running the PaySale use case");
		
		processSaleService.paySale(3, 168027852);
		
	}
}
