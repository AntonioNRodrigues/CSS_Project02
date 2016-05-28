package presentation.web.model;

import business.persistence.entities.Sale;

public class ViewSaleModel extends Model {
	private int id;
	private double discountValue;
	private double total;

	public ViewSaleModel(int id, double discountValue, double total) {
		super();
		this.discountValue = discountValue;
		this.id = id;
		this.total = total;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getDiscountValue() {
		return discountValue;
	}

	public void setDiscountValue(double discountValue) {
		this.discountValue = discountValue;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

}