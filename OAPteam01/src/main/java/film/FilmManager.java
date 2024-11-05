package film;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import db.DatabaseConnection;
import gui.BrowseMoviesPage;


/**
 * This class is responsible for executing SQL statements related to films 
 * in the database, such as retrieving films by title, genre, or release year.
 * It uses the DAO (Data Access Object) pattern to interact with the database.
 * 
 * @author Erica Laub Varpe
 */
public class FilmManager implements FilmHandler {
    

	@Override
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
	

	@Override
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
	
	@Override
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
	
	@Override
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

	@Override
	public void updateFilmTableTitle(String title) {
		BrowseMoviesPage.movieTable.setRowCount(0); // Clear previous data
		List<Film> films = getFilmByTitle(title); // Get films based on title
		for (Film film : films) {
			BrowseMoviesPage.movieTable.addRow(new Object[]{film.getTitle(), film.getDesc(), film.getReleaseYear(), film.getGenre()});
		}
		if (films.isEmpty()) {
        JOptionPane.showMessageDialog(null, "No films found with title: " + title, "No results", JOptionPane.INFORMATION_MESSAGE);
    }
}

	@Override
	public void updateFilmTableGenre(String genre) {
		BrowseMoviesPage.movieTable.setRowCount(0); // Clear previous data
		List<Film> films = getFilmByGenre(genre); // Get films based on genre
		for (Film film : films) {
			BrowseMoviesPage.movieTable.addRow(new Object[]{film.getTitle(), film.getDesc(), film.getReleaseYear(), film.getGenre()});
		}
		if (films.isEmpty()) {
			JOptionPane.showMessageDialog(null, "No films found in genre: " + genre, "No results", JOptionPane.INFORMATION_MESSAGE);
    }
}


	@Override
	public void updateFilmTableYear(int year) {
		BrowseMoviesPage.movieTable.setRowCount(0); // Clear previous data
		List<Film> films = getFilmByReleaseYear(year); // Get films based on release year
		for (Film film : films) {
			BrowseMoviesPage.movieTable.addRow(new Object[]{film.getTitle(), film.getDesc(), film.getReleaseYear(), film.getGenre()});
		}
		if (films.isEmpty()) {
			JOptionPane.showMessageDialog(null, "No films found from the year: " + year, "No results", JOptionPane.INFORMATION_MESSAGE);
    }
}


	@Override
	public void updateFilmTableAll() {
		BrowseMoviesPage.movieTable.setRowCount(0); // Clear previous data
	    List<Film> films = getAllFilms(); // Get all films
	    for (Film film : films) {
	    	BrowseMoviesPage.movieTable.addRow(new Object[]{film.getTitle(), film.getDesc(), film.getReleaseYear(), film.getGenre()});
	    }
	    if (films.isEmpty()) {
	        JOptionPane.showMessageDialog(null, "No films found in the db", "No results", JOptionPane.INFORMATION_MESSAGE);
	    }
	}
}