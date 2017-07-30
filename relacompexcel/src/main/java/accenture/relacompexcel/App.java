package accenture.relacompexcel;

import java.io.FileNotFoundException;
import java.sql.SQLException;

public class App 
{
    public static void main( String[] args )
    {
    	long totalBefore = System.nanoTime();
    	long before = System.nanoTime();
    	
    	///CONNECTION TO DATABASE:
    	DBConnection db = new DBConnection("","1521","ORCL","","");
    	
    	long elapsedTime = System.nanoTime() - before;
    	System.out.println((double)elapsedTime / 1000000000.0 + " seconds in CONNECTION TO DATABASE");

    	
    	before = System.nanoTime();
    	///TABLE ANALYSIS
    	DBTable table = new DBTable(db,"ADMIN","NOME_TABELA_30");
    	elapsedTime = System.nanoTime() - before;
    	System.out.println((double)elapsedTime / 1000000000.0 + " seconds in DEFINING TABLE FOR ANALYSIS");

    	
    	//DUMP IT !
    	try {
    		before = System.nanoTime();
			table.dumpIntoCSVFile("fileName",0,27);
	    	elapsedTime = System.nanoTime() - before;
	    	System.out.println((double)elapsedTime / 1000000000.0 + " seconds in DUMP TABLE INTO CSV FILE");

		} catch (FileNotFoundException e) {
			System.out.println("ARQUIVO fileName.csv EM USO !");
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	////CLOSE CONNECTION
    	before = System.nanoTime();
    	db.closeConnection();
    	elapsedTime = System.nanoTime() - before;
    	System.out.println((double)elapsedTime / 1000000000.0 + " seconds in CLOSE CONNECTION");

    	Runtime.getRuntime().runFinalization();
    	
    	long totalElapsedTime = System.nanoTime() - totalBefore;
    	System.out.println((double)totalElapsedTime / 1000000000.0 + " seconds in TOTAL EXECUTION TIME");

    }
}
