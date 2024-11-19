package review;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * This class creates and manages a file to store film reviews.
 * It handles adding new reviews, updating existing reviews, and calculating average review scores for films.
 * 
 * Currently, a user can review a film several times, which is not optimal. 
 * An improvement would be to make sure a user could not review a film several times, 
 * but rather change their review.
 * 
 * @author Trudy Ann Roberts
 */
public class ReviewManager implements ReviewHandler {
    private static final String REVIEW_FILE = "film_reviews.txt";  // File to store reviews

    /**
     * Saves the film review to a file.
     * If the film already has reviews, the new review will update the average score.
     */
    public void saveReview(String filmTitle, int newReview) {
        try {
            List<String> lines = new ArrayList<>();
            if (Files.exists(Paths.get(REVIEW_FILE))) {
                lines = Files.readAllLines(Paths.get(REVIEW_FILE)); // Read existing reviews
            }

            // Check if the film already has reviews
            boolean filmFound = false;
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                if (line.startsWith(filmTitle + " ")) {
                    // Film exists, update its review
                    String updatedLine = updateAverageReview(line, newReview);
                    lines.set(i, updatedLine);
                    filmFound = true;
                    break;
                }
            }

            // If film was not found, add a new entry
            if (!filmFound) {
                lines.add(filmTitle + " - The average review: " + newReview + " (1 review)");
            }

            // Write to the file
            Files.write(Paths.get(REVIEW_FILE), lines);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving the review: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Updates the average review for a film by incorporating a new review.
     * Calculates the new average rating and increments the review count.
     */
    public String updateAverageReview(String filmLine, int newReview) {
        try {
            // Split the line carefully
            String[] parts = filmLine.split(" - The average review: ");
            if (parts.length < 2) {
                // If the format is unexpected, handle it gracefully
                return filmLine;
            }
            
            String filmTitle = parts[0];
            String reviewPart = parts[1].trim();
            
            // Extract current average and review count
            String[] reviewDetails = reviewPart.split(" \\(");
            if (reviewDetails.length < 2) {
                // Unexpected format
                return filmLine;
            }
            
            int currentAverage = Integer.parseInt(reviewDetails[0]);
            int currentReviewCount = Integer.parseInt(reviewDetails[1].split(" ")[0]);
            
            // Calculate new average
            int newReviewCount = currentReviewCount + 1;
            int newAverage = (currentAverage * currentReviewCount + newReview) / newReviewCount;
            
            // Return updated review line
            return filmTitle + " - The average review: " + newAverage + " (" + newReviewCount + " reviews)";
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error updating review: " + e.getMessage());
            e.printStackTrace();
            return filmLine;  // Return the original line in case of an error
        }
    }
    
    /**
     * Loads all reviews from the review file.
     * 
     * @return a list of all stored film reviews, or an empty list if no reviews exist
     */
    public List<String> loadAllReviews() {
        List<String> reviews = new ArrayList<>();

        try {
            // Check if the review file exists before attempting to read it
            if (Files.exists(Paths.get(REVIEW_FILE))) {
                // Read all lines from the file
                reviews = Files.readAllLines(Paths.get(REVIEW_FILE));
            } else {
                // File does not exist, notify the user or handle accordingly
                JOptionPane.showMessageDialog(null, "No reviews found. The file does not exist.");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error loading reviews: " + e.getMessage());
            e.printStackTrace();
        }

        return reviews;
    }
}
