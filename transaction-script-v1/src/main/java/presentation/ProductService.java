package presentation;

import java.util.List;

import business.ApplicationException;
import business.ProductTransactionScripts;

public class ProductService {

	private ProductTransactionScripts productTS;
	
	public ProductService(ProductTransactionScripts ts)
	{
		this.productTS = ts;
	}
	
	
	public List<String> getAvailableProducts() throws ApplicationException{
		return this.productTS.getAvailableProducts();
	}
	
}
