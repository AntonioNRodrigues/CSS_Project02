package dataaccess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import business.ApplicationException;
import business.TransactionType;

public class SaleTransactionRowDataGateway {

	/**
	 * represents the database expected value for a credit transaction
	 */
	private static final int CREDIT = 1;
	private static final String CREDIT_STRING = "CREDIT";
	
	/**
	 * represents the database expected value for a debit transaction
	 */
	private static final int DEBIT = 2;
	private static final String DEBIT_STRING = "DEBIT";
	
	/**
	 * transaction id
	 */
	private int id;
	
	/**
	 * sale id
	 */
	private int saleId;
	
	/**
	 * transaction value
	 */
	private double value;
	
	/**
	 * transaction type
	 */
	private TransactionType type;
	
	/**
	 * created at
	 */
	private Date createdAt;
	
	/**
	 * Constructor
	 * 
	 * @param saleId, sale id
	 * @param value, transaction value
	 * @param type, transaction type
	 */
	public SaleTransactionRowDataGateway(int saleId, double value, TransactionType type){
		this.saleId = saleId;
		this.value = value;
		this.type = type;
	}
	
	/**
	 * Set sale transaction id to given value
	 * 
	 * @param id, the value to be set to id field
	 */
	private void setId(int id){
		this.id = id;
	}
	
	private void setCreatedAt(Date date){
		this.createdAt = date;
	}	
	
	/**
	 * insert sql
	 */
	private static final String INSERT_SQL = "insert into sale_transaction "
			+ "(sale_id, transaction_type, transaction_value, created_at) "
			+ "values (?, ?, ?, ?)";
	
	/**
	 * Insert a new transaction insert database
	 * 
	 * @throws PersistenceException
	 */
	public void insert() throws PersistenceException{
		
		try(PreparedStatement statement = DataSource.INSTANCE.prepare(INSERT_SQL)){
			
			statement.setInt(1, this.saleId);
			statement.setInt(2, convertTransactionType(this.type));
			statement.setDouble(3, this.value);
			statement.setDate(4, new java.sql.Date(new Date().getTime()));
			statement.executeUpdate();
			
		} catch (SQLException e) {
			throw new PersistenceException("Error when inserting a new transaction", e);
		} catch (PersistenceException e) {
			throw new PersistenceException("Error when inserting a new transaction", e);
		}
		
	}
	
	/**
	 * select to get all sale transactions
	 */
	private static final String GET_SALE_TRANSACTIONS_SQL = "select "
			+ "id, sale_id, transaction_type, transaction_value, created_at "
			+ "from sale_transaction "
			+ "where sale_id = ?";
	
	
	// GETTERS
	public int getId() {
		return id;
	}

	public int getSaleId() {
		return saleId;
	}

	public double getValue() {
		return value;
	}

	public TransactionType getType() {
		return type;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * Gets all sale transactions
	 * 
	 * @param saleId, sale id to be considered
	 * @return a list with string represented sale transactions
	 * @throws ApplicationException
	 */
	public static List<String> getSaleTransactions(int saleId) throws ApplicationException{
		
		try (PreparedStatement statement = DataSource.INSTANCE.prepare(GET_SALE_TRANSACTIONS_SQL)) {
			
			statement.setInt(1, saleId);
			ResultSet rs = statement.executeQuery();
			
			// list of sale transactions
			List<SaleTransactionRowDataGateway> prods = loadAll(rs);
			
			// convert to string
			List<String> list = new ArrayList<>();
			for(SaleTransactionRowDataGateway sale : prods)
				list.add(sale.toString());
			
			return list;
			
		} catch (PersistenceException | SQLException e) {
			throw new ApplicationException("Error when obtaining sale transactions", e);
		}
		
	}
	
	private static final String GET_TRANSACTION_BY_ID_SQL = "select "
			+ "id, sale_id, transaction_type, transaction_value, created_at "
			+ "from sale_transaction "
			+ "where id = ?";
	
	public static SaleTransactionRowDataGateway getTransactionById(int transactionId)
	throws PersistenceException{
		
		try (PreparedStatement statement = DataSource.INSTANCE.prepare(GET_TRANSACTION_BY_ID_SQL)) {
			
			statement.setInt(1, transactionId);
			ResultSet rs = statement.executeQuery();
			return loadAll(rs).get(0);
			
		} catch (PersistenceException | SQLException e) {
			throw new PersistenceException("Error getting sale transaction by id", e);
		}
		
	}
	
	/**
	 * Parses the given list and returns a list of SaleTransaction
	 * 
	 * @param rs, result set to be considered when parsing
	 * @return the SaleTransaction corresponding list
	 * @throws PersistenceException
	 */
	private static List<SaleTransactionRowDataGateway> loadAll(ResultSet rs) throws PersistenceException{
		
		List<SaleTransactionRowDataGateway> list = new ArrayList<>();
		try {
			while(rs.next())
			{
				SaleTransactionRowDataGateway st = new SaleTransactionRowDataGateway(
						rs.getInt("sale_id"), rs.getDouble("transaction_value"), 
						convertTransactionType(rs.getInt("transaction_type")));
				st.setId(rs.getInt("id"));
				st.setCreatedAt(rs.getDate("created_at"));
				list.add(st);
				
			}
			return list;
		} catch (SQLException e) {
			throw new PersistenceException("", e);
		}
		
	}
	
	/**
	 * Converts an integer into a transaction type
	 * 
	 * @param type, integer to be considered
	 * @return the corresponding transaction type
	 */
	private static TransactionType convertTransactionType(int type){
		return type == CREDIT ? TransactionType.CREDIT : TransactionType.DEBIT;
	}
	
	/**
	 * Converts a TransactionType into the corresponding integer
	 * 
	 * @param type, TransactionType to be considered
	 * @return the corresponding integer to the given TransactionType
	 */
	private int convertTransactionType(TransactionType type){
		return type == TransactionType.CREDIT ? CREDIT : DEBIT;
	}
	
	/**
	 * Converts a TransactionType into the corresponding string
	 * 
	 * @param t, TransactionType to be converted
	 * @return string representation of t
	 */
	private String transactionTypeToString(TransactionType t){
		return t == TransactionType.CREDIT ? CREDIT_STRING : DEBIT_STRING;
	}
	
	/**
	 * Checks if is credit type transaction or not
	 * 
	 * @return true if so, otherwise false
	 */
	public boolean isCredit(){
		return this.type == TransactionType.CREDIT;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		
		sb.append("ID: " + this.id + " | ");
		sb.append("TYPE " + transactionTypeToString(this.type) + " | ");
		sb.append("AMOUNT: " + this.value + " | ");
		sb.append("CREATED AT: " + this.createdAt.getDay()+ "/");
		sb.append(this.createdAt.getMonth() + "/" + this.createdAt.getYear());
		
		return sb.toString();
	}
}
