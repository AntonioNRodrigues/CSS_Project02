package business.persistence.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

@Entity
public class Account implements Serializable{

	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;

	@OneToMany(fetch=FetchType.EAGER)
	private List<Sale> sales;
	
	@OneToMany(fetch=FetchType.EAGER)
	private List<Transaction> transactions;

	public Account() {}
	public Account(Customer customer, List<Sale> sales, List<Transaction> transactions) {
		this.sales = sales;
		this.transactions = transactions;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public List<Sale> getSales() {
		return sales;
	}
	public void setSales(List<Sale> sales) {
		this.sales = sales;
	}
	
}
