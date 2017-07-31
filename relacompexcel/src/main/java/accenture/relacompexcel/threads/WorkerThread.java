package accenture.relacompexcel.threads;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import accenture.relacompexcel.db.DBTable;
import accenture.relacompexcel.timer.MyTimer;

public class WorkerThread implements Runnable{
	private static Integer globalThreadId = 1;
	private MyTimer timer = new MyTimer();		
	private DBTable table;
	private Integer min;
	private Integer max;

	public WorkerThread(DBTable table, Integer min, Integer max) {
		setTable(table);
		setMin(min);
		setMax(max);
	}

	public void run() {
		Integer threadId = WorkerThread.globalThreadId;
		WorkerThread.globalThreadId++;
		
		while(Escalonador.numOfThreadsRunning >= Escalonador.DEFAULT_MAX_NUM_OF_CONSECUTIVE_THREADS)
		{
			try {
				System.out.println("[THREAD "+threadId.toString()+"] Waiting to process from "+this.min.toString()+" to "+this.max.toString()+" ... "+Escalonador.numOfThreadsRunning.toString()+" THREADS RUNNING.");
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		Escalonador.numOfThreadsRunning++;
		this.timer.start();
		System.out.println("[THREAD "+threadId.toString()+"] is Now Processing From "+getMin().toString()+" To "+getMax().toString()+" ...");
		
		try {
			table.dumpIntoCSVFile("fileName["+getMin().toString()+"-"+getMax().toString()+"]", getMin(), getMax());
			Escalonador.numOfThreadsRunning--;
			if(Escalonador.numOfThreadsRunning < 0)
			{
				Escalonador.numOfThreadsRunning = 0;
			}
			this.timer.stop(" second(s) in THREAD "+threadId.toString()+"] execution time. "+Escalonador.numOfThreadsRunning.toString()+" THREADS RUNNING.");
			Escalonador.initialTimer.stop(" second(s) in TOTAL EXECUTION TIME");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("ok!");
	}

	public DBTable getTable() {
		return table;
	}

	public void setTable(DBTable table) {
		this.table = table;
	}

	public Integer getMin() {
		return min;
	}

	public void setMin(Integer min) {
		this.min = min;
	}

	public Integer getMax() {
		return max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}
}
