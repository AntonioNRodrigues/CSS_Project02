package domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DebitTransaction extends Transaction{

	private List<SaleProduct> products;

	public DebitTransaction(int id, int saleId, double value, Date date, List<SaleProduct> products) {
		super(id, saleId, value, date);
		this.products = products;
	}
	public DebitTransaction(int id, int saleId, double value, Date date) {
		super(id, saleId, value, date);
		this.products = new ArrayList<>();
	}

	public void setProducts(List<SaleProduct> products)
	{
		this.products = products;
	}
	
	public List<SaleProduct> getProducts(){
		return this.products;
	}
	
	@Override
	public void printDetails() {
		System.out.println("DEBIT TRANSACTION");
		for(SaleProduct s : products)
			System.out.println(s);
	}

	@Override
	public String getType() {
		return "DEBIT";
	}
	
}
