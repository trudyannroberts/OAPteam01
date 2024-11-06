package review;

import javax.swing.*;
import film.FilmManager;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Trudy Ann Roberts
 * This class creates a file to store the reviews and film title.
 */
public class ReviewManager implements ReviewHandler {
    private static final String REVIEW_FILE = "film_reviews.txt";  // File to store reviews

    /**
     * Saves the film review to a file.
     * If the film already has reviews, the new review will update the average score.
     */
    public void saveReviewToFile(String filmTitle, int newReview) {
        try {
            List<String> lines = new ArrayList<>();
            if (Files.exists(Paths.get(REVIEW_FILE))) {
                lines = Files.readAllLines(Paths.get(REVIEW_FILE)); // Read existing reviews
            }

            // Check if the film already has reviews
            String filmLine = findFilmLine(lines, filmTitle);

            if (filmLine == null) {
                // No existing reviews, add a new entry
                lines.add(filmTitle + " - " + newReview + " (1 review)");
            } else {
                // Update the existing review with a new average
                String updatedLine = updateAverageReview(filmLine, newReview);
                lines.remove(filmLine);
                lines.add(updatedLine);
            }

            // Write to the file
            Files.write(Paths.get(REVIEW_FILE), lines);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving the review: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Finds the line in the file for the given film title.
     */
    public String findFilmLine(List<String> lines, String filmTitle) {
        for (String line : lines) {
            if (line.startsWith(filmTitle)) {
                return line;
            }
        }
        return null;
    }

    /**
     * Updates the average review for a film.
     */
    public String updateAverageReview(String filmLine, int newReview) {
        try {
            // Extract current average and review count
            String[] parts = filmLine.split(" - ");  // Split by " - "
            String reviewPart = parts[1].trim();  // The part with the reviews, e.g., "8 (2 reviews)"
            
            // Find the current average and number of reviews
            int currentAverage = Integer.parseInt(reviewPart.split(" ")[0]);  // Extract current average
            String reviewCountPart = reviewPart.split("\\(")[1].split(" ")[0]; // Extract the count within brackets
            int currentReviewCount = Integer.parseInt(reviewCountPart);
            
            // Calculate new average
            int newReviewCount = currentReviewCount + 1;
            int newAverage = (currentAverage * currentReviewCount + newReview) / newReviewCount;
            
            // Return updated review line
            return parts[0] + " - " + newAverage + " (" + newReviewCount + " reviews)";
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error updating review: " + e.getMessage());
            e.printStackTrace();
            return filmLine;  // Return the original line in case of an error
        }
    }
    
    /**
     * Loads all reviews from the film_reviews.txt file.
     * Each review is represented by a line in the file.
     */
    /**
     * Loads all reviews from the review file.
     * 
     * @return a list of reviews
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
