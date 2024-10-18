package review;
/**
 * @author Trudy Ann Roberts
 * The Review class is meant to let the user rate the film from 1-10. We need to connect the rating to a film, and therefore I also included film title.
 */
import java.nio.file.*;
import java.io.*;
public class Review {
	private int review;
	private String filmTitle;
	/**
	 * @param review is a constructor that allows a user to enter a rating when a film ends.
	 * @return review returns the value
	 * @raturn filmTitle returns the value
	 */
	public Review (int review, String filmTitle) {
		this.review = review;
		this.filmTitle = filmTitle;
	}
	public int getReview() {
		return review;
	}
	public String getFilmTitle() {
		return filmTitle;
	}	
	public void displayReview() {
		System.out.println("You've rated " + filmTitle + review + "/10");
	}
}
