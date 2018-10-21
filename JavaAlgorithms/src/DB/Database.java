package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    public static String DERBY_DRIVER = "org.apache.derby.jdbc:EmbeddedDriver";
    public static String JDBC_DRIVER = "jdbc:derby:database;create=true";

    public static void main(String[] args) throws Exception {

	
//	Class.forName(DERBY_DRIVER);
	createDatabase("ESTUDIANTES", new DBField("nombre", SQLTypes.VARCHAR, "30"),
		new DBField("id", SQLTypes.VARCHAR, "30"), new DBField("edad", SQLTypes.NUMBER, "2"));

    }

    private static void createDatabase(String name, DBField... fields) {
	try {
	    Connection conn = getConnectionToDB();
	    Statement statement = conn.createStatement();
	    String createSql = "CREATE TABLE " + name + "(";
	    for (int i = 0; i < fields.length-1; i++) {
		createSql+=fields[i].toString()+",";
	    }
	    
	    createSql+=fields[fields.length-1].toString()+")";

	    statement.execute(createSql);
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    private static Connection getConnectionToDB() throws SQLException {
	return DriverManager.getConnection(JDBC_DRIVER);

    }

}
