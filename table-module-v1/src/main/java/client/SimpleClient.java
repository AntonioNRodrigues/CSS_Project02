package client;

import java.sql.SQLException;

import dataaccess.Persistence;
import dataaccess.PersistenceException;
import dbutils.ResetTables;
import presentation.CustomerService;
import presentation.SaleService;
import business.ApplicationException;
import business.DiscountType;

/**
 * The big bang class.
 *	
 * @author fmartins
 * @version 1.1 (4/10/2014)
 * 
 */
public class SimpleClient {

	/**
	 * Fire in the hole!!
	 * 
	 * @param args Command line parameters
	 */
	public static void main(String[] args) {

		// Reset tables
		ResetTables resetTables = new ResetTables();
		try	{
			resetTables.resetCSSDerbyDB();			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Create the persistence layer
		try (Persistence persistence = new dataaccess.Persistence("jdbc:derby:data/derby/cssdb", "SaleSys", "")) {

			// Creates the two available services
			CustomerService cs = new CustomerService(persistence);
			SaleService vs = new SaleService(persistence);


			// runs a small test
			try {
				// adds a customer.
				cs.addCustomer(168027852, "Cliente 1", 217500255, DiscountType.SALE_AMOUNT);

				// creates a new sale
				int sale = vs.newSale(168027852);

				// adds two products to the database
				vs.addProductToSale(sale, 123, 10);
				vs.addProductToSale(sale, 124, 5);

				// gets the discount amounts
				double discount = vs.getSaleDiscount(sale);
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
		} catch (PersistenceException e) {
			System.out.println("Error connecting database");
			System.out.println("Application Message: " + e.getMessage());
			System.out.println("SQLException: " + e.getCause().getMessage());
			System.out.println("SQLState: " + ((SQLException) e.getCause()).getSQLState());
			System.out.println("VendorError: " + ((SQLException) e.getCause()).getErrorCode());
			return;
		}
	}
}
