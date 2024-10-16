package logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DatabaseConnection;

/**
 * This class is responsible for executing SQL statements related to films 
 * in the database, such as retrieving films by title, genre, or release year.
 * It uses a DAO (Data Access Object) pattern to interact with the database.
 * 
 * @author Erica Laub Varpe
 */
public class FilmManager {
	
    /**
     * Retrieves all the films from the database.
     *
     * @return a list of all films found in the database
     */
	public List<Film> getAllFilms(){
		final String sql = "SELECT f.film_id, f.title, f.description, f.release_year, c.name AS category "
				+ "FROM film f "
				+ "JOIN film_category fc ON f.film_id = fc.film_id "
				+ "JOIN category c ON fc.category_id = c.category_id;";
		
		List<Film> films = new ArrayList<>();
		
        try (Connection connection = DatabaseConnection.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)) {
               
               while (resultSet.next()) {
            	   String title = resultSet.getString("title");
            	   String desc = resultSet.getString("description");
            	   int releaseYear = resultSet.getInt("release_year");
            	   String genre = resultSet.getString("category");
            	   
            	   films.add(new Film(title, desc, releaseYear, genre));
               }
               
			} catch (SQLException e) {
				e.printStackTrace();
			}
        
        return films;
        
		}
	
    /**
     * Searches for films based on a partial title. The user can input a part of the film's title,
     * and the method will return all matching films that contain the search term.
     *
     * @param partialTitle a substring of the title to search for
     * @return a list of films whose titles contain the specified search term
     */
	public List<Film> getFilmByTitle(String partialTitle) {
	    final String sql = "SELECT f.film_id, f.title, f.description, f.release_year, c.name AS category " +
	             "FROM film f " +
	             "JOIN film_category fc ON f.film_id = fc.film_id " +
	             "JOIN category c ON fc.category_id = c.category_id " +
	             "WHERE f.title LIKE ?";

	    List<Film> films = new ArrayList<>();

	    try (Connection connection = DatabaseConnection.getConnection();
	         PreparedStatement statement = connection.prepareStatement(sql)) {

	        statement.setString(1, "%" + partialTitle + "%"); // % used for wildcard notation

	        try (ResultSet resultSet = statement.executeQuery()) {

	            while (resultSet.next()) {
	                String title = resultSet.getString("title");
	                String desc = resultSet.getString("description");
	                int releaseYear = resultSet.getInt("release_year");
	                String genre = resultSet.getString("category");

	                films.add(new Film(title, desc, releaseYear, genre));
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return films;
	}
	
    /**
     * Retrieves films based on a specific genre provided by the user.
     *
     * @param genre the genre to filter films by
     * @return a list of films belonging to the specified genre
     */
	public List<Film> getFilmByGenre(String genre){
		
		final String sql = "SELECT f.film_id, f.title, f.description, f.release_year, c.name AS category " +
		             "FROM film f " +
		             "JOIN film_category fc ON f.film_id = fc.film_id " +
		             "JOIN category c ON fc.category_id = c.category_id " +
		             "WHERE c.name = ?";
		
		List<Film> films = new ArrayList<>();
		
		try (Connection connection = DatabaseConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql)) {
			
			statement.setString(1, genre);
			
            try (ResultSet resultSet = statement.executeQuery()) {
   
                while (resultSet.next()) {
             	   String title = resultSet.getString("title");
             	   String desc = resultSet.getString("description");
             	   int releaseYear = resultSet.getInt("release_year");
             	   genre = resultSet.getString("category");
                    
                    films.add(new Film(title, desc, releaseYear, genre));
                }
			}	
		} catch (SQLException e) {
            e.printStackTrace();
        }
		return films;
	}
	
    /**
     * Retrieves films from the database that were released in the specified year.
     *
     * @param releaseYear the year to filter films by
     * @return a list of films released in the specified year
     */
	public List<Film> getFilmByReleaseYear(int releaseYear){
		
		final String sql = "SELECT f.film_id, f.title, f.description, f.release_year, c.name AS category " +
	             "FROM film f " +
	             "JOIN film_category fc ON f.film_id = fc.film_id " +
	             "JOIN category c ON fc.category_id = c.category_id " +
	             "WHERE f.release_year = ?";
	
	List<Film> films = new ArrayList<>();
	
	try (Connection connection = DatabaseConnection.getConnection();
		PreparedStatement statement = connection.prepareStatement(sql)) {
		
		statement.setInt(1, releaseYear);
		
       try (ResultSet resultSet = statement.executeQuery()) {

           while (resultSet.next()) {
        	   String title = resultSet.getString("title");
        	   String desc = resultSet.getString("description");
        	   releaseYear = resultSet.getInt("release_year");
        	   String genre = resultSet.getString("category");
               
               films.add(new Film(title, desc, releaseYear, genre));
           }
		}	
	} catch (SQLException e) {
       e.printStackTrace();
   }
	return films;
	}
}