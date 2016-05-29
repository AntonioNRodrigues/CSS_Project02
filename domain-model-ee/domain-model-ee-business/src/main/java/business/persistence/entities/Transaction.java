package business.persistence.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;
import static javax.persistence.EnumType.STRING;

import business.TransactionType;

@Entity
public class Transaction {

	@Version
	private int version;
	
	@Id @GeneratedValue
	private int id;
	
	private double amount;
	
	@Temporal(TemporalType.DATE)
	private Date createdAt;
		
	@ManyToOne
	private Account account;
	
	@Enumerated(STRING)
	private TransactionType type;
	
	public Transaction(){}
	
	public Transaction(int id, double amount, Date createdAt){
		this.id = id;
		this.amount = amount;
		this.createdAt = createdAt;
	}
	
	public Transaction(TransactionType type, Account account, double amount, Date createdAt){
		this.amount = amount;
		this.createdAt = createdAt;
		this.account = account;
		this.type = type;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	public void setAccount(Account a){
		this.account = account;
	}
	public Account getAccount(){
		return this.account;
	}

	/**
	 * Knows how to print
	 */
	public String print() { return null; };
	
	public TransactionType getType() {
		return type;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}
	
}
