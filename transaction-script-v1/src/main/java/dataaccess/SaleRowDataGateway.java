package dataaccess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import business.SaleStatus;

/**
 * An in-memory representation of a customer table record. 
 *	
 * Notes:
 * 1. See the notes for the CustomerRowGateway class
 * 
 * 2. Java makes available two Date classes (in fact, more in Java 8, 
 * but we will address it later (with JPA)): one in the package 
 * java.util, which is the one we normally use, and another in
 * java.sql, which is a subclass of java.util.date and that 
 * transforms the milliseconds representation according to the
 * "Date type of databases". For more information refer to 
 * http://download.oracle.com/javase/6/docs/api/java/sql/Date.html.
 * 
 * 3. When creating a new sale, we only pass the date and customer id 
 * parameters to the constructor. Moreover, attribute open is always 
 * set to 'O'. The remaining attributes are either set automatically (id) 
 * or when closing the sale (totalSale and totalDiscount). Also, the open 
 * attribute is set to 'C' upon payment. 
 * 
 * @author fmartins
 * @Version 1.2 (13/02/2015)
 *
 */
public class SaleRowDataGateway {
	
	
	// Sale attributes
	
	/**
	 * The sale's id (unique, primary key)
	 */
	private int id;

	/**
	 * The date the sale was made 
	 */
	private java.sql.Date date;

	/**
	 * The sale's total
	 */
	private double total;
	
	/**
	 * The total discount for the sale
	 */
	private double discount;
	
	/**
	 * Whether the sale is open or closed. See constants below.
	 */
	private String status;
	
	/**
	 * The customer Id the sales refers to
	 */
	private int customerId;
	
		
	/**
	 * A pair of constants to map the status of a sale to a string
	 */
	private static final String CLOSED = "C";
	private static final String OPEN = "O";
	private static final String PAYED = "P";
	

	// 1. constructor 

	/**
	 * Creates a new sale given the client Id and the date it occurs.
	 * 
	 * @param customerId The customer Id the sale belongs to
	 * @param date The date the sale took place
	 */
	public SaleRowDataGateway(int customerId, Date date) {
		this.date = new java.sql.Date(date.getTime());
		setStatus(SaleStatus.OPEN);
		this.customerId = customerId;
	}

	
	// 2. getters and setters

	/**
	 * @return The sale's date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @return The sale's total
	 */
	public double getTotal() {
		return total;
	}

	/**
	 * Updates the sales total
	 * 
	 * @param total The new total
	 */
	public void setTotal(double total) {
		this.total = total;
	}

	/**
	 * @return The sale's discount
	 */
	public double getDiscount() {
		return discount;
	}

	/**
	 * Updates the sales total discount
	 * 
	 * @param discount The new discount total amount
	 */
	public void setDiscount(double discount) {
		this.discount = discount;
	}

	/**
	 * @return The sale's status. 
	 */
	public SaleStatus getStatus() {
		return status.equals(OPEN) ? SaleStatus.OPEN :
			status.equals(PAYED) ? SaleStatus.PAYED : SaleStatus.CLOSED;
	}

	/**
	 * Updates the sale's status
	 * Notice that the setter and the getter convert the string to an 
	 * enumerated value (of type SaleStatus) that is then used in the
	 * application's domain logic.
	 * 
	 * @param status The new sale status
	 */
	public void setStatus(SaleStatus status) {
		this.status = status == SaleStatus.OPEN ? OPEN : 
			status == SaleStatus.PAYED ? PAYED : CLOSED;
	}

	/**
	 * @return The client's Id associated with this sale
	 */
	public int getClientId() {
		return customerId;
	}

	/**
	 * Note: there is no setId because the id is automatically set
	 * by the database engine.
	 * 
	 * @return The sale's Id. 
	 */
	public int getId() {
		return id;
	}

	
	// 3. interaction with the repository (a memory map in this simple example)
	
	/**
	 * The insert sale SQL statement
	 */
	private static final String INSERT_SALE_SQL = 
			"insert into sale (date, total, discount_total, status, customer_id) " +
					"values (?, 0, 0, '" + OPEN + "', ?)";

	/**
	 * Stores the information in the repository
	 */
	public void insert () throws PersistenceException {		
		try (PreparedStatement statement = DataSource.INSTANCE.prepareGetGenKey(INSERT_SALE_SQL)) {
			// set statement arguments
			statement.setDate(1, date);
			statement.setInt(2, customerId);
			// execute SQL
			statement.executeUpdate();
			// Gets sale Id generated automatically by the database engine
			try (ResultSet rs = statement.getGeneratedKeys()) {
				rs.next(); 
				id = rs.getInt(1);
			}
		} catch (SQLException e) {
			throw new PersistenceException ("Internal error inserting a new sale!", e);
		}
	}
	
		
	/**
	 * The select a sale by Id SQL statement
	 */
	private static final String GET_SALE_SQL = 
			"select id, date, total, discount_total, status, customer_id " +
				"from sale " +
				"where id = ?";
		
	/**
	 * Gets a sale by its id 
	 * 
	 * @param id The sale id to search for
	 * @return The new object that represents an in-memory sale
	 * @throws PersistenceException In case there is an error accessing the database.
	 */
	public static SaleRowDataGateway getSaleById (int id) throws PersistenceException {
		try (PreparedStatement statement = DataSource.INSTANCE.prepare(GET_SALE_SQL)) {		
			// set statement arguments
			statement.setInt(1, id);		
			// execute SQL
			try (ResultSet rs = statement.executeQuery()) {
				// creates a new customer with the data retrieved from the database
				return loadSale(rs);
			}
		} catch (SQLException e) {
			throw new PersistenceException("Internal error getting a sale", e);
		} 
	}
	
	/**
	 * update sql query
	 */
	private static final String UPDATE_SALE_SQL = "update sale "
			+ "set total = ?, discount_total = ?, status = ? "
			+ "where id = ?";
	
	/**
	 * Updates a sale row on database
	 * based on it's current attributes values
	 * 
	 * @throws PersistenceException
	 */
	public void update() throws PersistenceException{
		
		try(PreparedStatement statement = DataSource.INSTANCE.prepare(UPDATE_SALE_SQL)){
			
			// populated sql parameters
			statement.setDouble(1, this.total);
			statement.setDouble(2, this.discount);
			statement.setString(3, this.status);
			statement.setInt(4, this.id);
			
			// execute database update
			if(statement.executeUpdate() != 1)
				throw new PersistenceException("Error updating sale. No modified rows.");
			
		} catch (SQLException | PersistenceException e) {
			throw new PersistenceException("Error updating sale", e);
		}
		
	}
	
	/**
	 * Creates a sale from a result set retrieved from the database.
	 * 
	 * @param rs The result set with the information to create the sale.
	 * @return A new sale loaded from the database.
	 * @throws RecordNotFoundException In case the result set is empty.
	 */
	private static SaleRowDataGateway loadSale(ResultSet rs) throws RecordNotFoundException {
		try {
			rs.next();
			SaleRowDataGateway newSale = new SaleRowDataGateway(rs.getInt("customer_id"), 
					rs.getDate("date"));
			newSale.id = rs.getInt("id");
			newSale.total = rs.getDouble("total");
			newSale.discount = rs.getDouble("discount_total");
			newSale.status = rs.getString("status"); 
			return newSale;
		} catch (SQLException e) {
			System.out.println("Oops...");
			e.printStackTrace();
			System.out.println("Oops...");
			throw new RecordNotFoundException ("Sale does not exist	", e);
		}
	}
	
	private static List<SaleRowDataGateway> loadSales(ResultSet rs) throws SQLException{
		List<SaleRowDataGateway> list = new ArrayList<>();
		
		while(rs.next())
		{
			SaleRowDataGateway newSale = new SaleRowDataGateway(rs.getInt("customer_id"), 
					rs.getDate("date"));
			newSale.id = rs.getInt("id");
			newSale.total = rs.getDouble("total");
			newSale.discount = rs.getDouble("discount_total");
			newSale.status = rs.getString("status");
			
			// add to list
			list.add(newSale);
		}
		
		return list;
	}

	/**
	 * select sales by customer id sql
	 */
	private static final String GET_SALES_BY_CUSTOMER_ID_SQL = 
			"select * "
			+ "from sale s "
			+ "where customer_id = ?";
	
	/**
	 * Gets a list of sales based on given customer id
	 * 
	 * @param customerId, customer id to be considered
	 * @return a list of sales
	 */
	public static List<SaleRowDataGateway> getSalesByCustomerId(int customerId) 
			throws PersistenceException{
		
		try(PreparedStatement statement = DataSource.INSTANCE.prepare(GET_SALES_BY_CUSTOMER_ID_SQL)){
			
			// set customer id
			statement.setInt(1, customerId);
			
			// fetch sales
			ResultSet rs = statement.executeQuery();
			
			// loading all sales and return
			return loadSales(rs);
			
			
		} catch (SQLException | PersistenceException e) {
			e.printStackTrace();
			throw new PersistenceException("Error getting sales by customer id.", e);
		}
		
	}
}
