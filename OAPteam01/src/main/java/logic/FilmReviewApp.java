package logic;
/**
 * @author Trudy Ann Roberts
 * This is the application that outputs the reviews. This can be removed and these methods implemented into GUI. 
 * Since GUI is in a different package, remember to import the logic package.
 */

public class FilmReviewApp {

    public static void main(String[] args) {
        /**
         * Creating an instance of a search class.
         */
        FilmSearcher filmSearcher = new FilmSearcher();
        
        /**
         * @param filmId is the value of film_id in the database. 
         * @param filmTitle connects with the filmId. 
         * The method getFilmTitleById() is inherited from the FilmSearcher class.
         * Right now filmId is set to 1, which means it will always show Academy Dinosaur. 
         * I need to change to a dynamic feature, that is connected to the current film the user is watching.
         */
        int filmId = 1; 
        String filmTitle = filmSearcher.getFilmTitleById(filmId);

        if (filmTitle != null) {
            System.out.println("You are watching: " + filmTitle);
            
            /**
             * This method can be picked up by the class where the user is playing a fil
             */
            FilmReview.promptForReview(filmTitle);
        } else {
            System.out.println("Film not found.");
        }
    }
}