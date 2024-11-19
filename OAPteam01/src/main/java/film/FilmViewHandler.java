package film;

/**
 * Interface defining GUI update operations for film display.
 * This interface handles updating the view with film search results.
 * 
 * @author Erica Laub Varpe
 */
public interface FilmViewHandler {
    /**
     * Updates the view to display films matching the given title.
     *
     * @param title The title to search for
     */
    void updateFilmViewTitle(String title);
    
    /**
     * Updates the view to display films of the given genre.
     *
     * @param genre The genre to search for
     */
    void updateFilmViewGenre(String genre);
    
    /**
     * Updates the view to display films from the given year.
     *
     * @param year The release year to search for
     */
    void updateFilmViewYear(int year);
    
    /**
     * Updates the view to display all films.
     */
    void updateFilmViewAll();
}
