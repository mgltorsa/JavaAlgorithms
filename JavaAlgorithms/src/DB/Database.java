package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Database {

    public static String JDBC_DRIVER = "jdbc:derby:database;create=true";

    public static void main(String[] args) {

	DBField nameField = new DBField("nombre", SQLTypes.VARCHAR, "30");
	DBField idField = new DBField("id", SQLTypes.VARCHAR, "30");
	DBField edadField = new DBField("edad", SQLTypes.INTEGER, null);
	try {
	    // CREAR UNA TABLA LLAMADA ESTUDIANTES CON LOS CAMPOS NOMBRE, ID, Y EDAD.
	    createTable("ESTUDIANTES", nameField, idField, edadField);
	} catch (SQLException e) {
	    System.out.println("La tabla estudiante no pudó ser creada, verifique que no exista");
	}

	try {
	    // AÑADIR EL ESTUDIANTE PEDRO CON ID 123 Y EDAD 19 años
	    InsertIn("ESTUDIANTES", "Pedro", "123", "19");
	    // AÑADIR EL ESTUDIANTE JUAN CON ID 223 Y EDAD 17 años
	    InsertIn("ESTUDIANTES", "Juan", "123", "19");

	} catch (SQLException e) {
	    e.printStackTrace();
	    System.out.println("No se logró añadir al estudiante");
	}

	// PARA TESTS
	Scanner sc = new Scanner(System.in);
	// FOR INSERT
	System.out.println("Ingrese en el siguiente formato: nombre:id:edad");
	for (String line = sc.nextLine(); line != null && !line.toLowerCase().equals("STOP"); line = sc.nextLine()) {

	    String[] info = line.split(":");
	    try {
		InsertIn("ESTUDIANTES", info[0], info[1], info[2]);
		System.out.println("Se agregó el estudiante");
	    } catch (Exception e) {
		System.out.println("No se logró añadir al estudiante " + info[0]);
	    }
	}
	System.out.println(
		"para buscar solo ingrese un id de algún estudiante y la información sobre el aparecera, para ver todos los registros"
			+ " ingrese lo siguiente: abcd");
	// FOR QUERY (SEARCH ANY STUDENT)
	for (String line = sc.nextLine(); line != null && !line.toLowerCase().equals("STOP"); line = sc.nextLine()) {
	    try {
		if (line.toLowerCase().equals("abcd")) {
		    selectAll("ESTUDIANTES");
		} else {
		    query("ESTUDIANTES", line);
		}
	    } catch (SQLException e) {

	    }
	}
	sc.close();

    }

    private static void query(String dbName, String idQuery) throws SQLException {

	Connection conn = getConnectionToDB();
	Statement statement = conn.createStatement();
	String querySql = "SELECT * FROM " + dbName + " WHERE " + " id=" + idQuery;
	ResultSet result = statement.executeQuery(querySql);
	System.out.println("Imprimiendo resultados consulta");

	while (result.next()) {
	    String name = result.getString(1);
	    String id = result.getString(2);
	    String edad = result.getString(3);
	    System.out.println(name + " " + id + " " + edad);
	}
    }

    private static void selectAll(String dbName) throws SQLException {
	Connection conn = getConnectionToDB();
	Statement statement = conn.createStatement();
	String allSql = "SELECT * FROM " + dbName;
	ResultSet result = statement.executeQuery(allSql);
	System.out.println("Imprimiendo todos los estudiantes en la tabla");
	while (result.next()) {
	    String name = result.getString(1);
	    String id = result.getString(2);
	    String edad = result.getString(3);
	    System.out.println(name + " " + id + " " + edad);
	}

    }

    private static void InsertIn(String dbName, String... values) throws SQLException {
	Connection conn = getConnectionToDB();
	Statement statement = conn.createStatement();
	String insertSql = "INSERT INTO " + dbName + " VALUES (";
	for (int i = 0; i < values.length - 1; i++) {
	    insertSql += "'"+values[i]+"'" + ",";
	}

	insertSql += values[values.length - 1] + ")";

	statement.execute(insertSql);

    }

    private static void createTable(String dbName, DBField... fields) throws SQLException {

	Connection conn = getConnectionToDB();
	Statement statement = conn.createStatement();
	String createSql = "CREATE TABLE " + dbName + " ( ";
	for (int i = 0; i < fields.length - 1; i++) {
	    createSql += fields[i].toString() + ", ";
	}

	createSql += fields[fields.length - 1].toString() + " )";
	

	statement.execute(createSql);

    }

    private static Connection getConnectionToDB() throws SQLException {
	return DriverManager.getConnection(JDBC_DRIVER);

    }

}
