package business;

import java.util.ArrayList;
import java.util.List;

import dataaccess.PersistenceException;
import dataaccess.ProductRowDataGateway;

public class ProductTransactionScripts {

	/**
	 * Gets a string list with a textual representation of products 
	 * 
	 * @return list of textual represented products
	 * @throws ApplicationException
	 */
	public static List<String> getAvailableProducts() throws ApplicationException{
		
		try{			
			List<ProductRowDataGateway> products = ProductRowDataGateway.getAllAvailableProducts();
			List<String> list = new ArrayList<>();
			for(ProductRowDataGateway prod : products)
				list.add(prod.toString());
			return list;
				
		} catch (PersistenceException e) {
			throw new ApplicationException("Error when getting available products", e);
		}
		
	}
	
}
