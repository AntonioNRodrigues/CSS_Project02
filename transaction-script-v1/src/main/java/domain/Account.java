package domain;

import java.util.List;

/**
 * This class represents a user account with no control of persistence
 * 
 * @author JoaoR
 *
 */
public class Account {
	
	/**
	 * account transactions
	 */
	private List<? extends Transaction> transactions;
	
	/**
	 * account customer id
	 */
	private int customerId;
	
	/**
	 * Constructor
	 * 
	 * @param customerId, account customer id
	 * @param transactions, account transactions
	 */
	public Account(int customerId, List<? extends Transaction> transactions){
		this.customerId = customerId;
		this.transactions = transactions;
	}

	/**
	 * Gets sale transactions
	 * 
	 * @return sale transactions
	 */
	public List<? extends Transaction> getTransactions() {
		return transactions;
	}

	/**
	 * Gets customer id
	 * 
	 * @return customer id
	 */
	public int getCustomerId() {
		return customerId;
	}

}
