package business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RegistTransactionHandler {

	private TransactionCatalog transactionCatalog;
	
	private SaleCatalog saleCatalog;
	
	private CustomerCatalog customerCatalog;
	
	public RegistTransactionHandler(TransactionCatalog transactionCatalog, SaleCatalog saleCatalog, CustomerCatalog customerCatalog){
		this.transactionCatalog = transactionCatalog;
		this.saleCatalog = saleCatalog;
		this.customerCatalog = customerCatalog;
	}
	
	public void insertCreditTransaction(int saleId, double value){
		
		// get sale by id
		Sale sale = this.getSaleById(saleId);
		
		// insert credit transaction
		this.transactionCatalog.addCreditTransaction(sale, value);
		
	}
	
	public void inserDebitTransaction(int saleId, double value){
		
		// get sale by id
		Sale sale = this.getSaleById(saleId);
		
		// insert debit transaction
		transactionCatalog.addDebitTransaction(sale, value);
		
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
	
	
	
	
	private Sale getSaleById(int saleId){
			return saleCatalog.find(saleId);
	}
	
}
