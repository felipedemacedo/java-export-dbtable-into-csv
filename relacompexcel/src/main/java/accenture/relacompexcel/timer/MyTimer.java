package accenture.relacompexcel.timer;

/**
 * Class for time measures
 * @author Felipe
 *
 */
public class MyTimer {
	private long begin;
	private long end;
	private long elapsed;
	
	/**
	 * Starts when instantiated
	 */
	public MyTimer() {
		start();
	}
	
	public void start()
	{
		setBegin(System.nanoTime());
	}
	
	public void stop()
	{
		setEnd(System.nanoTime());
		setElapsed(getEnd() - getBegin());
		print();
	}
	
	public void stop(String message)
	{
		setEnd(System.nanoTime());
		setElapsed(getEnd() - getBegin());
		print(message);
	}
	
	public void print()
	{
		System.out.println((double) getElapsed() / 1000000000.0);
	}
	
	public void print(String message)
	{
		System.out.println((double) getElapsed() / 1000000000.0 + message);
	}

	public long getBegin() {
		return begin;
	}

	public void setBegin(long begin) {
		this.begin = begin;
	}

	public long getEnd() {
		return end;
	}

	public void setEnd(long end) {
		this.end = end;
	}

	public long getElapsed() {
		return elapsed;
	}

	public void setElapsed(long elapsed) {
		this.elapsed = elapsed;
	}
}
