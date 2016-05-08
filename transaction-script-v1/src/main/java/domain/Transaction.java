package domain;

import java.util.Date;

import dataaccess.SaleTransactionRowDataGateway;

/**
 * This class represents a generalized concept of transaction
 * 
 * @author JoaoR
 *
 */
public abstract class Transaction {

	/**
	 * sale transaction gateway
	 */
	private SaleTransactionRowDataGateway transaction;

	/**
	 * Constructor
	 * 
	 * @param transaction sale transaction gateway
	 */
	public Transaction(SaleTransactionRowDataGateway transaction){
		this.transaction = transaction;
	}
	
	/**
	 * Gets sale id
	 * 
	 * @return sale id
	 */
	public int getSaleId() {
		return transaction.getSaleId();
	}

	/**
	 * Gets transaction id
	 * 
	 * @return id
	 */
	public int getId() {
		return transaction.getId();
	}

	/**
	 * Gets transaction value
	 * 
	 * @return value
	 */
	public double getValue() {
		return transaction.getValue();
	}

	/**
	 * Gets transaction date
	 * 
	 * @return transaction date
	 */
	public Date getDate() {
		return transaction.getCreatedAt();
	}
	
	/**
	 * Prints sale details
	 */
	public abstract void printDetails();
	
	/**
	 * Gets transaction type textual represented
	 * 
	 * @return transaction type
	 */
	public abstract String getType();
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		
		// id
		sb.append("ID: " + getId() + " | ");
		// type
		sb.append("TYPE: " + getType() + " | ");
		// date
		sb.append("DATE: " + getDate() + " | ");
		// value
		sb.append("VALUE: " + getValue());
		
		return sb.toString();
	}
	
}
