package dbutils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import dataaccess.Persistence;
import dataaccess.PersistenceException;

public class ResetTables {

	public void resetCSSDerbyDB() throws FileNotFoundException, IOException, SQLException, PersistenceException {
		try (Persistence persistence = new dataaccess.Persistence("jdbc:derby:data/derby/cssdb;create=false", "SaleSys", "")) {
			persistence.runSQLScript("data/scripts/resetTables-Derby.sql");
		}
	}
	
	public static void main(String[] args) throws PersistenceException, FileNotFoundException, IOException, SQLException {
		new ResetTables().resetCSSDerbyDB();
	}

}
