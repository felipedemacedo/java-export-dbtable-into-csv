package accenture.relacompexcel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBTable {
	private DBConnection db = null;
	private String schemaName = "";
	private String tableName = "";
	private Integer numRegistros = -1;
	private List<DBColumn> listCols = new ArrayList<DBColumn>();
	
	public DBTable(DBConnection db, String schemaName, String tableName) {
		setDb(db);
		setSchemaName(schemaName);
		setTableName(tableName);
		
		// PICK COLUMN NAMES
		try {
			setListCols();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("FAILED TO GET COLUMN NAMES FROM TABLE "+getTableNameWithSchema());
			e.printStackTrace();
		}
		
		// PICK NUMBER OF REGS
		try {
			setNumRegistros();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("FAILED TO GET NUMBER OF ENTRIES FROM TABLE "+getTableNameWithSchema());
			e.printStackTrace();
		}
	}

	public String getSchemaName() {
		return schemaName;
	}

	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
		System.out.println("SCHEMA NAME SET TO: " + getSchemaName());
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
		System.out.println("TABLE NAME SET TO: " + getTableName());
	}
	
	public String getTableNameWithSchema()
	{
		return getSchemaName() + "." + getTableName();
	}

	public List<DBColumn> getListCols() {
		return listCols;
	}

	public void setListCols() throws SQLException {
		String query = "SELECT column_name, data_type, data_length FROM USER_TAB_COLUMNS WHERE table_name = '"+getTableName()+"'";		
		System.out.println("-----\nEXECUTE QUERY: [" + query + "]");
	    Statement stmt = null;
	    try {
	        stmt = db.getConn().createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        while (rs.next()) {
	            String column_name = rs.getString("column_name");
	            String data_type = rs.getString("data_type");
	            String data_length = rs.getString("data_length");
	            DBColumn col = new DBColumn(column_name, data_type, data_length);
	            getListCols().add(col);
	            System.out.println(column_name + "\t" + data_type + "\t" + data_length);
	        }
	    } catch (SQLException e ) {
	        e.printStackTrace();
	    } finally {
	        if (stmt != null) { stmt.close(); }
	    }
	}

	public Integer getNumRegistros() {
		return numRegistros;
	}

	public void setNumRegistros() throws SQLException {
		String query = "SELECT count(*) AS NUM_REGS FROM "+getTableNameWithSchema();		
		System.out.println("-----\nEXECUTE QUERY: [" + query + "]");
	    Statement stmt = null;
	    try {
	        stmt = db.getConn().createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        while (rs.next()) {
	            String numRegs = rs.getString("NUM_REGS");
	            System.out.println("NUMBER OF ENTRIES IN "+getTableNameWithSchema()+": ["+numRegs+"]");
	        }
	    } catch (SQLException e ) {
	        e.printStackTrace();
	    } finally {
	        if (stmt != null) { stmt.close(); }
	    }
	}

	public DBConnection getDb() {
		return db;
	}

	public void setDb(DBConnection db) {
		this.db = db;
	}
}
