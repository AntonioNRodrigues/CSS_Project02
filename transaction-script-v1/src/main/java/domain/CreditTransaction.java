package domain;

import java.util.Date;

public class CreditTransaction extends Transaction {

	public CreditTransaction(int id, int saleId, double value, Date date){
		super(id, saleId, value, date);
	}

	@Override
	public void printDetails() {
		System.out.println("CREDIT TRANSACTION");
		System.out.println(super.toString());
	}

	@Override
	public String getType() {
		return "CREDIT";
	}
	
}
