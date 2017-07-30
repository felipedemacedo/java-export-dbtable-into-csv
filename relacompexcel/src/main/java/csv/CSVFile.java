package csv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class CSVFile {
	public static final String DEFAULT_COLUMN_SEPARATOR = ";";
	public static final String DEFAULT_LINE_SEPARATOR = "\n";
	private String fileName = "";
	private String extension = "csv";
	private PrintWriter pw = null;
	private StringBuilder sb = null;
	
	public CSVFile(String fileName) throws FileNotFoundException {
		// TODO Auto-generated constructor stub
		setFileName(fileName);
		setPw(new PrintWriter(new File(getFileNameWithExtension())));
		setSb(new StringBuilder());
	}

	public PrintWriter getPw() {
		return pw;
	}

	public void setPw(PrintWriter pw) {
		this.pw = pw;
	}

	public StringBuilder getSb() {
		return sb;
	}

	public void setSb(StringBuilder sb) {
		this.sb = sb;
	}

	public String getFileName() {
		return fileName;
	}
	
	public String getFileNameWithExtension() {
		return getFileName() + "." + getExtension();
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void write() {
		// TODO Auto-generated method stub
		getPw().write(getSb().toString());
	}

	public void close() {
		// TODO Auto-generated method stub
		getPw().close();
	}
}