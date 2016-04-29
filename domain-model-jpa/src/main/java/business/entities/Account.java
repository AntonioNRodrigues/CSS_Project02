package business.entities;

import java.util.ArrayList;
import java.util.List;
import business.entities.Transation;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
public class Account {

	@Id @GeneratedValue private int id;
	
	@Column private double balance;
	
	@OneToMany @JoinColumn private List<Transation> listTransactions;

	public Account() {
	}

	public Account(double balance) {
		this.balance = balance;
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

}
