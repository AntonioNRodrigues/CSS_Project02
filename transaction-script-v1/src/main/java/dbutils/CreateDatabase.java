package dbutils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import dataaccess.DataSource;
import dataaccess.PersistenceException;

public class CreateDatabase {

	public void createCSSDerbyDB() throws FileNotFoundException, IOException, SQLException, PersistenceException {
		DataSource.INSTANCE.connect("jdbc:derby:data/derby/cssdb;create=true", "SaleSys", "");
		RunSQLScript.runScript(DataSource.INSTANCE.getConnection(), "data/scripts/createDDL-Derby.sql");
		DataSource.INSTANCE.close();		
	}
	
	public static void main(String[] args) throws PersistenceException, FileNotFoundException, IOException, SQLException {
		new CreateDatabase().createCSSDerbyDB();
	}

}
