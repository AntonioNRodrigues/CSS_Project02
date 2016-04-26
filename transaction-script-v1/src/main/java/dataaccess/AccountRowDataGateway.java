package dataaccess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import business.ApplicationException;

/**
 * This class represents a Customer Account
 * It is responsible for establishing database communications
 * in Customer's Account manipulation
 * 
 * @author Joao Rodrigues
 *
 */
public class AccountRowDataGateway {
	
	/**
	 * account's id
	 */
	private int id;
	
	/**
	 * Account owner
	 */
	private int customerId;
	
	/**
	 * Account description
	 */
	private String description;
	
	/**
	 * Account creation date
	 */
	private Date createdAt;
	
	/**
	 * Constructor
	 */
	public AccountRowDataGateway(){}
	
	/**
	 * Constructor
	 * 
	 * @param customerId, Account owner id
	 * @param description, Account description
	 */
	public AccountRowDataGateway(int customerId, String description){
		this.customerId = customerId;
		this.description = description;
	}
	
	// GETTERS
	public int getId(){
		return this.id;
	}
	
	public int getCustomerId(){
		return this.customerId;
	}
	
	public String getDescription(){
		return this.description;
	}
	
	public Date getCreatedAt(){
		return this.createdAt;
	}
	
	// SETTERS
	public void setId(int id){
		this.id = id;
	}
	
	public void setCustomerId(int customerId){
		this.customerId = customerId;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
	public void setCreatedAt(Date createdAt){
		this.createdAt = createdAt;
	}
	
	
	/**
	 * select account by customer id query
	 */
	private final static String FETCH_BY_CUSTOMER_ID_SQL = "select * from account "
			+ "where customer_id = ?";
	
	/**
	 * Gets all AccountRowGateway form a customer
	 * 
	 * @param customerId, customer id
	 * @return correspondent AccountRowDataGateway
	 * @throws ApplicationException
	 */
	public static List<AccountRowDataGateway> 
	getAccountRowDataGatewayByCustomerId(int customerId) throws ApplicationException{
		
		try(PreparedStatement statement = DataSource.INSTANCE.prepare(FETCH_BY_CUSTOMER_ID_SQL)){
			
			statement.setInt(1, customerId);
			ResultSet rs = statement.executeQuery();
			return load(rs);
			
		}catch(SQLException e){
			throw new ApplicationException("SQL error", e);
		}catch(PersistenceException e){
			throw new ApplicationException("Persistence execption", e);
		}
	}
	
	/**
	 * insert new Account query
	 */
	private final static String INSERT_SQL = "insert into account (customer_Id, description, created_At) "
			+ "values (?, ?, ?)";
	
	/**
	 * Insert a new Customer's Account
	 * 
	 * @return Created Account's id
	 * @throws ApplicationException
	 */
	public int insert() throws ApplicationException{
		
		// insert the new account
		try(PreparedStatement statement = DataSource.INSTANCE.prepare(INSERT_SQL)){
			
			statement.setInt(1, this.customerId);
			statement.setString(2, this.description);
			statement.setDate(3, new java.sql.Date(new Date().getTime()));
			int created = statement.executeUpdate();
			System.out.println("Created: " + created);
			
			// get inserted account's id
			try(ResultSet rs = statement.getGeneratedKeys()){
//				rs.next();
//				return rs.getInt(1);
				return 1;
			}
			
		}catch(SQLException e){
			throw new ApplicationException("SQL command error", e);
		}catch(PersistenceException e){
			throw new ApplicationException("Error persisting new Account", e);
		}
		
	}
	
	/**
	 * Loads a ResultSet into a AccountRowDataGateway
	 * 
	 * @param rs, result set
	 * @return an AccountRowDataGateway with the corresponding details
	 * @throws ApplicationException
	 */
	private static List<AccountRowDataGateway> load(ResultSet rs) throws ApplicationException{
		try {
			List<AccountRowDataGateway> list = new ArrayList<>();
			
			while(rs.next()){
			AccountRowDataGateway account = new AccountRowDataGateway();
			account.setId(rs.getInt("id"));
			account.setCustomerId(rs.getInt("customer_id"));
			account.setDescription(rs.getString("description"));
			account.setCreatedAt(rs.getDate("created_at"));
			list.add(account);
			}
			return list;
		} catch (SQLException e) {
			throw new ApplicationException("Error getting Account from result set", e);
		}
	}
	
}
