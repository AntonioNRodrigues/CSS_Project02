package domain;

import java.util.List;

public class Account {
	
	private List<? extends Transaction> transactions;
	
	private int customerId;
	
	public Account(int customerId, List<? extends Transaction> transactions){
		this.customerId = customerId;
		this.transactions = transactions;
	}

	public List<? extends Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<? extends Transaction> transactions) {
		this.transactions = transactions;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	
	
	
}
