package client;

import java.sql.SQLException;

import presentation.CustomerService;
import presentation.SaleService;
import SaleSys.SaleSys;
import business.ApplicationException;
import business.DiscountType;

/**
 * A simple application client that uses both services.
 *	
 * @author fmartins
 * @version 1.2 (11/02/2015)
 * 
 */
public class SimpleClient {

	/**
	 * A simple interaction with the application services
	 * 
	 * @param args Command line parameters
	 */
	public static void main(String[] args) {
		
		SaleSys app = new SaleSys();
		try {
			app.run();
		} catch (ApplicationException e) {
			System.out.println(e.getMessage());
			System.out.println("Application Message: " + e.getMessage());
			SQLException e1 = (SQLException) e.getCause().getCause();
			System.out.println("SQLException: " + e1.getMessage());
			System.out.println("SQLState: " + e1.getSQLState());
			System.out.println("VendorError: " + e1.getErrorCode());
			return;
		}
			
		// Access both available services
		CustomerService cs = app.getCustomerService();
		SaleService ss = app.getSaleService();		
		
		// the interaction
		try {
			// adds a customer.
			cs.addCustomer(168027852, "Customer 1", 217500255, DiscountType.SALE_AMOUNT);

			// creates a new sale
			int sale = ss.newSale(168027852);

			// adds two products to the database
			ss.addProductToSale(sale, 123, 10);
			ss.addProductToSale(sale, 124, 5);
			
			// gets the discount amounts
			double discount = ss.getSaleDiscount(sale);
			System.out.println(discount);
		} catch (ApplicationException e) {
			System.out.println("Error: " + e.getMessage());
			// for debugging purposes only. Typically, in the application
			// this information can be associated with a "details" button when
			// the error message is displayed.
			if (e.getCause() != null) 
				System.out.println("Cause: ");
			e.printStackTrace();
		}
	
		app.stopRun();
		
	}
}
