package accenture.relacompexcel.db;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
	private String endpoint;
	private String port;
	private String schema;
	private String user;
	private String pass;
	private Connection conn = null;
	
	public DBConnection(String endpoint, String port, String schema, String user, String pass) {
		setEndpoint(endpoint);
		setPort(port);
		setSchema(schema);
		setUser(user);
		setPass(pass);
		connect();
	}
	
	public void connect()
	{
    	try {
 		   Driver myDriver = new oracle.jdbc.driver.OracleDriver();
 		   DriverManager.registerDriver( myDriver );
 		   
 		   this.conn = DriverManager.getConnection
 				   ("jdbc:oracle:thin:@"+this.endpoint+":"+this.port+":"+this.schema+"", this.user, this.pass);
 		   System.out.println("CONNECTION OPEN !");
 		}
 		catch(SQLException ex) {
 		   System.out.println("Error: unable to connect !");
 		   System.exit(1);
 		}
	}
	
	public boolean isConnected()
	{
		try {
			return !this.conn.isClosed();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Returns true for connection closed successfully
	 * @return
	 */
	public boolean closeConnection()
	{
		try {
			this.conn.close();
			System.out.println("CONNECTION CLOSED!");
			return isConnected();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("FAILED TO CLOSE CONNECTION!");
			return false;
		}
	}
	
	/**
	 * Expects SELECT statement
	 * PRINTS the result into console
	 * @param query
	 * @throws SQLException
	 */
	/*public void viewQuery(String query) throws SQLException {
		System.out.println("EXECUTE QUERY: [" + query + "]");
	    Statement stmt = null;
	    try {
	        stmt = this.conn.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        while (rs.next()) {
	            String id = rs.getString("ID");
	            String col1 = rs.getString("COL1");
	            String col2 = rs.getString("COL2");
	            String col3 = rs.getString("COL3");
	            String col4 = rs.getString("COL4");
	            String col5 = rs.getString("COL5");
	            System.out.println(id + "\t" + col1 +
	                               "\t" + col2 + "\t" + col3 +
	                               "\t" + col4 + "\t" + col5);
	        }
	    } catch (SQLException e ) {
	        e.printStackTrace();
	    } finally {
	        if (stmt != null) { stmt.close(); }
	    }
	}*/

	//GETS AND SETS
	
	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public ResultSet query(String query) throws SQLException {
		System.out.println("EXECUTE QUERY: [" + query + "]");
	    Statement stmt = null;
	    try {
	        stmt = this.conn.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        return rs;
	    } catch (SQLException e ) {
	        e.printStackTrace();
	    } finally {
	        if (stmt != null) { stmt.close(); }
	    }
		return null;
	}
}
