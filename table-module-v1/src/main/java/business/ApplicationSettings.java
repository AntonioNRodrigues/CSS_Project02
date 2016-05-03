package business;

import dataaccess.ConfigurationTableDataGateway;
import dataaccess.Persistence;
import dataaccess.PersistenceException;
import dataaccess.TableData;

/**
 * A table module for the application settings.
 * See remarks in the Customer class.
 * 
 * @author fmartins
 * @version 1.1 (5/10/2014)
 *
 */
public class ApplicationSettings extends TableModule {
		
	/**
	 * Constructs a ApplicationSettings module given the persistence repository
	 * 
	 * @param persistence The persistence repository
	 */
	public ApplicationSettings(Persistence persistence) {
		super(persistence);
	}

	/**
	 * @return The sale's amount threshold for being eligible for a
	 * total sale discount.
	 * @throws ApplicationException When there is no application settings data.
	 */
	public double getAmountThreshold () throws ApplicationException {
		return getDoubleFieldFromAppSettings (ConfigurationTableDataGateway.AMOUNT_THRESHOLD);
	}
	
	/**
	 * @return The discount percentage to apply for the total sale
	 * @throws ApplicationException When there is no application settings data.
	 */
	public double getAmountThresholdPercentage () throws ApplicationException {
		return getDoubleFieldFromAppSettings (ConfigurationTableDataGateway.TOTAL_AMOUNT_PERCENTAGE);
	}

	/**
	 * @return The discount percentage to apply to products marked as eligible.
	 * @throws ApplicationException When there is no application settings data.
	 */
	public double getEligiblePercentage () throws ApplicationException {
		return getDoubleFieldFromAppSettings (ConfigurationTableDataGateway.ELIGIBLE_PERCENTAGE);
	}
	
	/**
	 * @param field The field to get from the application settings
	 * @return The value of the field from the application settings
	 * @throws ApplicationException When there is no application settings data.
	 */
	private double getDoubleFieldFromAppSettings (String field) throws ApplicationException {
		try {
			TableData td = persistence.configurationTableGateway.getConfigurationSettings();
			if (!td.isEmpty()) 
				return td.iterator().next().getDouble(field);	
			else 
				throw new ApplicationException("Internal error accessing the application settings");
		} catch (PersistenceException e) {
			throw new ApplicationException("Internal error accessing the application settings", e);
		}
	}
}
