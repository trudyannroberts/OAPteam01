package film;

/**
 * This class represents a film with attributes such as title, description, release year, and genre.
 * It provides getter and setter methods to access and modify the film's attributes.
 * 
 * @author Erica Laub Varpe
 */
public class Film {
	
	private String title;
	private String desc;
	private int releaseYear;
	private String genre;

	/**
	 * Constructs a new Film object with the specified title, description, release year, and genre.
	 * 
	 * @param title the title of the film
	 * @param desc the description of the film
	 * @param releaseYear the year the film was released
	 * @param genre the genre of the film
	 */
	public Film(String title, String desc, int releaseYear, String genre) {
		this.setTitle(title);
		this.setDesc(desc);
		this.setReleaseYear(releaseYear);
		this.setGenre(genre); 
	}

	/**
	 * Returns the title of the film.
	 * 
	 * @return the title of the film
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title of the film.
	 * 
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Returns the description of the film.
	 * 
	 * @return the description of the film
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * Sets the description of the film.
	 * 
	 * @param desc the description to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * Returns the release year of the film.
	 * 
	 * @return the release year of the film
	 */
	public int getReleaseYear() {
		return releaseYear;
	}

	/**
	 * Sets the release year of the film.
	 * 
	 * @param releaseYear the release year to set
	 */
	public void setReleaseYear(int releaseYear) {
		this.releaseYear = releaseYear;
	}

	/**
	 * Returns the genre of the film.
	 * 
	 * @return the genre of the film
	 */
	public String getGenre() {
		return genre;
	}

	/**
	 * Sets the genre of the film.
	 * 
	 * @param genre the genre to set
	 */
	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	/**
	 * Returns a string representation of the Film object.
	 * 
	 * @return a string containing the title, description, release year, and genre of the film
	 */
	@Override
	public String toString() {
		return "Title: '" + title + '\'' +
                "\nDescription: '" + desc + '\'' +
                "\nRelease year: " + releaseYear +
                "\nGenre: '" + genre + '\'' + "\n";
	}
}
