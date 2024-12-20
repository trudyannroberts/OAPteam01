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
 * The FilmManager class manages film data retrieval from the database and updates the GUI.
 * It implements both FilmHandler for data operations and FilmViewHandler for GUI updates.
 *
 * <p>This class provides functionality for:
 * <ul>
 *   <li>Retrieving films using various search criteria</li>
 *   <li>Mapping database results to Film objects</li>
 *   <li>Updating the GUI film view with search results</li>
 * </ul>
 *
 * <p>Database queries support searching by:
 * <ul>
 *   <li>Film title (partial matches supported)</li>
 *   <li>Genre (exact matches)</li>
 *   <li>Release year</li>
 * </ul>
 * 
 * <p>SQL queries join the film, film_category, and category tables to retrieve complete film information.
 * 
 * @author Erica Laub Varpe
 */
public class FilmManager implements FilmHandler, FilmViewHandler {
    
	/**
     * Helpet method to map database ResultSet row to a Film object.
     * 
     * @param resultSet 	The ResultSet containing film data
     * @return A new Film object populated with data from the ResultSet
     * @throws SQLException if there's an error accessing the ResultSet
     */
    private Film mapResultSetToFilm(ResultSet resultSet) throws SQLException {
        String title = resultSet.getString("title");
        String desc = resultSet.getString("description");
        int releaseYear = resultSet.getInt("release_year");
        String genre = resultSet.getString("category");
        return new Film(title, desc, releaseYear, genre);
    }

    /**
     * Executes a parameterized SQL query and returns a list of Film objects.
     * Supports both String and Integer parameters for different search types.
     * 
     * @param sql 		The SQL query to execute
     * @param param 	The parameter to bind to the prepared statement (can be String or Integer),
     *             		or null for queries without parameters
     * @return List of Film objects matching the query criteria
     */
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

    /**
     * Helper method to update the film view in the GUI with the provided list of films.
     * Clears the previous data and displays a message if no films are found.
     *
     * @param films 	The list of films to display
     * @param message The message to show if no films are found
     */
    private void updateFilmView(List<Film> films, String message) {
        BrowseMoviesPage.filmView.setRowCount(0); // Clear previous data
        for (Film film : films) {
            BrowseMoviesPage.filmView.addRow(new Object[]{
                film.getTitle(), 
                film.getReleaseYear(), 
                film.getGenre(),
                film.getDescription()
            });
        }
        if (films.isEmpty()) {
            JOptionPane.showMessageDialog(null, message, "No results", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    @Override
    public void updateFilmViewTitle(String title) {
        List<Film> films = getFilmByTitle(title);
        updateFilmView(films, "No films found with title: " + title);
    }

    @Override
    public void updateFilmViewGenre(String genre) {
        List<Film> films = getFilmByGenre(genre);
        updateFilmView(films, "No films found in genre: " + genre);
    }

    @Override
    public void updateFilmViewYear(int year) {
        List<Film> films = getFilmByReleaseYear(year);
        updateFilmView(films, "No films found from the year: " + year);
    }

    @Override
    public void updateFilmViewAll() {
        List<Film> films = getAllFilms();
        updateFilmView(films, "No films found in the db");
    }
}
