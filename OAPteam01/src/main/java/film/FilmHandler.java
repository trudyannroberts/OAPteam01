package film;

import java.util.List;

/**
 * The FilmHandler interface provides methods for retrieving film information
 * from the database. This interface allows users to:
 * <ul>
 *     <li>Retrieve all films, or filter them by title, genre, or release year.</li>
 *     <li>Update the film table display based on the specific search criteria.</li>
 * </ul>
 * Each method performs a specific operation related to film management: fetching
 * films based on criteria and updating the current film view.
 * 
 * Implementations of this interface are expected to handle the necessary database interactions.
 * 
 * @author Erica Laub Varpe
 */
public interface FilmHandler {

    /**
     * Retrieves a complete list of all films available in the database.
     *
     * @return a list of all films found in the database, or an empty list if no films are available
     */
    List<Film> getAllFilms();

    /**
     * Searches for films based on a partial title provided by the user. This allows for finding films
     * even if only part of the title is known.
     *
     * @param partialTitle a substring of the film title to search for; it is not case sensitive.
     * @return a list of films whose titles contain the specified search term, or an empty list if no matches are found.
     */
    List<Film> getFilmByTitle(String partialTitle);

    /**
     * Retrieves a list of films that belong to a specific genre. This enables users to filter films by genre.
     *
     * @param genre the genre by which to filter films, matching with the database genre name; it is not case sensitive. 
     * @return a list of films that match the specified genre, or an empty list if no matches are found and a pop up saying it doest exist in the database.
     */
    List<Film> getFilmByGenre(String genre);

    /**
     * Retrieves a list of films that were released in a specified year.
     *
     * @param releaseYear the year to filter films by, in YYYY format
     * @return a list of films released in the specified year, or an empty list if no matches are found and a pop up saying it doest exist in the database.
     */
    List<Film> getFilmByReleaseYear(int releaseYear);

    /**
     * Updates the film table to display only the films that contain the specified title.
     * Useful for refreshing the table view based on a search by title.
     * 
     * @param title the title to search for, matching exactly or partially with the film's title in the database
     */
    void updateFilmViewTitle(String title);

    /**
     * Updates the film table to display only the films that match the specified genre.
     * Useful for refreshing the table view based on a genre search.
     * 
     * @param genre the genre to filter films by, matching with the genre name in the database 
     */
    void updateFilmViewGenre(String genre);

    /**
     * Updates the film table to display only the films released in the specified year.
     * Useful for refreshing the table view based on a release year search.
     * 
     * @param year the release year to filter films by
     */
    void updateFilmViewYear(int year);

    /**
     * Updates the film table to display all films currently stored in the database.
     * This method can be used to reset the table view to its default state, showing all films.
     */
    void updateFilmViewAll();
}
