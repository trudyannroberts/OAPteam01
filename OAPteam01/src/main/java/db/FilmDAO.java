package db;

import film.Film;
import java.sql.*;

/**
 * 
 * @author Erica Laub Varpe
 */
public class FilmDAO {
    private static final String INSERT_FILM_SQL = 
        "INSERT INTO film (title, description, release_year, language_id, rental_duration, " +
        "rental_rate, length, replacement_cost, rating, special_features) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    private static final String GET_GENRE_ID_SQL = 
        "SELECT category_id FROM category WHERE name = ?";
    
    private static final String INSERT_FILM_CATEGORY_SQL = 
        "INSERT INTO film_category (film_id, category_id) VALUES (?, ?)";
    
    private Connection connection;
    
    public FilmDAO(Connection connection) {
        this.connection = connection;
    }
    
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
    
    public void insertFilmCategory(int filmId, int categoryId) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(INSERT_FILM_CATEGORY_SQL)) {
            stmt.setInt(1, filmId);
            stmt.setInt(2, categoryId);
            stmt.executeUpdate();
        }
    }
}
