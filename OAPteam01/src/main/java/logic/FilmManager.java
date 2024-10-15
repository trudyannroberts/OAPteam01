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
 * This class executes statements to the database
 * 
 * @author Erica Laub Varpe
 */
public class FilmManager {
	/**
	 * This method is used to get all the movies from the database
	 * @return every film in the database
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
	 * This method returns film based on genre
	 * @return films that exists in the chosen genre
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
	 * @return 
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