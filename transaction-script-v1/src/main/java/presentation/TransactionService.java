package presentation;

import business.ApplicationException;
import business.SaleTransactionTransactionScripts;
import domain.Transaction;

public class TransactionService {
	
	SaleTransactionTransactionScripts STTS;
	
	public TransactionService(SaleTransactionTransactionScripts STTS){
		this.STTS = STTS;
	}
	
	public Transaction getTransactionDetails(int transactionId)
		throws ApplicationException{
		return this.STTS.getTransactionDetails(transactionId);
	}
	
}
