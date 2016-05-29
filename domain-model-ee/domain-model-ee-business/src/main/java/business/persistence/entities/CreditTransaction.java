package business.persistence.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
public class CreditTransaction extends Transaction implements Serializable {

	private static final long serialVersionUID = 2L;

	public CreditTransaction(){}
	public CreditTransaction(Sale sale, Account account, double amount, Date createdAt){
		super(sale, account, amount, createdAt);
	}

	@Override
	public String print(){
		return "Credit Transaction";
	}
	
}
