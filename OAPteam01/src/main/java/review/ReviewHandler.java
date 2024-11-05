package review;

import java.io.IOException;
import java.util.List;

public interface ReviewHandler {

    /**
     * Prompts the user to review a film with a rating between 1 and 10.
     * 
     * @param filmTitle The title of the film to be reviewed.
     */
    void promptForReview(String filmTitle);

    /**
     * Saves a review for the given film title.
     * 
     * @param filmTitle The title of the film.
     * @param newReview The rating provided by the user.
     * @throws IOException If there is an error during the file writing process.
     */
    void saveReviewToFile(String filmTitle, int newReview) throws IOException;

    /**
     * Finds the line in the file that corresponds to the given film title.
     * 
     * @param filmTitle The title of the film to search for.
     * @return The line containing the film's review data, or null if not found.
     * @throws IOException If there is an error reading the file.
     */
    String findFilmLine(List<String> lines, String filmTitle) throws IOException;

    /**
     * Updates the average review score for the specified film title.
     * 
     * @param filmLine The line containing the film's review data.
     * @param newReview The new review to add.
     * @return The updated review line.
     */
    String updateAverageReview(String filmLine, int newReview);
}
