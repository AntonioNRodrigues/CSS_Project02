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

	/**
	 * Get a TableData that has all the SaleProducts for the Sale with Id saleId
	 * @param saleId The Id of the sale we want the SaleProducts from
	 * @return TableData with all the rows that represent SaleProducts
	 * @throws ApplicationException
     */
	public TableData getSaleProductsFromSale(int saleId) throws ApplicationException {
		try {
			return persistence.saleProductTableGateway.getSaleProducts(saleId);
		} catch (PersistenceException e) {
			throw new ApplicationException("Internal error while getting all sale products from sale with id " + saleId, e);
		}
	}

	/**
	 * Textual representation of a SaleProduct
	 * @param row Row that has all the data to construct a String
	 * @return String representatin of a SaleProduct
	 * @throws PersistenceException
     */
	public String print(TableData.Row row) throws PersistenceException {
		return this.persistence.saleProductTableGateway.readSaleId(row) + " | "
				+ this.persistence.saleProductTableGateway.readQuantity(row) + " | "
				+ this.persistence.saleProductTableGateway.readProductId(row);
	}
}