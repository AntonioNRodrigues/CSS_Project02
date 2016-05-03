 package dataaccess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Table gateway for table configuration.
 * 
 * Please read the remarks in the CustomerTableGateway class
 * 
 * @author fmartins
 * @version 1.1 (5/10/2014)
 *
 */
public class ConfigurationTableDataGateway extends TableDataGateway {
	
	/**
	 * Columns' labels
	 */	
	public static final String AMOUNT_THRESHOLD = "AMOUNTTHRESHOLD";
	public static final String TOTAL_AMOUNT_PERCENTAGE = "TOTALAMOUNTPERCENTAGE";
	public static final String ELIGIBLE_PERCENTAGE = "ELIGIBLEPERCENTAGE";

	ConfigurationTableDataGateway(DataSource dataSource) {
		super(dataSource);
	}

	private static final String GET_APP_CONFIG_SQL = 
			"select * from appconfig";
	
	/**
	 * Gets configuration settings
	 * 
	 * @return The result set of the query
	 * @throws PersistenceException When there is an internal error accessing the database
	 */
	public TableData getConfigurationSettings () throws PersistenceException {
		try (PreparedStatement statement = dataSource.prepare(GET_APP_CONFIG_SQL);
				ResultSet rs = statement.executeQuery()) {
			return new TableData().populate(rs);
		} catch (SQLException e) {
			throw new PersistenceException("Internal error obtaning app configuration", e);
		}
	}
	
}
