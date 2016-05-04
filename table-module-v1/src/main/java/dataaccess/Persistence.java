package dataaccess;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import dbutils.RunSQLScript;


public class Persistence implements AutoCloseable {

	private DataSource dataSource;
	public final ConfigurationTableDataGateway configurationTableGateway;
	public final CustomerTableDataGateway customerTableGateway;
	public final ProductTableDataGateway productTableGateway;
	public final SaleTableDataGateway saleTableGateway;
	public final SaleProductTableDataGateway saleProductTableGateway;
	public final TransactionTableDataGateway transactionTableGateway;

	public Persistence(String url, String username, String password) throws PersistenceException {
		dataSource = new DataSource();
		dataSource.connect(url, username, password);
		configurationTableGateway = new ConfigurationTableDataGateway (dataSource);
		customerTableGateway = new CustomerTableDataGateway (dataSource);
		productTableGateway = new ProductTableDataGateway(dataSource);
		saleTableGateway = new SaleTableDataGateway(dataSource);
		saleProductTableGateway = new SaleProductTableDataGateway(dataSource);
		transactionTableGateway = new TransactionTableDataGateway(dataSource);
	}

	@Override
	public void close() throws PersistenceException {
		dataSource.close();
	}

	public void runSQLScript(String filename) throws FileNotFoundException, IOException, SQLException {
		RunSQLScript.runScript(dataSource.getConnection(), filename);
	}
	
	public void beginTransaction() throws PersistenceException {
		dataSource.beginTransaction();
	}
	
	public void commit() throws PersistenceException {
		dataSource.commit();
	}
	
	public void rollback() throws PersistenceException {
		dataSource.rollback();
	}
}
