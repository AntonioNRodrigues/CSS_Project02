package domain;

import dataaccess.SaleTransactionRowDataGateway;

/**
 * This class represents a specialized concept of transaction
 * In more detail it represents a Credit Transaction
 * 
 * @author JoaoR
 *
 */
public class CreditTransaction extends Transaction {

	/**
	 * Constructor
	 * 
	 * @param transaction, sale transaction gateway
	 */
	public CreditTransaction(SaleTransactionRowDataGateway transaction){
		super(transaction);
	}
	
	/**
	 * @see Transaction.printDetails()
	 */
	@Override
	public void printDetails() {
		System.out.println("CREDIT TRANSACTION");
		System.out.println(super.toString());
	}

	/**
	 * @see Transaction.getType()
	 */
	@Override
	public String getType() {
		return "CREDIT";
	}
	
}
