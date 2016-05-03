package dataaccess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dataaccess.TableData.Row;

/**
 * Table Data Gateway for the product's table
 * 
 * Please read the remarks in the CustomerTableGateway class
 *  
 * @author fmartins
 * @version 1.1 (5/10/2015)
 *
 */
public class ProductTableDataGateway extends TableDataGateway {

	/**
	 * Columns' labels
	 */	
	static final String ID = "ID";
	private static final String PRODUCT_CODE = "PRODCOD";
	private static final String DESCRIPTION = "DESCRIPTION";
	static final String FACE_VALUE = "FACEVALUE";
	private static final String QTY = "QTY";
	static final String DISCOUNT_ELIGIBILITY = "DISCOUNTELIGIBILITY";
	private static final String UNIT_ID = "UNIT_ID";

	ProductTableDataGateway(DataSource dataSource) {
		super(dataSource);
	}

	/**
	 * The select a product by product code SQL statement
	 */
	private static final String GET_PRODUCT_BY_PROD_COD_SQL = 
			"select * from product where " + PRODUCT_CODE + " = ?";

	/**
	 * Gets a product given its codProd 
	 * 
	 * @param prodCod The codProd of the product to search for
	 * @return The in-memory representation of the product
	 * @throws PersistenceException When there is an error interacting with the database
	 */
	public TableData findByProdCod (int prodCod) throws PersistenceException {
		try (PreparedStatement statement = dataSource.prepare(GET_PRODUCT_BY_PROD_COD_SQL)) {			
			// set statement arguments
			statement.setInt(1, prodCod);
			// execute SQL
			// Just to close the ResultSet, since same JDBC implementations (like MySql connector
			// and HSqlDB) are broken and do not close the ResultSet upon closing the Statement
			try (ResultSet rs = statement.executeQuery()) {
				return new TableData().populate(rs);
			}
		} catch (SQLException e) {
			throw new PersistenceException("Internal error obtaining a product by its code", e);
		}			
	}

	/**
	 * The select a product by Id SQL statement
	 */
	private static final String GET_PRODUCT_BY_ID_SQL = 
			"select * from product where " + ID + " = ?";

	/**
	 * Gets a product given its product id 
	 * 
	 * @param id The id of the product to search for
	 * @return The in-memory representation of the product
	 * @throws PersistenceException In case there is an error accessing the database.
	 */
	public TableData find (int id) throws PersistenceException {
		try (PreparedStatement statement = dataSource.prepare(GET_PRODUCT_BY_ID_SQL)) {			
			// set statement arguments
			statement.setInt(1, id);
			// execute SQL
			// see the comment in the findBProdCod
			try (ResultSet rs = statement.executeQuery()) {
				return new TableData().populate(rs);
			}
		} catch (SQLException e) {
			throw new PersistenceException("Internal error obtaining a product by its id", e);
		}			
	}

	/**
	 * The update product stock SQL statement
	 */
	private static final String	UPDATE_STOCK_SQL =
			"update product " +
					"set " + QTY + " = ? " +
					"where " + ID + " = ?";

	/**
	 * Updates the quantity in stock of a product id
	 * 
	 * @param id The internal code of the product to update its quantity
	 * @param qty The new product stock quantity
	 * @throws PersistenceException When there is an error interacting with the database
	 */
	public void updateStock(int id, double qty) throws PersistenceException {
		try (PreparedStatement statement = dataSource.prepare(UPDATE_STOCK_SQL)) {			
			// set statement arguments
			statement.setDouble(1, qty);
			statement.setInt(2, id);
			// execute SQL
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new PersistenceException("Internal error updating product stock", e);
		}			
	}



	/**
	 * Reads from a result set the value of the corresponding column
	 * @param rs 
	 * @return the conversion of the value read from the result set 
	 * @throws PersistenceException When there is an error in the reading or conversion
	 */
	public int readId(Row r) throws PersistenceException {
		return r.getInt(ID);
	}

	/**
	 * Reads from a result set the value of the corresponding column
	 * @param rs 
	 * @return the conversion of the value read from the result set 
	 * @throws PersistenceException When there is an error in the reading or conversion
	 */
	public int readProductCode(Row r) throws PersistenceException {
		return r.getInt(PRODUCT_CODE);
	}

	/**
	 * Reads from a result set the value of the corresponding column
	 * @param rs 
	 * @return the conversion of the value read from the result set 
	 * @throws PersistenceException When there is an error in the reading or conversion
	 */
	public String readDescription(Row r) throws PersistenceException {
		return r.getString(DESCRIPTION);
	}

	/**
	 * Reads from a result set the value of the corresponding column
	 * @param rs 
	 * @return the conversion of the value read from the result set 
	 * @throws PersistenceException When there is an error in the reading or conversion
	 */
	public double readFaceValue(Row r) throws PersistenceException {
		return r.getDouble(FACE_VALUE);
	}

	/**
	 * Reads from a result set the value of the corresponding column
	 * @param rs 
	 * @return the conversion of the value read from the result set 
	 * @throws PersistenceException When there is an error in the reading or conversion
	 */
	public double readQuantity(Row r) throws PersistenceException {
		return r.getDouble(QTY);
	}


	/**
	 * Reads from a result set the value of the corresponding column
	 * @param rs 
	 * @return the conversion of the value read from the result set 
	 * @throws PersistenceException When there is an error in the reading or conversion
	 */
	public boolean readDiscountElibility(Row r) throws PersistenceException {
		return r.getInt(DISCOUNT_ELIGIBILITY)==1 ;
	}

	/**
	 * Reads from a result set the value of the corresponding column
	 * @param rs 
	 * @return the conversion of the value read from the result set 
	 * @throws PersistenceException When there is an error in the reading or conversion
	 */
	public double readUnitId(Row r) throws PersistenceException {
		return r.getDouble(UNIT_ID);
	}

}
