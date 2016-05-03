package dbutils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import dataaccess.Persistence;
import dataaccess.PersistenceException;

public class CreateDatabase {

	public void createCSSDerby() throws FileNotFoundException, IOException, SQLException, PersistenceException {
		try (Persistence persistence = new dataaccess.Persistence("jdbc:derby:data/derby/cssdb;create=true", "SaleSys", "")) {
			persistence.runSQLScript("data/scripts/createDDL-Derby.sql");
		}
	}
	
	public static void main(String[] args) throws PersistenceException, FileNotFoundException, IOException, SQLException {
		new CreateDatabase().createCSSDerby();
	}

}
