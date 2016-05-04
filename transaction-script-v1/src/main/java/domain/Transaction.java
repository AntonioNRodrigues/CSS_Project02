package domain;

import java.util.Date;

public abstract class Transaction {

	private int id;
	
	private int saleId;
	
	private double value;
	
	private Date date;

	public Transaction(int id, int saleId, double value, Date date) {
		super();
		this.id = id;
		this.saleId = saleId;
		this.value = value;
		this.date = date;
	}

	public int getSaleId() {
		return saleId;
	}

	public void setSaleId(int saleId) {
		this.saleId = saleId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	
	public abstract void printDetails();
	
	public abstract String getType();
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		
		// id
		sb.append("ID: " + id + " | ");
		// type
		sb.append("TYPE: " + getType() + " | ");
		// date
		sb.append("DATE: " + date + " | ");
		// value
		sb.append("VALUE: " + value);
		
		return sb.toString();
	}
	
}
