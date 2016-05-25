package business;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Account implements Serializable{

	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	
	@OneToOne
	private Customer customer;
	
	@OneToMany
	private List<Sale> sales;
	
	@OneToMany
	private List<Transaction> transactions;

	public Account() {}
	public Account(Customer customer, List<Sale> sales, List<Transaction> transactions) {
		this.customer = customer;
		this.sales = sales;
		this.transactions = transactions;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public List<Sale> getSales() {
		return sales;
	}
	public void setSales(List<Sale> sales) {
		this.sales = sales;
	}
	public List<Transaction> getTransactions() {
		return transactions;
	}
	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}
	
}
