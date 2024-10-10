package logic;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import db.DatabaseConnection;

/**
 * @author Trudy Ann Roberts
 * 
 * The FilmSearcher class provides functionality to search for a film title in the database
 * using its film_id. It connects to the database and executes a SQL query to fetch the 
 * title of the film based on the provided film_id.
 */
public class FilmSearcher {

    /**
     * Retrieves the title of a film from the database based on the provided filmId.
     * 
     * This method establishes a connection to the database using the DatabaseConnection class, 
     * prepares a SQL query to find the film by its ID, and executes the query. If a matching 
     * film is found, the title is returned. Otherwise, null is returned.
     * 
     * @param filmId The ID of the film to search for.
     * @return The title of the film if found, or null if the film is not found or an error occurs.
     */
    public String getFilmTitleById(int filmId) {
        String query = "SELECT title FROM film WHERE film_id = ?";
        
        // Establish a connection to the database and execute the query
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, filmId);  // Set the filmId in the prepared statement
            ResultSet rs = pstmt.executeQuery();  // Execute the query
            
            if (rs.next()) {
                // Return the film title if a result is found
                return rs.getString("title");
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Handle any SQL exceptions
        }
        
        // Return null if the film was not found or an error occurred
        return null;
    }
}
