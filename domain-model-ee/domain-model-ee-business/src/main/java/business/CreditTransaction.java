package business;

import javax.persistence.Entity;
import javax.persistence.Inheritance;

@Entity
public class CreditTransaction extends Transaction{
	
	@Override
	public String print(){
		return "Credit Transaction";
	}
	
}
