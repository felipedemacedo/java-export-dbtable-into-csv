package accenture.relacompexcel;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	///CONNECTION TO DATABASE:
    	DBConnection db = new DBConnection("","1521","ORCL","","");
    	
    	///TABLE ANALYSIS
    	DBTable table = new DBTable("ADMIN","NOME_TABELA_30");
    	
    	try {
			db.viewQuery("SELECT * FROM "+table.getTableNameWithSchema());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	////CLOSE CONNECTION
    	db.closeConnection();
    }
}
