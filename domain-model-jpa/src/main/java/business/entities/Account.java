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
 * Entity implementation class for Entity: ContaCorrente
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
	// public static final String FIND_ALL = "Account.getTransations";

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
	 * 
	 * @param balance
	 */
	public void setBalance(double balance) {
		this.balance = balance;
	}

	/**
	 * 
	 * @return
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

	public boolean addTransation(Transation transation) {
		calcBalance(transation);
		return this.listTransactions.add(transation);
	}

	public List<Transation> getTransations() {
		return this.listTransactions;
	}

	@Override
	public String toString() {
		return "Account [balance=" + balance + ", listTransactions=" + listTransactions + "]";
	}

	public int getId() {
		return this.id;
	}
}