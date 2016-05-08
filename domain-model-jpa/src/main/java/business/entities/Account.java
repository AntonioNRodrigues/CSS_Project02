package business.entities;

import static javax.persistence.CascadeType.ALL;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 * Entity implementation class for Entity: Account
 * 
 * @author Antonio Rodrigues
 * @author Simão Neves
 * @author Joao Rodrigues
 * @Group:: css018
 * @Date 2016/04/28
 *
 */
@Entity
@NamedQueries({
		@NamedQuery(name = Account.FIND_BY_ID, query = "SELECT a FROM Account a WHERE a.id = :" + Account.FIND_BY_ID) })

public class Account {

	public static final String FIND_BY_ID = "id";

	@Id
	@GeneratedValue
	private int id;

	@Column
	private double balance;

	@OneToMany(cascade = ALL)
	@JoinColumn
	private List<Transation> listTransactions;

	public Account() {
	}

	public Account(double balance) {
		this.balance = balance;
	}

	@PostConstruct
	private void postInit() {
		listTransactions = new ArrayList<>();
	}

	/**
	 * 
	 * @return
	 */
	public double getBalance() {
		return this.balance;
	}

	/**
	 * method that updates the balance of the customer when a new Transation is
	 * added
	 */
	private void calcBalance(Transation transation) {

		if (transation instanceof Debit) {
			this.balance += transation.getValue();
		} else if (transation instanceof Credit) {
			this.balance -= transation.getValue();
		} else {
			throw new UnsupportedOperationException();
		}
	}

	/**
	 * method to add a new Transation
	 * 
	 * @param transation
	 *            to be added
	 * @return true is the transation is added an false otherwise
	 */
	public boolean addTransation(Transation transation) {
		calcBalance(transation);
		return this.listTransactions.add(transation);
	}

	/**
	 * getter of the list os Transations
	 * 
	 * @return
	 */
	public List<Transation> getTransations() {
		return this.listTransactions;
	}
	/**
	 * getter of the id
	 * @return
	 */
	public int getId() {
		return this.id;
	}
	
	@Override
	public String toString() {
		return "Account [balance=" + balance + ", listTransactions=" + listTransactions + "]";
	}
}