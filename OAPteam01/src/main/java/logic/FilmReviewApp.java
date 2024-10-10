package logic;

/**
 * @author Trudy Ann Roberts
 * 
 * This is the FilmReviewApp, a console-based application that outputs film reviews. 
 * In a real implementation, these methods could be integrated into a GUI class. 
 * When integrating with a GUI located in a different package, be sure to import the 
 * relevant classes from the `logic` package.
 */
public class FilmReviewApp {

    public static void main(String[] args) {
        // Create an instance of FilmSearcher to retrieve film titles by their ID.
        FilmSearcher filmSearcher = new FilmSearcher();
        
        /**
         * The current film ID being used for the demo is statically set to 1, which will 
         * always return "Academy Dinosaur" from the database. The method getFilmTitleById() 
         * from the FilmSearcher class is used to fetch the title based on the given filmId.
         * 
         * @param filmId represents the unique film identifier from the database.
         * @param filmTitle stores the title of the film returned by the getFilmTitleById() method.
         * 
         * To make the application more dynamic, this filmId should be replaced with a 
         * value representing the film currently being watched by the user, obtained through 
         * the actual film-watching logic or the GUI interaction.
         */
        int filmId = 1;  // For demo purposes, filmId is statically set.
        String filmTitle = filmSearcher.getFilmTitleById(filmId);

        if (filmTitle != null) {
            System.out.println("You are watching: " + filmTitle);

            /**
             * The promptForReview() method from the FilmReview class could be invoked by 
             * the class where the user is actively watching a film. This method prompts the 
             * user for a review and processes the input for the current film title.
             * 
             * The GUI class should replace this console output with a user interface action.
             */
            FilmReview.promptForReview(filmTitle);
        } else {
            System.out.println("Film not found.");
        }
    }
}
