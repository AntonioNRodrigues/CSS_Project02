package presentation;

import java.util.List;

import business.ApplicationException;
import business.GetCustomerTransactionHandler;
import business.Transaction;

public class GetCustomerTransactionsService {

	private GetCustomerTransactionHandler handler;
	
	private RegistTransactionService transactionService;
	
	public GetCustomerTransactionsService(
			GetCustomerTransactionHandler handler,  RegistTransactionService transactionService){
		this.handler = handler;
		this.transactionService = transactionService;
	}
	
	public List<Transaction> getCustomerTransactions(int vat) throws ApplicationException{
		return this.handler.getCustomerTransactions(vat);
	}
	
	public Transaction getTransactionById(int transactionId) throws ApplicationException{
		return handler.getCustomerTransaction(transactionId);
	}
	
}
