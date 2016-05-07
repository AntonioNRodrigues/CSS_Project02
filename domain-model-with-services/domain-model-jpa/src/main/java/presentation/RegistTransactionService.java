package presentation;

import java.util.List;

import business.ApplicationException;
import business.RegistTransactionHandler;
import business.Transaction;

public class RegistTransactionService {
	
	/**
	 * regist transaction handler
	 */
	private RegistTransactionHandler registTransactionHandler;
	
	public RegistTransactionService(RegistTransactionHandler registTransactionHandler){
		this.registTransactionHandler = registTransactionHandler;
	}
	
	public void registCreditTransaction(int saleId, double value){
		this.registTransactionHandler.insertCreditTransaction(saleId, value);
	}
	
	public void registDebitTransaction(int saleId, double value){
		this.registTransactionHandler.inserDebitTransaction(saleId, value);
	}
	
	public List<Transaction> getCustomerTransactions(int vat) throws ApplicationException{
		return this.registTransactionHandler.getCustomerTransactions(vat);
	}
	
	public Transaction getTransactionById(int transactionId) throws ApplicationException{
		return registTransactionHandler.getCustomerTransaction(transactionId);
	}
	
}
