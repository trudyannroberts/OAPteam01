package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 * @author Trudy Ann Roberts
 */
public class JDBC {
	  /**
	   * @param dbUrl hold the link to the local database
	   * @param user is the user name of the database
	   * @param pass is the password to get into the database
	   */
    static String dbUrl = "jdbc:mysql://localhost:3306/sakila";
    static String user = "root";
    static String pass = "Disneylanderkjempegoy86";

    public static void main(String[] args) {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(dbUrl, user, pass);
            System.out.println("Connection established!");

            Statement stmt = conn.createStatement();
            String sql = "SELECT film_id, title FROM film";
            ResultSet rs = stmt.executeQuery("select * from film");

            while (rs.next()) {
                int id = rs.getInt("film_id");
                String title = rs.getString("title");
                System.out.println("ID: " + id + ", Name: " + title);
            }

            rs.close();
            stmt.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}


