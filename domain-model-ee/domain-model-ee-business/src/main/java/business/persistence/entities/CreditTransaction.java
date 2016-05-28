package business.persistence.entities;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class CreditTransaction extends Transaction{
	
	public CreditTransaction(){}
	public CreditTransaction(Sale sale, Account account, double amount, Date createdAt){
		super(sale, account, amount, createdAt);
	}
	
	@Override
	public String print(){
		return "Credit Transaction";
	}
	
}
