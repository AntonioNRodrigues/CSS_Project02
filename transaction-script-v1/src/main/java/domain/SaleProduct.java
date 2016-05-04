package domain;

public class SaleProduct {

	private int id;
	
	private double qty;
	
	private String desigantion;
	
	private double price;

	public SaleProduct(int id, double qty, String desigantion, double price) {
		super();
		this.id = id;
		this.qty = qty;
		this.desigantion = desigantion;
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getQty() {
		return qty;
	}

	public void setQty(double qty) {
		this.qty = qty;
	}

	public String getDesigantion() {
		return desigantion;
	}

	public void setDesigantion(String desigantion) {
		this.desigantion = desigantion;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		
		sb.append("ID: " + this.id + " | ");
		sb.append("DESIGNATION: " + this.desigantion + " | ");
		sb.append("QTY: " + this.qty + " | ");
		sb.append("PRICE: " + this.price );
		
		return sb.toString();
	}
	
}
