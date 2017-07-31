package accenture.relacompexcel.db;

import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import accenture.relacompexcel.csv.CSVFile;

public class DBTable {
	private DBConnection db = null;
	private String schemaName = "";
	private String tableName = "";
	private Integer numRegistros = -1;
	private List<DBTableColumn> listCols = new ArrayList<DBTableColumn>();
	private String columnNamesList = ""; 
	
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
		
		// write column names list separated by CSV default separator
		for (DBTableColumn dbColumn : listCols) {
			this.columnNamesList += dbColumn.getColumnName();
			if(dbColumn.isLastColumn())
			{
				this.columnNamesList += CSVFile.DEFAULT_LINE_SEPARATOR;
			}
			else
			{
				this.columnNamesList += CSVFile.DEFAULT_COLUMN_SEPARATOR;
			}
				
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

	public List<DBTableColumn> getListCols() {
		return listCols;
	}

	public void setListCols() throws SQLException {
		String query = "SELECT column_name, data_type, data_length FROM USER_TAB_COLUMNS WHERE table_name = '"+getTableName()+"'";		
		System.out.println("-----\nEXECUTE QUERY: [" + query + "]");
	    Statement stmt = null;
	    try {
	        stmt = db.getConn().createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        DBTableColumn col = null;
	        while (rs.next()) {
	            String column_name = rs.getString("column_name");
	            String data_type = rs.getString("data_type");
	            String data_length = rs.getString("data_length");
	            col = new DBTableColumn(column_name, data_type, data_length);
	            getListCols().add(col);
	            System.out.println(column_name + "\t" + data_type + "\t" + data_length);
	        }
	        col.setLastColumn(true);
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
	            this.numRegistros = Integer.parseInt(numRegs);
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

	public void dumpIntoCSVFile(String fileName) throws FileNotFoundException, SQLException {
		CSVFile file = new CSVFile(fileName);
		
        ///query///
		String query = "SELECT * FROM "+getTableNameWithSchema();		
		System.out.println("-----\nEXECUTE QUERY: [" + query + "]");
	    Statement stmt = null;
	    try {
	        stmt = db.getConn().createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        
	        //write header:
	        file.getSb().append(this.columnNamesList);
	        
	        while (rs.next()) {
	        	for (DBTableColumn dbColumn : listCols) {
					//System.out.println(rs.getString(dbColumn.getColumnName()));
	        		String colName = dbColumn.getColumnName();
	        		String colValue = rs.getString(colName);
	        		
	        		file.getSb().append(colValue);
	        		
	        		if(dbColumn.isLastColumn()) {
	        			file.getSb().append(CSVFile.DEFAULT_LINE_SEPARATOR);
	        		}else
	        		{
	        			file.getSb().append(CSVFile.DEFAULT_COLUMN_SEPARATOR);
	        		}
				}
	        }
	    } catch (SQLException e ) {
	        e.printStackTrace();
	    } finally {
	        if (stmt != null) { stmt.close(); }
	    }

        file.write();
        file.close();
        
        System.out.println("done!");
	}
	
	public void dumpIntoCSVFile(String fileName, Integer rowNumMin, Integer rowNumMax) throws FileNotFoundException, SQLException
	{
		CSVFile file = new CSVFile(fileName);
		
        ///query///
		String query = 	"SELECT "+this.columnNamesList.replace(CSVFile.DEFAULT_COLUMN_SEPARATOR, ",") + 
						" FROM " + 
						"( " + 
						"    SELECT a.*, rownum as rn " + 
						"    FROM "+getTableNameWithSchema()+" a " + 
						"    WHERE rownum <= " + rowNumMax.toString() + 
						") " + 
						"WHERE rn >= " + rowNumMin.toString();
		
		System.out.println("-----\nEXECUTE QUERY: [" + query + "]");
	    Statement stmt = null;
	    try {
	        stmt = db.getConn().createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        
	        //write header:
	        file.getSb().append(this.columnNamesList);
	        
	        while (rs.next()) {
	        	for (DBTableColumn dbColumn : listCols) {
					//System.out.println(rs.getString(dbColumn.getColumnName()));
	        		String colName = dbColumn.getColumnName();
	        		String colValue = rs.getString(colName);
	        		
	        		file.getSb().append(colValue);
	        		
	        		if(dbColumn.isLastColumn()) {
	        			file.getSb().append(CSVFile.DEFAULT_LINE_SEPARATOR);
	        		}else
	        		{
	        			file.getSb().append(CSVFile.DEFAULT_COLUMN_SEPARATOR);
	        		}
				}
	        }
	    } catch (SQLException e ) {
	        e.printStackTrace();
	    } finally {
	        if (stmt != null) { stmt.close(); }
	    }

        file.write();
        file.close();
        
        System.out.println("done!");
	}
}
