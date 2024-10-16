package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * @author Trudy Ann Roberts
 * This class will connect to the database. The idea is that other classes can import this class and then connect to the database through here.
 */
public class DatabaseConnection {
	/**
	 * @param DB_URL is the url for the local database. This url can be used by all team members.
	 * @param DB_USER shows the username. No need to change this. I think we are all "root"
	 * @param DB_PASSWORD is out individual passwords to the database. Here you need to enter the password for your workbench Local Instance.
	 */
    private static final String DB_URL = "jdbc:mysql://localhost:3306/sakila";
    private static final String DB_USER = "root"; 
    private static final String DB_PASSWORD = "Disneylanderkjempegoy86"; 
    /**
     *  The Connection method is public so that we can access it from other classes.
     *  It can be called directly using the class name. It connects with the database through an API (JDBC). 
     *  With Connection we can create objects like Statement, PreparedStatement, and CallableStatement in order to do SQL queries.
     * @return connection to database.
     * @throws SQLException means that any calling code must handle the exception.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}
