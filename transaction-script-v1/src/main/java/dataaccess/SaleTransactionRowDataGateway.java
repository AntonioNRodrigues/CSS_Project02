package dataaccess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import business.ApplicationException;
import business.TransactionType;
import domain.CreditTransaction;
import domain.SaleProduct;

/**
 * This class represents a sale transaction script
 * It is responsible for all transactions related operations
 * 
 * @author JoaoR
 *
 */
public class SaleTransactionRowDataGateway {
	
	/**
	 * constants to be used when converting from and to database value
	 */
	public static final int CREDIT_TRANSACTION = 1;
	public static final int DEBIT_TRANSACTION = -1;
	
	/**
	 * transaction id
	 */
	private int id;
	
	/**
	 * transaction sale id
	 */
	private int saleId;
	
	/**
	 * transaction type
	 */
	private TransactionType type;
	
	/**
	 * transaction value
	 */
	private double value;
	
	/**
	 * the date when transaction was made
	 */
	private Date createdAt;
	
	/**
	 * Constructor 
	 * 
	 * @param saleId, transaction sale id
	 * @param type, transaction type
	 * @param value, transaction value
	 */
	public SaleTransactionRowDataGateway(int saleId, TransactionType type, double value){
		this.saleId = saleId;
		this.type = type;
		this.value = value;
	}
	
	/**
	 * Constructor
	 * 
	 * @param id, sale transaction id
	 * @param saleId, sale id
	 * @param type, transaction type
	 * @param value, transaction value
	 * @param createdAt, transaction creation date
	 */
	public SaleTransactionRowDataGateway(int id, int saleId, TransactionType type, double value, Date createdAt){
		this.id = id;
		this.saleId = saleId;
		this.type = type;
		this.value = value;
		this.createdAt = createdAt;
	}
	
	
	// GETTERS and SETTERS
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSaleId() {
		return saleId;
	}

	public void setSaleId(int saleId) {
		this.saleId = saleId;
	}

	public TransactionType getType() {
		return type;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}



	/**
	 * SQL insert command
	 */
	private static final String INSERT_SQL = "insert into sale_transaction (sale_id, "
			+ "transaction_type, transaction_value, created_at) "
			+ "values (?, ?, ?, ?)";
	
	/**
	 * Persists this object on database with it's own attribute values
	 * 
	 * @throws ApplicationException
	 */
	public void insert() throws ApplicationException{
		
		/**
		 * Insert new sale transaction row
		 */
		try(PreparedStatement statement = DataSource.INSTANCE.prepare(INSERT_SQL)){
			
			// set it's parameters
			statement.setInt(1, this.saleId);
			statement.setInt(2, this.convertTransactionTypeToDB(this.type));
			statement.setDouble(3, this.value);
			statement.setDate(4, new java.sql.Date(new Date().getTime()));
			
			// execute sql insert
			statement.executeUpdate();
			
			// obtain the inserted id
			try(ResultSet rs = statement.getGeneratedKeys();)
			{
				rs.next();
				this.id = rs.getInt(1);
			} catch (SQLException e) {
				throw new ApplicationException("Error when getting new sale transaction inserted id.");
			}
			
		} catch (SQLException | PersistenceException e) {
			throw new ApplicationException("Error: SQL Exception when inserting sale transaction.", e);
		}
		
	}
	
	/**
	 * Get transaction by id query
	 */
	private static final String GET_TRANSACTION_BY_ID = "select * from sale_transaction "
			+ "where id = ?";
	/**
	 * Gets a transaction by it's id
	 * 
	 * @param transactionId, transaction id
	 * @return the corresponding sale transaction
	 * 
	 * @throws PersistenceException
	 */
	public static final SaleTransactionRowDataGateway  getTransactionById(int transactionId)
		throws PersistenceException{
		
		try(PreparedStatement statement = DataSource.INSTANCE.prepare(GET_TRANSACTION_BY_ID)){
			
			// set transaction id
			statement.setInt(1, transactionId);
			
			// execute query
			try(ResultSet rs = statement.executeQuery()){
				return load(rs);
				
			} catch (SQLException e) {
				throw new PersistenceException("Error getting transaction details", e);
			}
			
			
		} catch (SQLException | PersistenceException e) {
			throw new PersistenceException("", e);
		}
		
	}
	
	
	/**
	 * select sale transactions by sale id
	 */
	private static final String GET_SALE_TRANSACTIONS_BY_SALE_ID_SQL = 
			"select * from sale_transaction st "
			+ "where sale_id = ?";
	
	/**
	 * Gets all sale transactions of a given sale
	 * 
	 * @param saleId, sale id to be considered
	 * @return a list with sale transaction from the corresponding sale
	 * 
	 * @throws ApplicationException
	 */
	public static List<SaleTransactionRowDataGateway> getSaleTransactions(int saleId)
			throws PersistenceException{
		
		try(PreparedStatement statement = 
				DataSource.INSTANCE.prepare(GET_SALE_TRANSACTIONS_BY_SALE_ID_SQL)){
			
			// set transaction sale id
			statement.setInt(1, saleId);
			
			// fetch results
			ResultSet rs = statement.executeQuery();
			try{				
				return loadAll(rs);
			} catch (ApplicationException e)
			{
				throw new PersistenceException("Error loading sale transaction", e);
			}
			
		} catch (SQLException | PersistenceException e) {
			throw new PersistenceException("", e);
		}
		
	}
	
	/**
	 * Loads a result set into a list of sale transactions
	 * 
	 * @param rs, result set to be used
	 * @return the generated list
	 * 
	 * @throws ApplicationException
	 */
	private static List<SaleTransactionRowDataGateway> loadAll(ResultSet rs)
		throws ApplicationException{
		
		// objects list
		List<SaleTransactionRowDataGateway> list = new ArrayList<>();
		
		try{
			// load all result rows into objects
			while(rs.next())
			{
				SaleTransactionRowDataGateway t =
						new SaleTransactionRowDataGateway(
								rs.getInt("sale_id"),
								convertTransactionTypeFromDB(rs.getInt("transaction_type")),
								rs.getDouble("transaction_value")
								);
				t.setId(rs.getInt("id"));
				t.setCreatedAt(rs.getDate("created_at"));
				list.add(t);
			}
			
			return list;
			
		} catch (SQLException e) {
			throw new ApplicationException("Error loading sale transactions", e);
		} 
		
	}
	
	/**
	 * Loads a result set row into a SaleTransactionRowDataGateway object
	 * 
	 * @param rs, ResultSet to use
	 * @return the corresponding generated object
	 */
	private static SaleTransactionRowDataGateway load(ResultSet rs) throws PersistenceException{
		try{			
			rs.next();
			return new SaleTransactionRowDataGateway(
					rs.getInt("id"),
					rs.getInt("sale_id"),
					convertTransactionTypeFromDB(rs.getInt("transaction_type")),
					rs.getDouble("transaction_value"),
					rs.getDate("created_at")
					);
		} catch (SQLException e) {
			throw new PersistenceException("Error loading sale transction object", e);
		}
		
	}
	
	
	/**
	 * Converts a transaction type into the corresponding integer value
	 * 
	 * @param type, TransactionType to be considered
	 * @return the corresponding integer value
	 */
	private int convertTransactionTypeToDB(TransactionType type){
		return type == TransactionType.CREDIT ? CREDIT_TRANSACTION : DEBIT_TRANSACTION; 
	}
	
	/**
	 * Converts an integer into the corresponding TransactionType value
	 * 
	 * @param type, integer to be considered
	 * @return the corresponding TransactionType enum
	 */
	private static TransactionType convertTransactionTypeFromDB(int type){
		return type == CREDIT_TRANSACTION ? TransactionType.CREDIT : TransactionType.DEBIT;
	}
}
