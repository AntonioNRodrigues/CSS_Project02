package domain;

import java.util.ArrayList;
import java.util.List;

import dataaccess.SaleTransactionRowDataGateway;

/**
 * This class represents a specialized concept of transaction
 * In more detail it represents a Debit Transaction
 * 
 * @author JoaoR
 *
 */
public class DebitTransaction extends Transaction{

	/**
	 * transaction sale products list
	 */
	private List<SaleProduct> products;

	/**
	 * Constructor
	 * 
	 * @param transaction, sale transaction gateway
	 * @param products, sale products list
	 */
	public DebitTransaction(SaleTransactionRowDataGateway transaction, List<SaleProduct> products) {
		super(transaction);
		this.products = products;
	}
	
	/**
	 * Constructor
	 * 
	 * @param transaction, sale transaction gateway
	 */
	public DebitTransaction(SaleTransactionRowDataGateway transaction) {
		super(transaction);
		this.products = new ArrayList<>();
	}
	
	/**
	 * Gets sale products
	 * 
	 * @return sale products
	 */
	public List<SaleProduct> getProducts(){
		return this.products;
	}
	
	/**
	 * @see Transaction.printDetails()
	 */
	@Override
	public void printDetails() {
		System.out.println("DEBIT TRANSACTION");
		for(SaleProduct s : products)
			System.out.println(s);
	}

	/**
	 * @see Transaction.getType()
	 */
	@Override
	public String getType() {
		return "DEBIT";
	}
	
}
