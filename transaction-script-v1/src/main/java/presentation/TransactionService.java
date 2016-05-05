package presentation;

import business.ApplicationException;
import business.SaleTransactionTransactionScripts;
import domain.Transaction;

/**
 * This class represents a sale transaction service
 * It is the first layer to be reached when calling a transaction related operation
 * 
 * @author JoaoR
 *
 */
public class TransactionService {
	
	/**
	 * Sale transaction sale transaction scripts
	 */
	SaleTransactionTransactionScripts STTS;
	
	/**
	 * Constructor
	 * 
	 * @param STTS, sale transaction transaction script
	 */
	public TransactionService(SaleTransactionTransactionScripts STTS){
		this.STTS = STTS;
	}
	
	/**
	 * Gets transaction based on transaction id
	 * 
	 * @param transactionId, transaction id
	 * @return the corresponding transaction
	 * 
	 * @throws ApplicationException
	 */
	public Transaction getTransactionDetails(int transactionId)
		throws ApplicationException{
		return this.STTS.getTransactionDetails(transactionId);
	}
	
}
