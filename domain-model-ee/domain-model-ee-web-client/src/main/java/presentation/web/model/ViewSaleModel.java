package presentation.web.model;

import java.util.List;

import business.persistence.entities.SaleProduct;
import business.persistence.entities.Customer;

//import business.persistence.entities.SaleProduct;

public class ViewSaleModel extends Model {
	private int id;
	private double discountValue;
	private double total;
	private List<SaleProduct> saleProducts;
	private Customer customer;

	public ViewSaleModel(int id, double discountValue, double total, List<SaleProduct> lista, Customer c) {
		super();
		this.setCustomer(c);
		this.discountValue = discountValue;
		this.id = id;
		this.total = total;
		setSaleProducts(lista);
	}

	public double getFinalValue(){
		return total - discountValue;
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

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}