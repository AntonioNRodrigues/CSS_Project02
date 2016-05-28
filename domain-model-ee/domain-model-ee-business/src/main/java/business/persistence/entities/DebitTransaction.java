package business.persistence.entities;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class DebitTransaction extends Transaction{

	public DebitTransaction(){}
	public DebitTransaction(Sale sale, Account account, double amount, Date createdAt){
		super(sale, account, amount, createdAt);
	}
	
	@Override
	public String print(){
		return "Debit Transaction";
	}
	
}
