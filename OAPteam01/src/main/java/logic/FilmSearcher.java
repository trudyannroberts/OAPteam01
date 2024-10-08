package logic;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import db.DatabaseConnection;
/**
 * @author Trudy Ann Roberts
 * The method getFilmTitleById() connects with the database and fetches the film title based in the film_id.
 * @return the title of the film.
 */

public class FilmSearcher {

    public String getFilmTitleById(int filmId) {
        String query = "SELECT title FROM film WHERE film_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, filmId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("title");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}