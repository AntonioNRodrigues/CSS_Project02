package client;

import presentation.AddCustomerService;
import presentation.ProcessSaleService;
import business.ApplicationException;

/**
 * A simple application client that uses both application handlers.
 * 
 * @author fmartins
 * @version 1.1 (17/04/2015)
 */
public class SimpleClient {

	private AddCustomerService addCustomerService;
	private ProcessSaleService processSaleService;

	public SimpleClient(AddCustomerService addCustomerService, ProcessSaleService processSaleService) {
		this.addCustomerService = addCustomerService;
		this.processSaleService = processSaleService;
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
			addCustomerService.addCustomer(168027852, "Customer 1", 217500255, 2);

			// starts a new sale
			processSaleService.newSale(168027852);

			// adds two products to the sale
			processSaleService.addProductToSale(123, 6);
			processSaleService.addProductToSale(124, 5);
			processSaleService.addProductToSale(123, 4);

			// gets the discount amount
			System.out.println(processSaleService.getSaleDiscount());

			// close's the sale
			System.out.println("The Sale has been closed: " + processSaleService.closeSale(168027852));

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
}
