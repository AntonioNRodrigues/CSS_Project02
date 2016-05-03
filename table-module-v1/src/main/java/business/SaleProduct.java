package business;


import dataaccess.Persistence;
import dataaccess.PersistenceException;


public class SaleProduct extends TableModule {

	/**
	 * Constructs a saleproduct module given the persistence repository
	 * 
	 * @param persistence The persistence repository
	 */
	public SaleProduct (Persistence persistence) {
		super(persistence);
	}

	/**
	 * Add a product to sale.
	 * 
	 * @param saleId The sale id to add a product to
	 * @param productCode The product to be added to the sale
	 * @param qty The quantity sold of the product
	 * @throws ApplicationException When some internal error occurred while saving the data.
	 */
	public void addProductToSale(int saleId, int productId, double qty) throws ApplicationException {
		try {
			persistence.saleProductTableGateway.addProductToSale(saleId, productId, qty);
		} catch (PersistenceException e) {
			throw new ApplicationException("Internal error with selling product id " + productId, e);
		}		
	}
	
}
;