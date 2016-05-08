package presentation;

import java.util.List;

import business.ApplicationException;
import business.GetCustomerTransactionHandler;
import business.Transaction;

public class GetCustomerTransactionsService {

	private GetCustomerTransactionHandler handler;
	
	public GetCustomerTransactionsService(
			GetCustomerTransactionHandler handler){
		this.handler = handler;
	}
	
	public List<Transaction> getCustomerTransactions(int vat) throws ApplicationException{
		return this.handler.getCustomerTransactions(vat);
	}
	
	public Transaction getTransactionById(int transactionId) throws ApplicationException{
		return handler.getCustomerTransaction(transactionId);
	}
	
}
