package review;

import java.io.IOException;
import java.util.List;

/**
 * Interface for handling film reviews in the media streaming service.
 */
public interface ReviewHandler {

    /**
     * Saves a new review for the specified film title.
     * If a review for the film already exists, it updates the average rating.
     *
     * @param filmTitle The title of the film.
     * @param newReview The new rating provided by the user.
     * @throws IOException If there is an error during the file writing process.
     */
    void saveReviewToFile(String filmTitle, int newReview) throws IOException;

    /**
     * Finds the line in the review file associated with the specified film title.
     *
     * @param lines     A list of lines from the review file.
     * @param filmTitle The title of the film to search for.
     * @return The line with the film's review data, or null if the film is not found.
     */
    String findFilmLine(List<String> lines, String filmTitle);

    /**
     * Updates the average review score for the specified film based on a new review.
     *
     * @param filmLine  The line with the film's current review data.
     * @param newReview The new rating to incorporate.
     * @return The updated line reflecting the new average score and review count.
     */
    String updateAverageReview(String filmLine, int newReview);
}
