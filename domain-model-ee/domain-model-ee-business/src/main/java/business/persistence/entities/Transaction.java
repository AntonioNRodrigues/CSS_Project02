package business.persistence.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlSeeAlso;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="TRANS_TYPE")
@XmlSeeAlso({CreditTransaction.class, DebitTransaction.class})
public abstract class Transaction implements Serializable {

	@Version
	private int version;
	
	@Id @GeneratedValue
	private int id;
	
	private double amount;
	
	@Temporal(TemporalType.DATE)
	private Date createdAt;
	
	@ManyToOne
	private Sale sale;
	
	@ManyToOne
	private Account account;
	
	public Transaction(){}
	
	public Transaction(int id, double amount, Date createdAt){
		this.id = id;
		this.amount = amount;
		this.createdAt = createdAt;
	}
	
	public Transaction(Sale sale, Account account, double amount, Date createdAt){
		this.amount = amount;
		this.createdAt = createdAt;
		this.sale = sale;
		this.account = account;
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

	public Sale getSale() {
		return sale;
	}

	public void setSale(Sale sale) {
		this.sale = sale;
	}

	/**
	 * Knows how to print
	 */
	public abstract String print();
	
	@Override
	public String toString(){
		return this.print();
	}
	
}
