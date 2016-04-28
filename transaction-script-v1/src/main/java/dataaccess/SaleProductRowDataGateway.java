package dataaccess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import business.ApplicationException;

/**
 * An in-memory representation of a row gateway for the products composing a sale.	
 * 
 * @author fmartins
 * @version 1.1 (19/02/2015)
 *
 */
public class SaleProductRowDataGateway {
	
	// Sale product attributes 

	private int id;
	
	/**
	 * The sale the product belongs to
	 */
	private int saleId;

	/**
	 * The product of the sale
	 */
	private int productId;

	/**
	 * The number of items purchased
	 */
	private double qty;
	

	// 1. constructor

	/**
	 * Creates an association between a saleId and a productId. The qty is the 
	 * quantity of items in the sale. 
	 * 
	 * @param saleId The sale to associate a product to.
	 * @param productId The product to be associated with the sale.
	 * @param qty The number of products sold.
	 */
	public SaleProductRowDataGateway(int saleId, int productId, double qty) {
		this.saleId = saleId;
		this.productId = productId;
		this.qty = qty;
	}
	

	// 2. getters and setters

	/**
	 * @return The product sale id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return The sale id of the product sale
	 */
	public int getSaleId() {
		return saleId;
	}

	/**
	 * @return The product id of the product sale
	 */
	public int getProductId() {
		return productId;
	}

	/**
	 * @return The quantity of the product sale
	 */
	public double getQty() {
		return qty;
	}

	/**
	 * Increases quantity n times
	 * 
	 * @param n amount to add
	 */
	public void increaseQty(double n)
	{
		this.qty += n;
	}
	
	
	// 3. interaction with the repository (a relational database in this simple example)

	/**
	 * The insert product in a sale SQL statement
	 */
	private static final String INSERT_PRODUCT_SALE_SQL = 
			"insert into saleproduct (id, sale_id, product_id, qty) " +
				"values (DEFAULT, ?, ?, ?)";
	
	/**
	 * Inserts the record in the products sale 
	 */
	public void insert () throws PersistenceException {
		try (PreparedStatement statement = DataSource.INSTANCE.prepareGetGenKey(INSERT_PRODUCT_SALE_SQL)) {
			// set statement arguments
			statement.setInt(1, saleId);
			statement.setInt(2, productId);
			statement.setDouble(3, qty);		
			// execute SQL
			statement.executeUpdate();
			// Gets sale product Id generated automatically by the database engine
			try (ResultSet rs = statement.getGeneratedKeys()) {
				rs.next(); 
				id = rs.getInt(1);
			}
		} catch (SQLException e) {
			throw new PersistenceException ("Internal error inserting a new product in a sale!", e);
		}
	}
	
	/**
	 * Update sale product query
	 */
	private final static String UPDATE_PRODUCT_SALE_SQL = "update saleproduct "
			+ "set qty = ? "
			+ "where sale_id = ? and product_id = ?";
	
	/**
	 * Updates a sale product based on object details
	 * 
	 * @throws ApplicationException
	 */
	public void update () throws ApplicationException{
		
		try(PreparedStatement statement = DataSource.INSTANCE.prepare(UPDATE_PRODUCT_SALE_SQL)){
			
			statement.setDouble(1, this.qty);
			statement.setInt(2, this.saleId);
			statement.setInt(3, this.productId);
			if(statement.executeUpdate() == 1)
				System.out.println("SALE PRODUCT UPDATE!");
			
		} catch (SQLException | PersistenceException e) {
			throw new ApplicationException("Error when updating sale product row", e);
		} 
		
	} 
	
	
	/**
	 * The select the products of a sale by sale Id SQL statement
	 */
	private static final String GET_SALE_PRODUCTS_SQL = 
			"select id, sale_id, product_id, qty " +
				"from saleproduct " +
				"where sale_id = ?";
		
	/**
	 * Gets the products of a sale by its sale id 
	 * 
	 * @param saleId The sale id to get the products of
	 * @return The set of products that compose the sale
	 * @throws PersistenceException When there is an error obtaining the
	 *         information from the database.
	 */
	public static Set<SaleProductRowDataGateway> getSaleProducts (int saleId) throws PersistenceException {
		try (PreparedStatement statement = DataSource.INSTANCE.prepare(GET_SALE_PRODUCTS_SQL)) {
			// set statement arguments
			statement.setInt(1, saleId);			
			// execute SQL
			try (ResultSet rs = statement.executeQuery()) {
				// creates the sale's products set with the data retrieved from the database
				return loadSaleProducts(rs);
			}
		} catch (SQLException e) {
			throw new PersistenceException("Internal error getting the products of a sale", e);
		}
	}
	
	/**
	 * select to fetch a single sale product from database based of given info
	 */
	private static final String GET_SALE_PRODUCT_SQL = "select id, sale_id, product_id, qty "
			+ "from saleproduct "
			+ "where sale_id = ? and product_id = ?";
	
	/**
	 * Get a sale product from database based both on sale and product id
	 * 
	 * @param saleId, sale id to be considered
	 * @param productId, product id to be considered
	 * @return null if sale product doesnt exist, otherwise false
	 * @throws ApplicationException
	 */
	public static SaleProductRowDataGateway getSaleProductById(int saleId, int productId)
	throws RecordNotFoundException, ApplicationException{
		
		// obtain a sale product based both on sale and product id
		try(PreparedStatement statement = DataSource.INSTANCE.prepare(GET_SALE_PRODUCT_SQL)){
			
			statement.setInt(1, saleId);
			statement.setInt(2, productId);
			ResultSet rs = statement.executeQuery();
			
			// loads database info into a convenient object
			return loadSaleProduct(rs);

		} catch (PersistenceException | SQLException e) {
			throw new RecordNotFoundException("Sale Product does not exist", e);
		}
	}
	
	/**
	 * Creates the set of products of a sale from a result set retrieved from the database.
	 * 
	 * @param rs The result set with the information to create the set of products from a sale.
	 * @return The set of products of a sale loaded from the database.
	 * @throws SQLException When there is an error reading from the database.
	 */
	private static Set<SaleProductRowDataGateway> loadSaleProducts(ResultSet rs) throws SQLException {
		Set<SaleProductRowDataGateway> result = new HashSet<SaleProductRowDataGateway>();
		while (rs.next()) {
			SaleProductRowDataGateway newSaleProduct = new SaleProductRowDataGateway(rs.getInt("sale_id"), 
					rs.getInt("product_id"), rs.getDouble("qty"));
			newSaleProduct.id = rs.getInt("id");
			result.add(newSaleProduct);
		}
		return result;		
	}
	
	/**
	 * Creates a sale product of sale from a result set retrieved from database
	 * 
	 * @param rs, The result set with the information to create the set of products from a sale.
	 * @return a Sale Product retrieved from the database
	 * @throws SQLException
	 */
	private static SaleProductRowDataGateway loadSaleProduct(ResultSet rs) throws SQLException {
		rs.next();
		SaleProductRowDataGateway newSaleProduct = new SaleProductRowDataGateway(rs.getInt("sale_id"), 
				rs.getInt("product_id"), rs.getDouble("qty"));
		newSaleProduct.id = rs.getInt("id");
		return newSaleProduct;		
	}
}
