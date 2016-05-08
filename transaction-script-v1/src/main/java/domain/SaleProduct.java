package domain;

import dataaccess.SaleProductRowDataGateway;

/**
 * This class represents a sale product with no persistence control
 * 
 * @author JoaoR
 *
 */
public class SaleProduct {
	
	/**
	 * product designation
	 */
	private String prodDesignation;
	
	/**
	 * sale price
	 */
	private double salePrice;
	
	/**
	 * sale product gateway
	 */
	private SaleProductRowDataGateway saleProduct;

	/**
	 * Constructor
	 * 
	 * @param s, sale product gateway
	 * @param designation, producto designation
	 * @param price, sale price
	 */
	public SaleProduct(SaleProductRowDataGateway s, String designation, double price) {
		super();
		this.saleProduct = s;
		this.prodDesignation = designation;
		this.salePrice = price;
	}

	/**
	 * Gets sale id
	 * 
	 * @return sale id
	 */
	public int getId() {
		return saleProduct.getId();
	}
	
	/**
	 * Gets sale product quantity
	 * 
	 * @return product quantity
	 */
	public double getQty() {
		return saleProduct.getQty();
	}

	/**
	 * Gets product designation
	 * 
	 * @return product designation
	 */
	public String getDesigantion() {
		return prodDesignation;
	}

	/**
	 * Gets sale price
	 * 
	 * @return sale price
	 */
	public double getPrice() {
		return salePrice;
	}

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		
		sb.append("ID: " + saleProduct.getId() + " | ");
		sb.append("DESIGNATION: " + this.prodDesignation + " | ");
		sb.append("QTY: " + saleProduct.getQty() + " | ");
		sb.append("PRICE: " + this.salePrice );
		
		return sb.toString();
	}
	
}
