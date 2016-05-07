package business;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Transaction
 *
 */

public class SaleTransaction{

	
	private int id;
	
	
	private double value;
	
//	@ManyToOne
//	private Sale sale;
	
	private static final long serialVersionUID = 1L;

	public SaleTransaction() {
		super();
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

//	public Sale getSale() {
//		return sale;
//	}
//
//	public void setSale(Sale sale) {
//		this.sale = sale;
//	}
	
	
   
}
