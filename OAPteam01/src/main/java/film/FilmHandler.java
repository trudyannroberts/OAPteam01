package film;

import java.util.List;

/**
 * Interface defining the core film data operations.
 * This interface provides methods for retrieving films using various search criteria.
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
     * @param partialTitle	 a substring of the film title to search for; it is not case sensitive.
     * @return a list of films whose titles contain the specified search term, or an empty list if no matches are found.
     */
    List<Film> getFilmByTitle(String partialTitle);

    /**
     * Retrieves a list of films that belong to a specific genre. This enables users to filter films by genre.
     *
     * @param genre 	the genre by which to filter films, matching with the database genre name; it is not case sensitive. 
     * @return a list of films that match the specified genre, or an empty list if no matches are found and a pop up saying it doest exist in the database.
     */
    List<Film> getFilmByGenre(String genre);

    /**
     * Retrieves a list of films that were released in a specified year.
     *
     * @param releaseYear 	the year to filter films by, in YYYY format
     * @return a list of films released in the specified year, or an empty list if no matches are found and a pop up saying it doest exist in the database.
     */
    List<Film> getFilmByReleaseYear(int releaseYear);
}
