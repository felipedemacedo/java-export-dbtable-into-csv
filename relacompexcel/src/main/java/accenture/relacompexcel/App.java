package accenture.relacompexcel;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import accenture.relacompexcel.db.DBConnection;
import accenture.relacompexcel.db.DBTable;
import accenture.relacompexcel.threads.Escalonador;
import accenture.relacompexcel.timer.MyTimer;

public class App 
{
	//DATABASE CONNECTION
	private static final String endpoint = "";
	private static final String port = "1521";
	private static final String sid = "ORCL";
	private static final String user = "";
	private static final String pass = "";
	
	//DATABASE TABLE
	private static final String schema = "ADMIN";
	private static final String tableName = "NOME_TABELA_15000";
	
    public static void main( String[] args )
    {
    	MyTimer processTimer = new MyTimer();
    	
    	///CONNECTION TO DATABASE:
    	DBConnection db = new DBConnection(App.endpoint,App.port,App.sid,App.user,App.pass);
    	
    	processTimer.stop(" second(s) in CONNECTION TO DATABASE");
    	processTimer.start();
    	
    	///TABLE ANALYSIS
    	DBTable table = new DBTable(db,App.schema,App.tableName);
    	
    	processTimer.stop(" second(s) in DEFINING TABLE FOR ANALYSIS \n-----");
    	processTimer.start();
    	
    	Escalonador esc = new Escalonador();
    	esc.setNumTotalRegs(table.getNumRegistros());
    	try {
			esc.start(table);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	processTimer.stop(" second(s) in RUNNING THE THREADS ESCALONATOR");
    	processTimer.start();
    	
    	////CLOSE CONNECTION
    	//db.closeConnection();
    	//Runtime.getRuntime().runFinalization();
    	
    	//processTimer.stop(" second(s) TO CLOSE CONNECTION");
    }
}
