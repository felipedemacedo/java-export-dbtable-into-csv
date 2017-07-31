package accenture.relacompexcel.threads;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import accenture.relacompexcel.db.DBTable;
import accenture.relacompexcel.timer.MyTimer;

public class Escalonador{
	private static final Double DEFAULT_DIVIDE_FACTOR = 0.25; // % of all regs
	public static final Integer DEFAULT_MAX_NUM_OF_CONSECUTIVE_THREADS = 4;
	public static Integer numOfThreadsRunning = 0;
	
	public static MyTimer initialTimer = new MyTimer();
	
	private Integer numTotalRegs = 0; //total of regs to be processed
	private Integer numRegsProcessed = 0;
	private Integer numRegsPerThread = 1; //defined by divide-factor
	private Integer minIndex = 0;

	/*private void process(DBTable table, Integer min, Integer max) throws FileNotFoundException, SQLException
	{
		System.out.print("Processing from "+min.toString()+" to "+max.toString()+" ... ");
		table.dumpIntoCSVFile("fileName["+min.toString()+"-"+max.toString()+"]", min, max);
		System.out.println("ok!");
	}*/
	
	public void start(DBTable table) throws FileNotFoundException, SQLException {
		if(getNumTotalRegs()<1) {
			System.out.println("NOTHING TO BE DONE HERE !");
			return;
		}
		
		while(getNumRegsProcessed() < getNumTotalRegs()) {
			Integer min = getMinIndex();
			
			Integer max = min + getNumRegsPerThread();
			if(max > getNumTotalRegs())
			{
				max=getNumTotalRegs();
			}
			
			//process(table,min,max);
			(new Thread(new WorkerThread(table,min,max))).start();
			
			setMinIndex(max+1);
			setNumRegsProcessed(getNumRegsProcessed() + (max-min+1));
		}
	}
	
	public Integer getNumTotalRegs() {
		return numTotalRegs;
	}

	public void setNumTotalRegs(Integer numTotalRegs) {
		this.numTotalRegs = numTotalRegs;
		setNumRegsPerThread((int)(this.numTotalRegs * Escalonador.DEFAULT_DIVIDE_FACTOR));
	}

	public Integer getNumRegsPerThread() {
		return numRegsPerThread;
	}

	public void setNumRegsPerThread(Integer numRegsPerThread) {
		this.numRegsPerThread = numRegsPerThread;
	}

	public Integer getNumRegsProcessed() {
		return numRegsProcessed;
	}

	public void setNumRegsProcessed(Integer numRegsProcessed) {
		this.numRegsProcessed = numRegsProcessed;
	}

	public Integer getMinIndex() {
		return minIndex;
	}

	public void setMinIndex(Integer minIndex) {
		this.minIndex = minIndex;
	}

}
