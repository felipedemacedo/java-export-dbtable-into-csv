package accenture.relacompexcel;

public class DBTableColumn {
	private String columnName = "";
	private String dataType = "";
	private String dataLength = "";
	private Boolean lastColumn = false;
	
	public DBTableColumn(String columnName, String dataType, String dataLength) {
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

	public Boolean isLastColumn() {
		return lastColumn;
	}

	public void setLastColumn(Boolean lastColumn) {
		this.lastColumn = lastColumn;
	}		
}
