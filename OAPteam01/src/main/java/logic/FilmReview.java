package logic;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Trudy Ann Roberts
 * 
 * This class manages film reviews. It allows users to review films and stores the reviews 
 * along with the film titles in a file. If a film already has reviews, it updates the average review.
 */
public class FilmReview {

    private static final String REVIEW_FILE = "film_reviews.txt";  // Path to the file where reviews are stored
    
    /**
     * Prompts the user to provide a review for a selected film.
     * 
     * @param filmTitle The title of the film the user is reviewing.
     * 
     * This method asks the user to input a rating between 1 and 10. 
     * If the rating is valid, it calls the `saveReviewToFile()` method to store or update the review.
     * If the rating is invalid (less than 1 or greater than 10), it outputs an error message.
     */
    public static void promptForReview(String filmTitle) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please rate the film '" + filmTitle + "' (1-10): ");
        int review = scanner.nextInt();
        
        if (review < 1 || review > 10) {
            System.out.println("Invalid input. Please enter a number between 1 and 10.");
            return;
        }
        saveReviewToFile(filmTitle, review);
    }

    /**
     * Saves the user's review to the file.
     * 
     * @param filmTitle The title of the film being reviewed.
     * @param newReview The user's review score for the film.
     * 
     * This method reads the existing reviews from the file into a list. 
     * If the film already has reviews, it updates the average review using the 
     * `updateAverageReview()` method. Otherwise, it adds a new review entry for the film.
     */
    private static void saveReviewToFile(String filmTitle, int newReview) {
        try {
            List<String> lines = new ArrayList<>();
            if (Files.exists(Paths.get(REVIEW_FILE))) {
                lines = Files.readAllLines(Paths.get(REVIEW_FILE)); 
            }

            /**
             * Check if the film already has reviews in the file.
             * If it does, update the average review by calling `updateAverageReview()`.
             * If not, add a new entry for the film and its review.
             */
            String filmLine = findFilmLine(lines, filmTitle);

            if (filmLine == null) {
                // If no existing reviews for this film, add a new entry with the current review.
                lines.add(filmTitle + " - " + newReview + " (1 review)");
            } else {
                // Update the existing review entry with a new average review.
                String updatedLine = updateAverageReview(filmLine, newReview);
                lines.remove(filmLine);
                lines.add(updatedLine);
            }

            // Write the updated list of reviews back to the file.
            Files.write(Paths.get(REVIEW_FILE), lines);
            System.out.println("Review saved!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Finds the line corresponding to a film's reviews in the file.
     * 
     * @param lines The list of lines (reviews) read from the file.
     * @param filmTitle The title of the film to search for in the file.
     * 
     * @return The line containing the review information for the specified film, or null if not found.
     */
    private static String findFilmLine(List<String> lines, String filmTitle) {
        for (String line : lines) {
            if (line.startsWith(filmTitle)) {
                return line;
            }
        }
        return null;
    }

    /**
     * Updates the average review for a film.
     * 
     * @param filmLine The line from the file that contains the current review information for the film.
     * @param newReview The new review to be added to the film's average.
     * 
     * @return A new string representing the updated average review and the total number of reviews for the film.
     * 
     * This method parses the current average review and the number of reviews from the existing review entry, 
     * recalculates the average with the new review, and returns an updated review string.
     */
    private static String updateAverageReview(String filmLine, int newReview) {
        try {
            // Extract the current average and the number of reviews from the line
            String[] parts = filmLine.split(" - ");  // Split the line by " - " to get the review details
            String reviewPart = parts[1].trim();     // The part containing the current average and review count
            
            // Extract the current average review score and the number of reviews
            int currentAverage = Integer.parseInt(reviewPart.split(" ")[0]);  // Get the current average score
            String reviewCountPart = reviewPart.split("\\(")[1].split(" ")[0];  // Extract the number of reviews from "(x reviews)"
            int currentReviewCount = Integer.parseInt(reviewCountPart);
            
            // Calculate the new average review score
            int newReviewCount = currentReviewCount + 1;
            int newAverage = (currentAverage * currentReviewCount + newReview) / newReviewCount;
            
            // Return the updated review entry with the new average and review count
            return parts[0] + " - " + newAverage + " (" + newReviewCount + " reviews)";
        } catch (Exception e) {
            e.printStackTrace();
            return filmLine;  // Return the original line in case of an error
        }
    }
}
