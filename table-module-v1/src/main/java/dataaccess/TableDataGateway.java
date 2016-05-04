package dataaccess;

import business.SaleStatus;

/**
 * Layer super type that stores information shared by all data gateway classes.
 * 
 * @author fmartins
 * @version 1.0 (1/10/2015)
 *
 */
public abstract class TableDataGateway {

	/**
	 * The data source
	 */
	DataSource dataSource;

	TableDataGateway(DataSource dataSource) {
		this.dataSource = dataSource;
	}
}
