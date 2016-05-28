package presentation.web.model;

import java.util.List;

import business.persistence.entities.Product;

public class AddProductToSaleModel extends Model{

	private List<Product> products;
	
	private int saleId;
	
	public AddProductToSaleModel(){}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	public int getSaleId(){
		return this.saleId;
	}
	
	public void setSaleId(int saleId){
		this.saleId = saleId;
	}
	
}
