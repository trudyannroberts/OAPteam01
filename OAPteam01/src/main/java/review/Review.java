package review;
/**
 * The Review class is meant to let the user review the film from 1-10. 
 * We need to connect the rating to a film, and therefore I also included film title.
 * 
 * @author Trudy Ann Roberts
 */

import javax.swing.JOptionPane;

public class Review {
	
	/**
	 * @param review is the number between 1 - 10 given by the user
	 * @param filmTitle will be the film title connected to the review
	 */
	private int review;
	private String filmTitle;
	
	/**
	 * Creates a new Review instance with a rating and film title.
	 * 
	 * @param review the numerical rating for the film (1-10)
	 * @param filmTitle the title of the film being reviewed
	 */
	public Review (int review, String filmTitle) {
		this.review = review;
		this.filmTitle = filmTitle;
	}
	
	/**
	 * Retrieves the numerical rating for the film.
	 * 
	 * @return the rating between 1 and 10
	 */
	public int getReview() {
		return review;
	}
	
	/**
	 * Retrieves the title of the film.
	 * 
	 * @return the film's title
	 */
	public String getFilmTitle() {
		return filmTitle;
	}
	
	/**
	 * Displays a message dialog showing the film title and its rating.
	 * Uses JOptionPane to show a pop-up with review information.
	 */
	public void displayReview() {
		JOptionPane.showMessageDialog(null, "You've rated " + filmTitle + " " + review + "/10" );
	}
}
