package business;

import java.util.ArrayList;
import java.util.List;

public class GetCustomerTransactionHandler {

	private CustomerCatalog customerCatalog;
	
	private TransactionCatalog transactionCatalog;
	
	public GetCustomerTransactionHandler(CustomerCatalog customerCatalog, TransactionCatalog transactionCatalog){
		this.customerCatalog = customerCatalog;
		this.transactionCatalog = transactionCatalog;
	}
	
	public List<Transaction> getCustomerTransactions(int vat) throws ApplicationException{
		
		try{
		
			// obtain customer
			Customer customer = customerCatalog.getCustomer(vat);
			
			// obtain customer sales
			List<Sale> sales = customer.getSales();
			
			// obtain all sales transactions
			List<Transaction> transactions = new ArrayList<>();
			for(Sale sale : sales)
				transactions.addAll(sale.getTransactions());
			
			return transactions;
			
		} catch ( Exception e ) {
			throw new ApplicationException("Error getting customer transactions", e);
		}
		
	}

	public Transaction getCustomerTransaction(int transactionId) throws ApplicationException{
		return transactionCatalog.findById(transactionId);
	}
	
}
