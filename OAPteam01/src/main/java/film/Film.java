package film;

/**
 * This class represents a film with attributes such as title, description, release year, and genre.
 * It provides getter and setter methods to access and modify the film's attributes.
 * 
 * @author Erica Laub Varpe
 */
public class Film {
	
    private String title;
    private String description;
    private int releaseYear;
    private String genre;
    private boolean importSuccess;
    private String failureReason;

	/**
	 * Constructs a new Film object with the specified title, description, release year, and genre.
	 * 
	 * @param title 		the title of the film
	 * @param description 	the description of the film
	 * @param releaseYear	the year the film was released
	 * @param genre 		the genre of the film
	 */
    public Film(String title, String description, int releaseYear, String genre) {
        this.title = title;
        this.description = description;
        this.releaseYear = releaseYear;
        this.genre = genre;
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
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description of the film.
	 * 
	 * @param description the description to set
	 */
	public void setDescription(String desc) {
		this.description = desc;
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
     * Returns whether the film was successfully imported.
     *
     * @return true if the import was successful, false otherwise
     */
    public boolean isImportSuccess() { 
    	return importSuccess; 
    	}
    
    /**
     * Sets the import success status of the film.
     *
     * @param importSuccess the import success status to set
     */
    public void setImportSuccess(boolean importSuccess) { 
    	this.importSuccess = importSuccess; 
    	}
    
    /**
     * Returns the reason for import failure, if any.
     *
     * @return the failure reason, or null if import was successful
     */
    public String getFailureReason() { 
    	return failureReason; 
    	}
    
    /**
     * Sets the reason for import failure.
     *
     * @param failureReason the failure reason to set
     */
    public void setFailureReason(String failureReason) { 
    	this.failureReason = failureReason; 
    	}
	
	/**
	 * Returns a string representation of the Film object.
	 * 
	 * @return a string containing the title, description, release year, and genre of the film
	 */
	@Override
	public String toString() {
		return "Title: '" + title + '\'' +
                "\nDescription: '" + description + '\'' +
                "\nRelease year: " + releaseYear +
                "\nGenre: '" + genre + '\'' + "\n";
	}
}
