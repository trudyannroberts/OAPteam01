package logic;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Trudy Ann Roberts
 * This class creates a file to store the reviews and film title.
 */

public class FilmReview {

    private static final String REVIEW_FILE = "film_reviews.txt";  // File to store reviews
    
    /**
     * 
     * @param filmTitle is the film the user has chosen. 
     * his method prompts the user to review a film between 1 - 10. 
     * If the user enters a number smaller than 1 or larger than 10, this will return a message telling the user that the input in invalid.
     * If the review is valid, it will call the saveReviewToFile() method.
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
     * 
     * @param filmTitle is the film title that is to be saved.
     * @param newReview is the review given by the user.
     * Here we have an Array List that will list all the titles and reviews. 
     * If the file exists, it will read the file.
     */
    private static void saveReviewToFile(String filmTitle, int newReview) {
        try {
            List<String> lines = new ArrayList<>();
            if (Files.exists(Paths.get(REVIEW_FILE))) {
                lines = Files.readAllLines(Paths.get(REVIEW_FILE)); 
            }

            /**
             *  Check if the film already has reviews
             *  If the film has no previous reviews, create a new entry.
             *  If it has previous reviews, then call the updateAverageReview() method and update the file with the new average.
             */
            String filmLine = findFilmLine(lines, filmTitle);

            if (filmLine == null) {
         
                lines.add(filmTitle + " - " + newReview + " (1 review)");
            } else {
             
                String updatedLine = updateAverageReview(filmLine, newReview);
                lines.remove(filmLine);
                lines.add(updatedLine);
            }

            /**
             * Write to the file.
             */
            Files.write(Paths.get(REVIEW_FILE), lines);
            System.out.println("Review saved!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
    Method to find a movie's review in the file
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
     * Method to update the average review
     * @param filmLine will include the film title and review that is split with a - . Now we split it to be able to extract the current average.
     * @param newReview
     * @return
     */
    private static String updateAverageReview(String filmLine, int newReview) {
        try {
            // Extract current average and review count
            String[] parts = filmLine.split(" - ");  // Split by " - "
            String reviewPart = parts[1].trim();  // The part with the reviews, e.g. "8 (2 reviews)"
            
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
            e.printStackTrace();
            return filmLine;  // Return the original line in case of an error
        }
    }

}