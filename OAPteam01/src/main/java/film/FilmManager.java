package film;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

import db.DatabaseConnection;
import gui.BrowseMoviesPage;

/**
 * The FilmManager class manages film data retrieval from the database.
 * It implements the FilmHandler interface and follows the DAO pattern 
 * to access film related information.
 *
 * <p>This class supports:
 * <ul>
 *   <li>Retrieving all films</li>
 *   <li>Searching films by title, genre, or release year</li>
 *   <li>Updating the film display in the GUI</li>
 * </ul>
 *
 * <p>Internal helper methods are used for database mapping and query execution.
 * 
 * <p><b>Note:</b> Database errors are logged with stack traces.
 * 
 * @author Erica Laub Varpe
 */
public class FilmManager implements FilmHandler {

    // Helper method to map ResultSet to a Film object
    private Film mapResultSetToFilm(ResultSet resultSet) throws SQLException {
        String title = resultSet.getString("title");
        String desc = resultSet.getString("description");
        int releaseYear = resultSet.getInt("release_year");
        String genre = resultSet.getString("category");
        return new Film(title, desc, releaseYear, genre);
    }

    // General method to fetch films based on a query and a parameter
    private List<Film> getFilms(String sql, Object param) {
        List<Film> films = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            if (param instanceof String) {
                statement.setString(1, (String) param);
            } else if (param instanceof Integer) {
                statement.setInt(1, (Integer) param);
            }

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    films.add(mapResultSetToFilm(resultSet));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching films: " + e.getMessage());
        }
        return films;
    }

    @Override
    public List<Film> getAllFilms() {
        final String sql = "SELECT f.film_id, f.title, f.description, f.release_year, c.name AS category " +
                           "FROM film f " +
                           "JOIN film_category fc ON f.film_id = fc.film_id " +
                           "JOIN category c ON fc.category_id = c.category_id;";
        return getFilms(sql, null);
    }

    @Override
    public List<Film> getFilmByTitle(String partialTitle) {
        final String sql = "SELECT f.film_id, f.title, f.description, f.release_year, c.name AS category " +
                           "FROM film f " +
                           "JOIN film_category fc ON f.film_id = fc.film_id " +
                           "JOIN category c ON fc.category_id = c.category_id " +
                           "WHERE f.title LIKE ?";
        return getFilms(sql, "%" + partialTitle + "%");
    }

    @Override
    public List<Film> getFilmByGenre(String genre) {
        final String sql = "SELECT f.film_id, f.title, f.description, f.release_year, c.name AS category " +
                           "FROM film f " +
                           "JOIN film_category fc ON f.film_id = fc.film_id " +
                           "JOIN category c ON fc.category_id = c.category_id " +
                           "WHERE c.name = ?";
        return getFilms(sql, genre);
    }

    @Override
    public List<Film> getFilmByReleaseYear(int releaseYear) {
        final String sql = "SELECT f.film_id, f.title, f.description, f.release_year, c.name AS category " +
                           "FROM film f " +
                           "JOIN film_category fc ON f.film_id = fc.film_id " +
                           "JOIN category c ON fc.category_id = c.category_id " +
                           "WHERE f.release_year = ?";
        return getFilms(sql, releaseYear);
    }

    // Methods to update the film table display
    public void updateFilmTable(List<Film> films, String message) {
        BrowseMoviesPage.movieTable.setRowCount(0); // Clear previous data
        for (Film film : films) {
            BrowseMoviesPage.movieTable.addRow(new Object[]{film.getTitle(), film.getDesc(), film.getReleaseYear(), film.getGenre()});
        }
        if (films.isEmpty()) {
            JOptionPane.showMessageDialog(null, message, "No results", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    @Override
    public void updateFilmTableTitle(String title) {
        List<Film> films = getFilmByTitle(title);
        updateFilmTable(films, "No films found with title: " + title);
    }

    @Override
    public void updateFilmTableGenre(String genre) {
        List<Film> films = getFilmByGenre(genre);
        updateFilmTable(films, "No films found in genre: " + genre);
    }

    @Override
    public void updateFilmTableYear(int year) {
        List<Film> films = getFilmByReleaseYear(year);
        updateFilmTable(films, "No films found from the year: " + year);
    }

    @Override
    public void updateFilmTableAll() {
        List<Film> films = getAllFilms();
        updateFilmTable(films, "No films found in the db");
    }
}
