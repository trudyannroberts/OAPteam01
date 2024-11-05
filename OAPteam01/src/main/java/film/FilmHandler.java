package film;

import java.util.List;

public interface FilmHandler {
    /**
     * Retrieves all the films from the database.
     *
     * @return a list of all films found in the database
     */
    List<Film> getAllFilms();

    /**
     * Searches for films based on a partial title. The user can input a part of the film's title,
     * and the method will return all matching films that contain the search term.
     *
     * @param partialTitle a substring of the title to search for
     * @return a list of films whose titles contain the specified search term
     */
    List<Film> getFilmByTitle(String partialTitle);

    /**
     * Retrieves films based on a specific genre provided by the user.
     *
     * @param genre the genre to filter films by
     * @return a list of films belonging to the specified genre
     */
    List<Film> getFilmByGenre(String genre);

    /**
     * Retrieves films from the database that were released in the specified year.
     *
     * @param releaseYear the year to filter films by
     * @return a list of films released in the specified year
     */
    List<Film> getFilmByReleaseYear(int releaseYear);

    /**
     * Updates the film table with films that match the specified title.
     * 
     * @param title the title to search for
     */
    void updateFilmTableTitle(String title);

    /**
     * Updates the film table with films that match the specified genre.
     * 
     * @param genre the genre to search for
     */
    void updateFilmTableGenre(String genre);

    /**
     * Updates the film table with films that were released in the specified year.
     * 
     * @param year the release year to search for
     */
    void updateFilmTableYear(int year);

    /**
     * Updates the film table with all films from the database.
     */
    void updateFilmTableAll();
}
