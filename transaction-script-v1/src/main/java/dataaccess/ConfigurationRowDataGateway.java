package dataaccess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * An in-memory representation of a row gateway for a configuration row.
 * 
 * Here we illustrate another form of handling singleton objects. 
 * The object is created only when information is needed (lazy creation)
 * and the information is cached in final attributes, preventing future
 * accesses to the database. In this example we are assuming that 
 * there are no changes to the application parameters during its execution.
 * Should we need to handled this situation, then we would have to 
 * access the database in every read.   
 * See the discussion about thread safety in the DataSource class.
 * 
 * @author fmartins
 * @version 1.1 (19/02/2015)
 *
 */
public class ConfigurationRowDataGateway {
	
	private static ConfigurationRowDataGateway configurationRow;  

	// Configuration attributes 
	
	private final double totalAmountPercentage;
	private final double amountThreshold;
	private final double eligiblePercentage;
	

	// 1. constructor 
	// Since this class is a singleton, the constructor is private, so objects
	// cannot be created from outside the class.
	private ConfigurationRowDataGateway(double totalAmountPercentage, double amountThreshold,
			double eligiblePercentage) {
		this.totalAmountPercentage = totalAmountPercentage;
		this.amountThreshold = amountThreshold;
		this.eligiblePercentage = eligiblePercentage;
	}
	
	// 1. getters. There is no setters, since the application 
	// does not support the change of these attributes. 
	// (Of course, only an illustrative example).

	/**
	 * @return The discount percentage to apply for the total sale
	 */
	public double getAmountThresholdPercentage() {
		return totalAmountPercentage;
	}

	/**
	 * @return The sale's amount threshold for being eligible for a
	 * total sale discount.
	 */
	public double getAmountThreshold() {
		return amountThreshold;
	}

	/**
	 * @return The discount percentage to apply to products marked
	 * as eligible.
	 */
	public double getEligiblePercentage() {
		return eligiblePercentage;
	}

	
	// 2. interaction with the repository. In this case the application
	// does disallow parameter changes, so inserts and updates are not possible.
		
	private static final String GET_APP_CONFIG_SQL =
			"select id, totalAmountPercentage, amountThreshold, eligiblePercentage " +
				"from appconfig ";
	
	/**
	 * Gets the app configuration 
	 * 
	 * @return The new object that represents the in-memory app configuration
	 * @throws PersistenceException 
	 */
	public static ConfigurationRowDataGateway getConfiguration() throws PersistenceException {
		if (configurationRow == null)
			try (PreparedStatement comando = DataSource.INSTANCE.prepare(GET_APP_CONFIG_SQL)) {
				try (ResultSet rs = comando.executeQuery()) {
					rs.next();
					// creates the singleton configuration row 
					configurationRow = new ConfigurationRowDataGateway(rs.getDouble("totalAmountPercentage"),
							rs.getDouble("amountThreshold"), rs.getDouble("eligiblePercentage"));
				}
			} catch (SQLException e) {
				throw new PersistenceException("Internal error obtaning app configuration", e);
			}
		return configurationRow;
	}
}
