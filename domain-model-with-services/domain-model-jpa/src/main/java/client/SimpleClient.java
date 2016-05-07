package client;

import java.util.List;
import java.util.Scanner;

import business.ApplicationException;
import business.Sale;
import business.SaleProduct;
import business.Transaction;
import business.TransactionType;
import presentation.AddCustomerService;
import presentation.GetCustomerTransactionsService;
import presentation.ProcessSaleService;

/**
 * A simple application client that uses both application handlers.
 *	
 * @author fmartins
 * @version 1.1 (17/04/2015)
 */
public class SimpleClient {

	private AddCustomerService addCustomerService;
	private ProcessSaleService processSaleService;
	private GetCustomerTransactionsService getCustomerTransactionsService;
	
	public SimpleClient(AddCustomerService addCustomerService, 
			ProcessSaleService processSaleService,
			GetCustomerTransactionsService customerTransactionsService) {
		this.addCustomerService = addCustomerService;
		this.processSaleService = processSaleService;
		this.getCustomerTransactionsService = customerTransactionsService;
	}
	
	/**
	 * A simple interaction with the application services
	 */
	public void createASale() {
		
		// the interaction
		try {
			// adds a customer.
			addCustomerService.addCustomer(168027851, "Customer 2", 217500255, 2);

			// starts a new sale
			processSaleService.newSale(168027851);

			// adds two products to the sale
			processSaleService.addProductToSale(123, 10);
			processSaleService.addProductToSale(124, 5);
			
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
	
	
	public void closeSale() throws ApplicationException{
		// close sale
		processSaleService.closeSale();
		// gets the discount amount
		System.out.println(processSaleService.getSaleDiscount());
	}
	
	public void makePayment() throws ApplicationException{
		// make payment
		processSaleService.makePayment();
	}
	
	public void checkAccount() throws ApplicationException{
		// get customer transactions
		int input = 0;
		Scanner sc = new Scanner(System.in);
		do{
			System.out.println("===================================");
			List<Transaction> transactions = getCustomerTransactionsService.getCustomerTransactions(168027851);
			for(Transaction t : transactions)
				System.out.println(t);
			
			System.out.println("Insert transaction id");
			input = sc.nextInt();
			
			// get transaction
			Transaction transaction = getCustomerTransactionsService.getTransactionById(input);
			if(transaction.getType() == TransactionType.CREDIT)
				System.out.println(transaction);
			else
			{
				Sale sale = transaction.getSale();
				List<SaleProduct> saleProducts = sale.getSaleProducts();
				for(SaleProduct sp : saleProducts)
				{
					System.out.println(sp.getProduct().toString(sp.getQty()));
				}
			}	
			
		}while(input != 0);
	}
	
}
