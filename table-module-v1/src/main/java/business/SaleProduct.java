package business;


import dataaccess.Persistence;
import dataaccess.PersistenceException;
import dataaccess.TableData;


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
	 * @param productId The product to be added to the sale
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

	public TableData getSaleProductsFromSale(int saleId) throws ApplicationException {
		try {
			return persistence.saleProductTableGateway.getSaleProducts(saleId);
		} catch (PersistenceException e) {
			throw new ApplicationException("Internal error while getting all sale products from sale with id " + saleId, e);
		}
	}

	public String print(TableData.Row row) throws PersistenceException {
		return this.persistence.saleProductTableGateway.readSaleId(row) + " | "
				+ this.persistence.saleProductTableGateway.readQuantity(row) + " | "
				+ this.persistence.saleProductTableGateway.readProductId(row);
	}
}