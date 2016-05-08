package presentation;

import business.*;
import dataaccess.Persistence;
import dataaccess.PersistenceException;

import java.util.Scanner;

/**
 * Handles customer transactions. 
 * Requests are dispatched and handled by table modules.
 * Read Martin Fowler's Table Module pattern. 
 * 
 * Remarks:
 * 1. Since Java does not have a powerful database abstraction as .NET C# has (DataSet)
 * I'm going to use the persistence repository to act with the same role as DataSet. 
 * 
 * 2. Notice the use of the Layer Supertype pattern 
 * (see http://martinfowler.com/eaaCatalog/layerSupertype.html). All service classes
 * extend the Service class and pass it the persistence repository. Later we will
 * use extensively a style of programming called dependency injection. 
 * (see, for instance, http://en.wikipedia.org/wiki/Dependency_injection). This
 * is the role of the persistence parameter in the constructor.
 * 
 * @author fmartins
 * @version 1.1 (5/10/2014)
 */
public class CustomerService extends Service {
	
	/**
	 * Constructs a customer service given the persistence repository
	 * 
	 * @param persistence The persistence repository
	 */
	public CustomerService(Persistence persistence) {
		super(persistence);
	}
	
	
	/**
	 * Adds a new customer.
	 * 
	 * @param vat The VAT of the customer
	 * @param designation The customer's name
	 * @param phoneNumber The customer's phone 
	 * @param discountType The type of discount applicable to the customer
	 * @throws ApplicationException When the VAT number is invalid (we check according 
	 * to the Portuguese legislation), when it is already in the database, 
	 * or when is some unexpected persistence error.
	 */
	public void addCustomer (int vat, String designation, 
			int phoneNumber, DiscountType discountType) throws ApplicationException {
		Customer customer = new Customer (persistence);
		customer.addCustomer(vat, designation, phoneNumber, discountType);
	}

	/**
	 * Show Customer's CurrentAccount operation, which will list all Transactions for
	 * a Customer
	 *
	 * @param vat VAT of the Customer, to be validated and get all the Transactions from him
	 * @throws ApplicationException
	 * @throws PersistenceException
     */
	public void showCustomerCurrentAccount(int vat) throws ApplicationException, PersistenceException {
		Customer customer = new Customer(persistence);
		CustomerAccount account = customer.getCustomerCurrentAccount(vat);
		String command = "";
		Scanner sc = new Scanner(System.in);
		while (!command.equals("quit")) {
			System.out.print("List of Transactions:");
			System.out.println(account.print());
			System.out.println();
			System.out.println("Your total is: " + account.computeTotal());
			System.out.println("Which one do you want to see? (write 'quit' to exit)");

			command = sc.nextLine();
			if (!command.equals("quit") && !command.equals("")) {
				int index = Integer.parseInt(command);

				System.out.println("Printing details: ");
				System.out.println(account.printTransaction(index));
				System.out.println();
				System.out.println("Press Enter to see the list of Transactions");
				command = sc.nextLine();
			}
		}
	}
}
