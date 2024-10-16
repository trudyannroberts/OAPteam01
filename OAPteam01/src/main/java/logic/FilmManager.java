package logic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

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
	
	/*
	 // Action listener for title search button
    searchTitleButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String title = titleTextField.getText();
            if (!title.isEmpty()) {
                updateFilmTableTitle(title);
            } else {
                JOptionPane.showMessageDialog(null, "Title can't be empty", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    });

    // Action listener for genre search button
    searchButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String genre = genreTextField.getText();
            if (!genre.isEmpty()) {
                updateFilmTableGenre(genre);
            } else {
                JOptionPane.showMessageDialog(null, "Genre can't be empty", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    });

    // Action listener for release year search button
    searchYearButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String yearText = yearTextField.getText();
            if (!yearText.isEmpty()) {
                try {
                    int year = Integer.parseInt(yearText); // Convert input to an integer
                    updateFilmTableYear(year);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid year format", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Year can't be empty", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    });

    // Action listener for "Show all movies" button
    showAllButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            updateFilmTableAll();
        }
    });
}

	/**
	 * Updates the film table with films that match the specified title.
	 * 
	 * @param title the title to search for
	 */ /*
	private void updateFilmTableTitle(String title) {
		tableModel.setRowCount(0); // Clear previous data
		List<Film> films = filmManager.getFilmByTitle(title); // Get films based on title
		for (Film film : films) {
			tableModel.addRow(new Object[]{film.getTitle(), film.getDesc(), film.getReleaseYear(), film.getGenre()});
		}
		if (films.isEmpty()) {
        JOptionPane.showMessageDialog(null, "No films found with title: " + title, "No results", JOptionPane.INFORMATION_MESSAGE);
    }
}

	/**
	 * Updates the film table with films that match the specified genre.
	 * 
	 * @param genre the genre to search for
	 */
	/*
	private void updateFilmTableGenre(String genre) {
		tableModel.setRowCount(0); // Clear previous data
		List<Film> films = filmManager.getFilmByGenre(genre); // Get films based on genre
		for (Film film : films) {
			tableModel.addRow(new Object[]{film.getTitle(), film.getDesc(), film.getReleaseYear(), film.getGenre()});
		}
		if (films.isEmpty()) {
			JOptionPane.showMessageDialog(null, "No films found in genre: " + genre, "No results", JOptionPane.INFORMATION_MESSAGE);
    }
}

	/**
	 * Updates the film table with films that were released in the specified year.
	 * 
	 * @param year the release year to search for
	 */
	/*
	private void updateFilmTableYear(int year) {
		tableModel.setRowCount(0); // Clear previous data
		List<Film> films = filmManager.getFilmByReleaseYear(year); // Get films based on release year
		for (Film film : films) {
			tableModel.addRow(new Object[]{film.getTitle(), film.getDesc(), film.getReleaseYear(), film.getGenre()});
		}
		if (films.isEmpty()) {
			JOptionPane.showMessageDialog(null, "No films found from the year: " + year, "No results", JOptionPane.INFORMATION_MESSAGE);
    }
}

	/**
	 * Updates the film table with all films from the database.
	 */
	/*
	private void updateFilmTableAll() {
	    tableModel.setRowCount(0); // Clear previous data
	    List<Film> films = getAllFilms(); // Get all films
	    for (Film film : films) {
	        tableModel.addRow(new Object[]{film.getTitle(), film.getDesc(), film.getReleaseYear(), film.getGenre()});
	    }
	    if (films.isEmpty()) {
	        JOptionPane.showMessageDialog(null, "No films found in the database", "No results", JOptionPane.INFORMATION_MESSAGE);
	    }
	} */
}