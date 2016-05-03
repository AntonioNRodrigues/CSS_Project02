package business;

import dataaccess.Persistence;

public class TableModule {

	protected Persistence persistence;
	
	public TableModule (Persistence persistence) {
		this.persistence = persistence;
	}

}
