package dataaccess;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TableData implements Iterable<TableData.Row> {

	private List<Row> data;
	 
	public static class Row {
		private Map<String, Object> record;
		
		public Row () {
			this.record = new HashMap<>();
		}
		
		public void update(String key, Object value) {
			record.put(key, value);
		}
		
		public String getString(String key) throws PersistenceException {
			try {
				return (String) record.get(key);
			} catch (ClassCastException e) {
				throw new PersistenceException("This value is not a string", e);
			}
		}

		public int getInt(String key) throws PersistenceException {
			try {
				return (int) record.get(key);
			} catch (ClassCastException e) {
				throw new PersistenceException("This value is not a int", e);
			}
		}

		public Date getDate(String key) throws PersistenceException {
			try {
				return (Date) record.get(key);
			} catch (ClassCastException e) {
				throw new PersistenceException("This value is not a data", e);
			}
		}

		public double getDouble(String key) throws PersistenceException {
			try {
				return (double) record.get(key);
			} catch (ClassCastException e) {
				throw new PersistenceException("This value is not a double", e);
			}
		}

	}
	
	public TableData populate(ResultSet rs) throws PersistenceException {
		try {
			data = new LinkedList<>();
			while(rs.next()) {
				ResultSetMetaData rsmd = rs.getMetaData();
				Row r = new Row();
				for (int i = 1; i <= rsmd.getColumnCount(); i++)  {
					String colName = rsmd.getColumnLabel(i);
					r.update(colName, rs.getObject(colName));
				}
				data.add(r);
			}	
			return this;
		} catch (SQLException e) {
			throw new PersistenceException("Error login result set into memory", e);
		} 
	}

	public boolean isEmpty() {
		return data.isEmpty();
	}
	
	@Override
	public Iterator<Row> iterator() {
		return data.iterator();
	}
		
}
