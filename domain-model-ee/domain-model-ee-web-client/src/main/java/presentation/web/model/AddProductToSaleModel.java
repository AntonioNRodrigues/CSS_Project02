package presentation.web.model;

import java.util.List;

import business.Product;

public class AddProductToSaleModel extends Model{

	private List<Product> products;
	
	public AddProductToSaleModel(){}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	
	
}
