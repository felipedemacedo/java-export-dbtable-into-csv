package accenture.relacompexcel;

public class DBColumn {
	private String columnName = "";
	private String dataType = "";
	private String dataLength = "";
	
	public DBColumn(String columnName, String dataType, String dataLength) {
		setColumnName(columnName);
		setDataType(dataType);
		setDataLength(dataLength);
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getDataLength() {
		return dataLength;
	}

	public void setDataLength(String dataLength) {
		this.dataLength = dataLength;
	}
	
	
			
}
