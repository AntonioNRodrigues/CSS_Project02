package presentation.web.model;

import java.util.List;

import business.persistence.entities.SaleProduct;

//import business.persistence.entities.SaleProduct;

public class ViewSaleModel extends Model {
	private int id;
	private double discountValue;
	private double total;
	private List<SaleProduct> saleProducts;
	
	public ViewSaleModel(int id, double discountValue, double total, List<SaleProduct> lista)  {
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

	public List<SaleProduct> getSaleProducts() {
		return saleProducts;
	}

	public void setSaleProducts(List<SaleProduct> saleProducts) {
		this.saleProducts = saleProducts;
	}

}