package dataaccess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * An in-memory representation of a product table record. 
 *	
 * Notes:
 * 1. See the notes for the CustomerRowGateway class
 * 
 * @author fmartins
 * @Version 1.2 (13/02/2015)
 *
 */
public class ProductRowDataGateway {

	// Product attributes 

	/**
	 * Product id (unique, primary key)
	 */
	private int id;
	
	/**
	 * The product code. This code is the one present in the product that customers have access to.
	 * It is different from the id, which is an internal identity, used for relations with other 
	 * entities (e.g., Sale)
	 */	
	private int prodCod;
	
	/**
	 * Product's description 
	 */
	private String description;
	
	/**
	 * Product's face value
	 */
	private double faceValue;
	
	/**
	 * Product's stock quantity
	 */
	private double qty;

	/**
	 * If the product is eligible for a discount
	 */
	private String discountEligibility;
	
	/**
	 * Product's units
	 */
	private int unitId;
	
	/**
	 * Constants for mapping eligible discount to a string
	 */
	private static final String ELIGIBLE = "E";
	private static final String NOT_ELIGIBLE = "N";

	
	// 1. getters and setters

	/**
	 * Comment: the product id is an internal (persistence) concept only used
	 * to link entities and must not have any business meaning. Also, there
	 * is no setProductId, since the id does not change through the execution
	 * of the program.
	 * 
	 * @return The product's id
	 */
	public int getProductId() {
		return id;
	}
	
	/**
	 * Comment: there is a business rule to not allow product code changes.
	 * That is why there is no method for updating the product code.
	 * 
	 * @return The code of the product.
	 */
	public int getProdCod() {
		return prodCod;
	}

	/**
	 * @return The product's description.
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Updates the product's description.
	 * 
	 * @param description The new description for the product 
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return The product's face value
	 */
	public double getFaceValue() {
		return faceValue;
	}

	/**
	 * Updates the product's face value
	 * 
	 * @param faceValue The new face value for the product
	 */
	public void setFaceValue(double faceValue) {
		this.faceValue = faceValue;
	}

	/**
	 * @return The product's quantity
	 */
	public double getQty() {
		return qty;
	}

	/**
	 * Updates the product's stock quantity
	 * 
	 * @param qty The new stock quantity
	 */
	public void setQty(double qty) {
		this.qty = qty;
	}


	/**
	 * Notice that the setter and the getter convert the string to a 
	 * boolean value that is then used in the application's domain logic.
	 * 
	 * @return whether the product is eligible for discount
	 */
	public boolean isEligibleForDiscount() {
		return discountEligibility == ELIGIBLE;
	}

	/**
	 * Updates the eligibility condition of a product.
	 * 
	 * @param eligibleForDiscount The new eligibility condition for the product.
	 */
	public void setEligibleForDiscount(boolean eligibleForDiscount) {
		this.discountEligibility = eligibleForDiscount ? ELIGIBLE : NOT_ELIGIBLE;
	}

	/**
	 * @return The units id of the product
	 */
	public int getUnitId() {
		return unitId;
	}

	/**
	 * Updates the units id of the product.
	 * 
	 * @param unitId The new units id of the product.
	 */
	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}

	
	// 2. interaction with the repository (a database in this simple example)
	
	// Since this first version of the application does not add new products, 
	// there is no insert method.	
	
	/**
	 * The select a product by product code SQL statement
	 */
	private static final String GET_PRODUCT_BY_PROD_COD_SQL = 
		    "select id, prodcod, description, facevalue, qty, discounteligibility, unit_id " +
				"from product " +
				"where prodcod = ?";
		
	/**
	 * Gets a product given its codProd 
	 * 
	 * @param prodCod The codProd of the product to search for
	 * @return The in-memory representation of the product
	 * @throws PersistenceException 
	 */
	public static ProductRowDataGateway getProductByProdCod (int prodCod) throws PersistenceException {
		try (PreparedStatement Statement = DataSource.INSTANCE.prepare(GET_PRODUCT_BY_PROD_COD_SQL)) {
			// set statement arguments
			Statement.setInt(1, prodCod);
			// execute SQL
			try (ResultSet rs = Statement.executeQuery()) {
				// creates a new product with the data retrieved from the database
				return loadProduct(rs);	
			}
		} catch (SQLException e) {
			throw new PersistenceException("Internal error getting product with id " + prodCod, e);
		}
	}
	
	/**
	 * The select a product by Id SQL statement
	 */
	private static final String GET_PRODUCT_BY_ID_SQL = 
		    "select id, prodcod, description, facevalue, qty, discounteligibility, unit_id " +
				"from product " +
				"where id = ?";
	
	/**
	 * Gets a product given its product id 
	 * 
	 * @param id The id of the product to search for
	 * @return The in-memory representation of the product
	 * @throws PersistenceException In case there is an error accessing the database.
	 */
	public ProductRowDataGateway getProductById(int id) throws PersistenceException {
		try (PreparedStatement statement = DataSource.INSTANCE.prepare(GET_PRODUCT_BY_ID_SQL)) {
			// set statement arguments
			statement.setInt(1, id);
			// execute SQL
			try (ResultSet rs = statement.executeQuery()) {
				// creates a new product with the data retrieved from the database
				return loadProduct(rs);
			}
		} catch (SQLException e) {
			throw new PersistenceException("Internal error getting product with id " + id, e);
		}
	}

	
	/**
	 * The update product stock SQL statement
	 */
	private static final String	UPDATE_STOCK_SQL =
			"update product " +
					   "set qty = ? " +
					   "where id = ?";
	
	/**
	 * Updates the product quantity
	 * 
	 * @throws PersistenceException
	 */
	public void updateStockValue () throws PersistenceException {
		try (PreparedStatement statement = DataSource.INSTANCE.prepare(UPDATE_STOCK_SQL)){
			// set statement arguments
			statement.setDouble(1, qty);
			statement.setInt(2, id);
			// execute SQL
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new PersistenceException("Internal error updating product " + id + " stock amount.", e);
		}
	}
	
	/**
	 * Creates a product from a result set retrieved from the database.
	 * 
	 * @param rs The result set with the information to create the product.
	 * @return A new product loaded from the database.
	 * @throws RecordNotFoundException In case the result set is empty.
	 */
	private static ProductRowDataGateway loadProduct(ResultSet rs) throws RecordNotFoundException {
		try {
			rs.next();
			ProductRowDataGateway newProduct = new ProductRowDataGateway();
			newProduct.id = rs.getInt("id");
			newProduct.prodCod = rs.getInt("prodcod");
			newProduct.description = rs.getString("description");
			newProduct.faceValue = rs.getDouble("facevalue");
			newProduct.qty = rs.getDouble("qty");
			newProduct.discountEligibility = rs.getString("discounteligibility");
			newProduct.unitId = rs.getInt("unit_id");
			return newProduct;
		} catch (SQLException e) {
			throw new RecordNotFoundException ("Product not found", e);
		}
	}
}
