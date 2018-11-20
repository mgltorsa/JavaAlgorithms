package DB;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class DatabaseManager {

    public static String JDBC_DRIVER = "jdbc:derby:";

    private HashMap<String, Table> tables;
    private Connection conn;

    public DatabaseManager() {
	tables = new HashMap<>();
    }

    public void setConnection(String dbname, boolean create) throws SQLException {
	String dbConnection = JDBC_DRIVER + dbname + ";create=" + create;
	Connection conn = getConnectionToDB(dbConnection);
	this.conn = conn;
    }

    public Connection getConnectionToDB(String dbConnection) throws SQLException {
	return DriverManager.getConnection(dbConnection);

    }

    public void createTable(String name, PrimaryKey[] keys, ForeignKey[] foreigns, DBField... fields)
	    throws IllegalArgumentException, SQLException {
	if (conn == null) {
	    throw new IllegalArgumentException("Connection was null");
	}

	String createSql = getSqlCreateCommand(name, fields);
	Statement st = conn.createStatement();
	st.execute(createSql);
	Table table = new Table(name, keys, foreigns,fields);
    }

    private String getSqlCreateCommand(String name, DBField[] fields) {
	String createSql = "CREATE TABLE " + name + " ( ";

	for (int i = 0; i < fields.length - 1; i++) {

	    createSql += fields[i].toString() + ", ";

	}
	createSql += fields[fields.length - 1].toString() + ")";

	return createSql;
    }
}
