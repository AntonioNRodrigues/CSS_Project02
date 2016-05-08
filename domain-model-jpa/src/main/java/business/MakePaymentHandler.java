package business;

public class MakePaymentHandler {

	private SaleCatalog saleCatalog;
	
	private TransactionCatalog transactionCatalog;
	
	private CustomerCatalog customerCatalog;
	
	public MakePaymentHandler(SaleCatalog saleCatalog, TransactionCatalog transactionCatalog, 
			CustomerCatalog customerCatalog){
		this.saleCatalog = saleCatalog;
		this.transactionCatalog = transactionCatalog;
		this.customerCatalog = customerCatalog;
	}
	
	public void makePayment(Sale sale, double value){
		
		// add credit transaction
		transactionCatalog.addCreditTransaction(sale, TransactionType.CREDIT);
		
		// update sale status
		sale.setStatus(SaleStatus.PAYED);
		saleCatalog.updateSale(sale);
		
	}
	
}
