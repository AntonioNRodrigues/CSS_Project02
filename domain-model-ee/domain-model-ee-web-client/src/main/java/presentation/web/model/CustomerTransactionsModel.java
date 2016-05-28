package presentation.web.model;

import java.util.List;

import business.persistence.entities.Customer;
import business.persistence.entities.Transaction;

public class CustomerTransactionsModel extends Model{

	private List<Transaction> transactions;
	
	private Customer customer;
	
	public CustomerTransactionsModel(){}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	
	
}
