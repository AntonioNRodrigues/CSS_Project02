package dataaccess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dataaccess.TableData.Row;

/**
 * Table Data Gateway for the salesproduct's table
 * 
 *   
 * @author fmartins
 * @version 1.1 (5/10/2015)
 *
 */
public class SaleProductTableDataGateway extends TableDataGateway {

	/**
	 * Columns' labels
	 */	
	static final String SALE_ID = "SALE_ID";
	static final String PRODUCT_ID = "PRODUCT_ID";
	static final String QTY = "QTY";


	SaleProductTableDataGateway(DataSource dataSource) {
		super(dataSource);
	}


	private static final String INSERT_PRODUCT_SALE_SQL = 
			"insert into saleproduct (id, " + SALE_ID + ", " + PRODUCT_ID + ", " + QTY + ") " +
				"values (DEFAULT, ?, ?, ?)";
	
	/**
	 * Inserts the record in the products sale 
	 * 
	 * @param saleId The id of the sale to add the product to
	 * @param productId The id of the product to add to the sale
	 * @param qty The amount of the product to be sold
	 * @throws PersistenceException When an internal error adding a product to a sale occurs
	 */
	public void addProductToSale (int saleId, int productId, double qty) throws PersistenceException {
		try (PreparedStatement statement = dataSource.prepare(INSERT_PRODUCT_SALE_SQL)) {
			// set statement arguments
			statement.setInt(1, saleId);
			statement.setInt(2, productId);
			statement.setDouble(3, qty);		
			// execute SQL
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new PersistenceException ("Internal error adding a product to a sale", e);
		}
	}
	
	
	/**
	 * The select the products of a sale by sale Id SQL statement
	 */
	private static final String GET_SALE_PRODUCTS_SQL =
			"select * from saleproduct a " +
				"where " + SALE_ID + " = ?";

	/**
	 * Gets the products of a sale by its sale id 
	 * 
	 * @param saleId The sale id to get the products of
	 * @return The set of products that compose the sale
	 * @throws PersistenceException When there is an error obtaining the
	 *         information from the database.
	 */
	public TableData getSaleProducts (int saleId) throws PersistenceException {
		try (PreparedStatement statement = dataSource.prepare(GET_SALE_PRODUCTS_SQL)) {		
			// set statement arguments 
			statement.setInt(1, saleId);			
			// execute SQL
			try (ResultSet rs = statement.executeQuery()) {
				return new TableData().populate(rs);
			}
		} catch (SQLException e) {
			throw new PersistenceException("Internal error getting the products of a sale", e);
		}			
	}

	/**
	 * Reads from a result set the value of the corresponding column
	 * @param r
	 * @return the conversion of the value read from the result set 
	 * @throws PersistenceException When there is an error in the reading or conversion
	 */
	public double readQuantity(Row r) throws PersistenceException{
		return r.getDouble(QTY);
	}

	/**
	 * Reads from a result set the value of the corresponding column
	 * @param r
	 * @return the conversion of the value read from the result set 
	 * @throws PersistenceException When there is an error in the reading or conversion
	 */
	public int readSaleId(Row r) throws PersistenceException{
		return r.getInt(SALE_ID);
	}

	/**
	 * Reads from a result set the value of the corresponding column
	 * @param r
	 * @return the conversion of the value read from the result set 
	 * @throws PersistenceException When there is an error in the reading or conversion
	 */
	public int readProductId(Row r) throws PersistenceException{
		return r.getInt(PRODUCT_ID);
	}

}
