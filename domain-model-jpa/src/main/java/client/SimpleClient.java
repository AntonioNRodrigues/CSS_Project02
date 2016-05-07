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
import presentation.MakePaymentService;
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
	private MakePaymentService makePaymentService;
	private GetCustomerTransactionsService getCustomerTransactionsService;
	
	public SimpleClient(AddCustomerService addCustomerService, 
			ProcessSaleService processSaleService,
			MakePaymentService makePaymentService,
			GetCustomerTransactionsService customerTransactionsService) {
		this.addCustomerService = addCustomerService;
		this.processSaleService = processSaleService;
		this.makePaymentService = makePaymentService;
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
			
			// close sale
			processSaleService.closeSale();
			
			// make payment
			makePaymentService.makePayment(processSaleService.getCurrentSale(), 1000);
			
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
			
			
			// gets the discount amount
			System.out.println(processSaleService.getSaleDiscount());
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
