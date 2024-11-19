package db;

import film.Film;
import java.sql.*;

/**
 * Data Access Object for handling film import database operations.
 * Provides methods for inserting films and managing film categories in the database.
 * 
 * @author Erica Laub Varpe
 */
public class FilmDAO {
	
	/** SQL query for inserting a new film */
    private static final String INSERT_FILM_SQL = 
        "INSERT INTO film (title, description, release_year, language_id, rental_duration, " +
        "rental_rate, length, replacement_cost, rating, special_features) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    /** SQL query for retrieving a genre ID by name */
    private static final String GET_GENRE_ID_SQL = 
        "SELECT category_id FROM category WHERE name = ?";
    
    /** SQL query for linking a film to a category */
    private static final String INSERT_FILM_CATEGORY_SQL = 
        "INSERT INTO film_category (film_id, category_id) VALUES (?, ?)";
    
    /** Database connection used by this DAO */
    private Connection connection;
    
    /**
     * Creates a new FilmDAO with the specified database connection.
     * 
     * @param connection The database connection to use for operations
     */
    public FilmDAO(Connection connection) {
        this.connection = connection;
    }
    
    /**
     * Inserts a new film into the database with default values for some fields.
     * 
     * @param film The film to insert
     * @return The generated film ID
     * @throws SQLException If there is an error executing the insert
     */
    public int insertFilm(Film film) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(INSERT_FILM_SQL, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, film.getTitle());
            stmt.setString(2, film.getDescription());
            stmt.setInt(3, film.getReleaseYear());
            stmt.setInt(4, 1); // default language_id
            stmt.setInt(5, 3); // default rental_duration
            stmt.setDouble(6, 4.99); // default rental_rate
            stmt.setInt(7, 120); // default length
            stmt.setDouble(8, 19.99); // default replacement_cost
            stmt.setString(9, "PG"); // default rating
            stmt.setString(10, "Trailers"); // default special_features
            
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                throw new SQLException("Failed to get generated film ID");
            }
        }
    }
    
    /**
     * Retrieves the category ID for a given genre name.
     * 
     * @param genreName The name of the genre to look up
     * @return The category ID associated with the genre
     * @throws SQLException If the genre is not found or there is a database error
     */
    public int getGenreId(String genreName) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(GET_GENRE_ID_SQL)) {
            stmt.setString(1, genreName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("category_id");
                }
                throw new SQLException("Genre not found: " + genreName);
            }
        }
    }
    
    /**
     * Associates a film with a category in the database.
     * 
     * @param filmId The ID of the film
     * @param categoryId The ID of the category
     * @throws SQLException If there is an error creating the association
     */
    public void insertFilmCategory(int filmId, int categoryId) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(INSERT_FILM_CATEGORY_SQL)) {
            stmt.setInt(1, filmId);
            stmt.setInt(2, categoryId);
            stmt.executeUpdate();
        }
    }
}
