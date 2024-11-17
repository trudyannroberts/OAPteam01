package film;

import java.util.ArrayList;
import java.util.List;

public class ImportResult {
    private List<Film> successfulImports;
    private List<Film> failedImports;
    
    public ImportResult() {
        this.successfulImports = new ArrayList<>();
        this.failedImports = new ArrayList<>();
    }
    
    public void addSuccessfulImport(Film film) {
        film.setImportSuccess(true);
        successfulImports.add(film);
    }
    
    public void addFailedImport(Film film, String reason) {
        film.setImportSuccess(false);
        film.setFailureReason(reason);
        failedImports.add(film);
    }
    
    public List<Film> getSuccessfulImports() { return successfulImports; }
    public List<Film> getFailedImports() { return failedImports; }
    public int getTotalSuccessful() { return successfulImports.size(); }
    public int getTotalFailed() { return failedImports.size(); }
}
