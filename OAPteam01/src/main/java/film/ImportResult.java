package film;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the result of a film import operation.
 * Tracks both successful and failed film imports, maintaining separate lists
 * for each category along with relevant metadata.
 * 
 * @author Erica Laub Varpe
 */
public class ImportResult {
	
    /**
     * List of successfully imported films
     */
    private List<Film> successfulImports;
    
    /**
     * List of films that failed to import
     */
    private List<Film> failedImports;

    /**
     * Initializes a new ImportResult with empty lists for successful and failed imports.
     */
    public ImportResult() {
        this.successfulImports = new ArrayList<>();
        this.failedImports = new ArrayList<>();
    }
    
    /**
     * Adds a film to the list of successful imports.
     * Sets the film's import status to successful.
     * 
     * @param film The successfully imported film to add
     */
    public void addSuccessfulImport(Film film) {
        film.setImportSuccess(true);
        successfulImports.add(film);
    }
    
    /**
     * Adds a film to the list of failed imports.
     * Sets the film's import status to failed and records the failure reason.
     * 
     * @param film The film that failed to import
     * @param reason The reason for the import failure
     */
    public void addFailedImport(Film film, String reason) {
        film.setImportSuccess(false);
        film.setFailureReason(reason);
        failedImports.add(film);
    }
    
    /**
     * Gets the list of successfully imported films.
     * 
     * @return List of successfully imported Film objects
     */
    public List<Film> getSuccessfulImports() { 
    	return successfulImports; 
    	}
    
    /**
     * Gets the list of films that failed to import.
     * 
     * @return List of Film objects that failed to import
     */
    public List<Film> getFailedImports() { 
    	return failedImports; 
    	}
    
    /**
     * Gets the total number of successfully imported films.
     * 
     * @return The count of successful imports
     */
    public int getTotalSuccessful() { 
    	return successfulImports.size(); 
    	}
    
    /**
     * Gets the total number of failed film imports.
     * 
     * @return The count of failed imports
     */
    public int getTotalFailed() { 
    	return failedImports.size(); 
    	}
}
