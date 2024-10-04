package logic;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class TitlesListDB {
	 static String dbUrl = "jdbc:mysql://localhost:3306/sakila";
	 static String user = "root";
	 static String pass = "Disneylanderkjempegoy86";
	 private List<Review> reviews;
	public static void main(String[] args) {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(dbUrl, user, pass);
            System.out.println("Connection established!");

            Statement stmt = conn.createStatement();
            String sql = "SELECT title FROM film";
            ResultSet rs = stmt.executeQuery("select * from film");

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


	

	    //Fetch all film titles from the database
	    public List<String>s getFilmTitle() throws SQLException {
	        List<String> filmTitles = new ArrayList<>();
	        String query = "SELECT title FROM film";  // Replace 'films' with your actual table name

	        try (Statement statement = conn.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {
	            while (resultSet.next()) {
	                filmTitles.add(resultSet.getString("title"));
	            }
	        }
	        return filmTitles;
	    }

	    // Close the database connection
	    public void closeConnection() throws SQLException {
	        if (conn != null) {
	            conn.close();
	        }
	    }
	}*/

