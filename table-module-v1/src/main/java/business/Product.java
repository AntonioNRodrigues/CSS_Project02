package business;

import dataaccess.Persistence;
import dataaccess.PersistenceException;
import dataaccess.TableData;

/**
 * A table module for products.
 * See remarks in the Customer class.
 * 
 * @author fmartins
 * @version 1.1 (5/10/2014)
 *
 */
public class Product extends TableModule {

	/**
	 * Constructs a product module given the persistence repository
	 * 
	 * @param persistence The persistence repository
	 */
	public Product(Persistence persistence) {
		super(persistence);
	}


	/**
	 * @param productCode The code of the product to get its internal id
	 * @return The internal id of the product with code productCode
	 * @throws ApplicationException When the product with code productCode is not found
	 * or when there is an internal database error (not present in the current implementation
	 * of the in memory database with use).
	 */
	public int getProductId (int productCode) throws ApplicationException {
		try {
			TableData td = persistence.productTableGateway.findByProdCod(productCode);
			if (!td.isEmpty()) 
				return persistence.productTableGateway.readId(td.iterator().next());
			else 
				throw new ApplicationException("Product with code " + productCode + " does not exist!");
		} catch (PersistenceException e) {
			throw new ApplicationException ("Internal error retrieving product with code " + productCode, e);
		}
	}

	/**
	 * Reduce by qty the number of units in stock of product with id productId.
	 * 
	 * @param productId The product id to reduce the units in stock
	 * @param qty The number of units to remove from stock of productId
	 * @throws ApplicationException When there is not enough products in stock to fulfill
	 * the request or refer to getDoubleFieldFromProduct for the other possible exceptions. 
	 */
	public void takeFromStock (int productId, double qty) throws ApplicationException {
		try {
			// if the is enough products in stock...
			double stockQty = getQty (productId);
			if (stockQty >= qty) {
				// reduce the number of stock items
				persistence.productTableGateway.updateStock (productId, stockQty - qty);
			} else
				throw new ApplicationException("Product with internal id " + productId + " has ("  + 
						stockQty + ") units, which is insufficient for fulfilling the request of " + qty + " units.");
		} catch (PersistenceException e) {
			throw new ApplicationException ("Internal error updating the stock of product with internal id " + productId, e);
		}
	}

	public TableData findProduct(int productId) throws ApplicationException {
		try {
			TableData td = persistence.productTableGateway.find(productId);
			if (td.isEmpty()) 
				throw new ApplicationException("Product with internal id " + productId + " does not exist!");
			else 
				return td;
		} catch (PersistenceException e) {
			throw new ApplicationException ("Internal error retrieving customer with internal id " + productId, e);
		}
	}

	public boolean isEligibleForDiscount(int productId) throws ApplicationException {
		try {
			TableData td = findProduct(productId);
			return  persistence.productTableGateway.readDiscountElibility(td.iterator().next());
		} catch (PersistenceException e) {
			throw new ApplicationException ("Internal error retrieving customer with internal id " + productId, e);
		}
	}

	/**
	 * @param productId The id of the product to get its stock quantity
	 * @return The quantity in stock of the product with id productId
	 * @throws ApplicationException When the product with productId does not exist or
	 * when there is an internal database error (which is not the case in this example),
	 * nevertheless, the ResultSet is prepared to send such an exception.
	 */
	public double getQty(int productId) throws ApplicationException {
		try {
			TableData td = findProduct(productId);
			return  persistence.productTableGateway.readQuantity(td.iterator().next());
		} catch (PersistenceException e) {
			throw new ApplicationException ("Internal error retrieving customer with internal id " + productId, e);
		}
	}
	
	public double getFaceValue(int productId) throws ApplicationException {
		try {
			TableData td = findProduct(productId);
			return  persistence.productTableGateway.readFaceValue(td.iterator().next());
		} catch (PersistenceException e) {
			throw new ApplicationException ("Internal error retrieving customer with internal id " + productId, e);
		}
	}

}
