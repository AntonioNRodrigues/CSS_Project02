package dataaccess;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import business.SaleStatus;
import dataaccess.TableData.Row;

/**
 * Table Data Gateway for the sales's table
 * 
 * Remarks:
 * 1. See notes in class CustomerTableGateway 
 *  
 * 2. When creating a sale, the newSale method returns the id of the
 * sale just added, so we can add products to the brand new sale. Otherwise,
 * it would not be possible to determine to which sale to add products. 
 * Keep an attribute (in this class) to represent the current sale is not
 * a good solution for many reasons, namely, there can be more than one
 * open sale, and, in case we want to keep a session with the user so 
 * we can isolate the underway sales, the responsibility of keeping the
 * "current sale" should be the responsibility of the class handling the
 * session and not of this class.
 * 
 * 3. The usage of ResultSets is a common practice in this kind of 
 * database interface, but it has the drawback of not being typed and
 * the compiler cannot emit error messages during the compilation phase, 
 * like field type mismatches, unknown field names. All this errors,
 * in case they exist, will can during runtime (hopefully during the
 * testing phase). In most cases it give rises to very boring testing 
 * sessions with errors popping one after the other...
 *  
 * @author fmartins
 * @version 1.1 (5/10/2015)
 *
 */
public class SaleTableDataGateway extends TableDataGateway {

	/**
	 * Columns' labels
	 */
	private static final String ID = "ID";
	private static final String DATE = "DATE";
	private static final String TOTAL = "TOTAL";
	private static final String DISCOUNT = "DISCOUNT_TOTAL";
	private static final String STATUS = "STATUS";
	private static final String CUSTOMER_ID = "CUSTOMER_ID";

	/**
	 * Strings used to register the status of a sale in the Sale table
	 */
	private static final String CLOSED = "C";
	private static final String OPEN = "O";
	private static final String PAYED = "P";

	SaleTableDataGateway(DataSource dataSource) {
		super(dataSource);
	}

	/**
	 * The insert sale SQL statement
	 */
	private static final String INSERT_SALE_SQL = 
			"insert into sale (id, " + DATE + ", " + TOTAL + ", " + DISCOUNT + ", " +
					STATUS + ", " + CUSTOMER_ID + ") " +
					"values (DEFAULT, ?, 0, 0, '" + OPEN + "', ?)";

	/**
	 * Add a new sale to the database
	 * 
	 * @param customerId The customer id of the sale
	 * @return The sale id just created
	 * @throws PersistenceException In case an internal database error occurs
	 */
	public int newSale (int customerId) throws PersistenceException {
		try (PreparedStatement statement = dataSource.prepareGetGenKey(INSERT_SALE_SQL)) {
			// set statement arguments
			statement.setDate(1, (new Date(new java.util.Date().getTime())));
			statement.setInt(2, customerId);
			// execute SQL
			statement.executeUpdate();
			// Gets sale Id generated automatically by the database engine
			try (ResultSet rs = statement.getGeneratedKeys()) {
				rs.next(); 
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			throw new PersistenceException ("Internal error inserting a new sale", e);
		}
	}


	/**
	 * The select a sale by Id SQL statement
	 */
	private static final String GET_SALE_SQL = 
			"select *  from sale " + "where " + ID + " = ?";

	/**
	 * Gets a sale by its id 
	 * 
	 * @param saleId The sale id to search for
	 * @return The result set with information about the sale
	 * @throws PersistenceException In case there is an error accessing the database.
	 */
	public TableData find (int saleId) throws PersistenceException {
		try (PreparedStatement statement = dataSource.prepare(GET_SALE_SQL)) {		
			// set statement arguments
			statement.setInt(1, saleId);		
			// execute SQL
			try (ResultSet rs = statement.executeQuery()) {
				TableData td = new TableData();
				td.populate(rs);
				return td;
			}
		} catch (SQLException e) {
			throw new PersistenceException("Internal error getting a sale", e);
		} 		
	}

	/**
	 * The select a sale by Id SQL statement
	 */
	private static final String EXISTS_SALE_SQL = 
			"select count(*) from sale " +
					"where " + ID + " = ?";

	public boolean existsSale(int saleId) throws PersistenceException {
		try (PreparedStatement statement = dataSource.prepare(EXISTS_SALE_SQL)) {		
			// set statement arguments
			statement.setInt(1, saleId);		
			// execute SQL
			try (ResultSet rs = statement.executeQuery()) {
				rs.next();
				return rs.getInt(1) != 0;
			}
		} catch (SQLException | PersistenceException e) {
			throw new PersistenceException("Internal error getting a sale", e);
		} 		
	}

	/**
	 * The update a sale by Id SQL statement
	 */
	private static final String UPDATE_SALE_SQL =
			"update sale set " + STATUS + " = ?, " + TOTAL + " = ?, " + DISCOUNT + " = ? where " + ID + " = ?";

	/**
	 * Updates a Sale with the parameters received
	 * @param saleId Sale Id of the Sale to be updated
	 * @param status Status to be updated
	 * @param total Total to be updated
	 * @param discount Discount to be updated
	 * @return True if the update in the DB was successful
	 * @throws PersistenceException
     */
	public boolean updateSale(int saleId, SaleStatus status, double total, double discount) throws PersistenceException {
		try (PreparedStatement statement = dataSource.prepare(UPDATE_SALE_SQL)) {

			// set statement arguments
			String finalStatus = OPEN;
			switch (status) {
				case OPEN:
					finalStatus = OPEN;
					break;
				case CLOSED:
					finalStatus = CLOSED;
					break;
				case PAYED:
					finalStatus = PAYED;
					break;
			}
			statement.setString(1, finalStatus);
			statement.setDouble(2, total);
			statement.setDouble(3, discount);
			statement.setInt(4, saleId);

			// execute SQL
			statement.executeUpdate();
			return true;
		} catch (SQLException | PersistenceException e) {
			throw new PersistenceException("Internal error while updating a sale", e);
		}
	}

	/**
	 * Reads from a result set the value of the corresponding column
	 * @param r
	 * @return the conversion of the value read from the result set 
	 * @throws PersistenceException When there is an error in the reading or conversion
	 */
	public int readId(Row r) throws PersistenceException{
		return r.getInt(ID);
	}

	/**
	 * Reads from a result set the value of the corresponding column
	 * @param r
	 * @return the conversion of the value read from the result set 
	 * @throws PersistenceException When there is an error in the reading or conversion
	 */
	public Date readDate(Row r) throws PersistenceException{
		return r.getDate(DATE);
	}

	/**
	 * Reads from a result set the value of the corresponding column
	 * @param r
	 * @return the conversion of the value read from the result set 
	 * @throws PersistenceException When there is an error in the reading or conversion
	 */
	public double readTotal(Row r) throws PersistenceException{
		return r.getDouble(TOTAL);
	}

	/**
	 * Reads from a result set the value of the corresponding column
	 * @param r
	 * @return the conversion of the value read from the result set 
	 * @throws PersistenceException When there is an error in the reading or conversion
	 */
	public double readDiscount(Row r) throws PersistenceException{
		return r.getDouble(DISCOUNT);
	}

	/**
	 * Reads from a result set the value of the corresponding column
	 * @param r
	 * @return the conversion of the value read from the result set 
	 * @throws PersistenceException When there is an error in the reading or conversion
	 */
	public SaleStatus readStatus(Row r) throws PersistenceException{
		String status = r.getString(STATUS);
		if (status.equals(OPEN))
			return SaleStatus.OPEN;
		else if (status.equals(CLOSED))
			return SaleStatus.CLOSED;
		else if (status.equals(PAYED))
			return SaleStatus.PAYED;
		else
			throw new PersistenceException("Internal error reading sale status");
	}
	
	/**
	 * Reads from a result set the value of the corresponding column
	 * @param r
	 * @return the conversion of the value read from the result set 
	 * @throws SQLException When there is an error in the reading or conversion
	 */
	public int readCustomerId(Row r) throws PersistenceException{
		return r.getInt(CUSTOMER_ID);
	}

	/**
	 * The select all sales by customer_id SQL statement
	 */
	private static final String GET_ALL_SALES_FROM_CUSTOMER_SQL =
			"select * from sale " + "where " + CUSTOMER_ID + " = ?";

	/**
	 * Gets All the Sales from a Customer, by it's Id
	 * @param customerId Customer Id that has the Sales to be returned
	 * @return TableData with Rows of data that represent Sales
	 * @throws PersistenceException
     */
	public TableData getAllSalesFromCustomer(int customerId) throws PersistenceException {
		try (PreparedStatement statement = dataSource.prepare(GET_ALL_SALES_FROM_CUSTOMER_SQL)) {
			// set statement arguments
			statement.setInt(1, customerId);
			// execute SQL
			try (ResultSet rs = statement.executeQuery()) {
				TableData td = new TableData();;
				td.populate(rs);
				return td;
			}
		} catch (SQLException e) {
			throw new PersistenceException("Internal error getting all sales ids", e);
		} catch (PersistenceException e) {
			e.printStackTrace();
		}
		return null;
	}
}