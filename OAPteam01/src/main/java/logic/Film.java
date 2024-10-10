package logic;
/**
 * @author Erica Laub Varpe
 */
public class Film {
	
	private String title;
	private String desc;
	private int releaseYear;
	private String genre;

	public Film(String title, String desc, int releaseYear, String genre) {
		this.setTitle(title);
		this.setDesc(desc);
		this.setReleaseYear(releaseYear);
		this.setGenre(genre); 
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(int releaseYear) {
		this.releaseYear = releaseYear;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	@Override
	public String toString() {
		return "Title: '" + title + '\'' +
                "\nDescription: '" + desc + '\'' +
                "\nRelease year: " + releaseYear +
                "\nGenre: '" + genre + '\'' + "\n";
	}
}
