package business;

import javax.persistence.Entity;

@Entity
public class DebitTransaction extends Transaction{

	@Override
	public String print(){
		return "Debit Transaction";
	}
	
}
