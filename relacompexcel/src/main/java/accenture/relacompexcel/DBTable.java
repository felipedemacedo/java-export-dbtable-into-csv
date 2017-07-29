package accenture.relacompexcel;

public class DBTable {
	private String tableName = "";
	private String schemaName = "";
	
	public DBTable(String schemaName, String tableName) {
		setTableName(tableName);
		setSchemaName(schemaName);
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
		System.out.println("TABLE NAME SET TO: " + getTableName());
	}

	public String getSchemaName() {
		return schemaName;
	}

	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
		System.out.println("SCHEMA NAME SET TO: " + getSchemaName());
	}
	
	public String getTableNameWithSchema()
	{
		return getSchemaName() + "." + getTableName();
	}
	
}
