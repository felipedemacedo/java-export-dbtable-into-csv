package accenture.relacompexcel;

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
    	DBConnection db = new DBConnection("acompexceldb.cranwur5otqa.sa-east-1.rds.amazonaws.com","1521","ORCL","admin","admin123");
    	
    	///TABLE ANALYSIS
    	DBTable table = new DBTable(db,"ADMIN","NOME_TABELA_30");
    	
    	/*try {
			db.viewQuery("SELECT * FROM "+table.getTableNameWithSchema());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
    	
    	
    	
    	////CLOSE CONNECTION
    	db.closeConnection();
    }
}
