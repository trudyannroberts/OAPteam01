package film;

import db.FilmDAO;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import db.DatabaseConnection;

import java.io.FileReader;
import java.sql.Connection;
import java.util.List;
import java.util.ArrayList;

/**
 * Service class responsible for coordinating the import of films from CSV files into the database.
 * This class handles CSV file reading, data validation, and coordinates with FilmDAO for database operations.
 * 
 * <p>The expected CSV format is:
 * <pre>
 * title,description,releaseYear,genre
 * "Film Title","Description text",2024,"Action"
 * </pre>
 * 
 * 
 * @author Erica Laub Varpe
 */
public class FilmImportService {
    
    /**
     * Coordinates the import of films from a CSV file into the database. This method manages the overall
     * import process by reading the CSV file and coordinating with FilmDAO for database operations.
     * 
     * <p>The method will:
     * <ul>
     *   <li>Read and parse the CSV file</li>
     *   <li>Validate each film's data</li>
     *   <li>Coordinate with FilmDAO for database operations</li>
     *   <li>Track successful and failed imports</li>
     *   <li>Manage database transactions</li>
     * </ul>
     * 
     * <p>If any film fails to import, the error will be logged in the ImportResult,
     * but the method will continue processing other films.
     * 
     * @param filePath	The path to the CSV file containing film data. Must be a valid file path
     *               	with read permissions and proper CSV formatting.
     * @return ImportResult containing information about successful and failed imports, including:
     *         <ul>
     *           <li>Successfully imported films</li>
     *           <li>Failed imports with error messages</li>
     *           <li>Total number of processed films</li>
     *         </ul>
     * @throws Exception If there is an error reading the file or accessing the database.
     */
    public ImportResult importFilmsFromCSV(String filePath) throws Exception {
        ImportResult result = new ImportResult();
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);
            FilmDAO filmDAO = new FilmDAO(conn);
            
            List<Film> films = readFilmsFromCSV(filePath);
            
            for (Film film : films) {
                try {
                    int genreId = filmDAO.getGenreId(film.getGenre());
                    int filmId = filmDAO.insertFilm(film);
                    filmDAO.insertFilmCategory(filmId, genreId);
                    
                    result.addSuccessfulImport(film);
                    
                } catch (Exception e) {
                    result.addFailedImport(film, e.getMessage());
                }
            }
            
            conn.commit();
            
        } catch (Exception e) {
            throw new Exception("Import failed: " + e.getMessage(), e);
        }
        
        return result;
    }
    
    /**
     * Reads and parses film data from a CSV file, converting each row into a Film object.
     * The method expects a specific CSV format and validates the data during parsing.
     * 
     * <p>CSV Format Requirements:
     * <ul>
     *   <li>First row must be a header row (automatically skipped)</li>
     *   <li>Each data row must contain exactly 4 columns in the order:
     *       title, description, releaseYear, genre</li>
     *   <li>Fields may be quoted or unquoted</li>
     *   <li>Release year must be a valid integer</li>
     * </ul>
     * 
     * <p>Data Validation:
     * <ul>
     *   <li>Trims whitespace from all fields</li>
     *   <li>Validates row length (must be at least 4 columns)</li>
     *   <li>Parses release year as integer</li>
     * </ul>
     * 
     * @param filePath	The path to the CSV file to read. Must be a valid file path
     *                 	with read permissions.
     * @return List of Film objects created from the CSV data. Empty list if no
     *         valid data rows are found.
     * @throws Exception If there is an error reading or parsing the CSV file.
     */
    private List<Film> readFilmsFromCSV(String filePath) throws Exception {
        List<Film> films = new ArrayList<>();
        
        try (CSVReader reader = new CSVReaderBuilder(new FileReader(filePath))
                .withSkipLines(1) // Skip header
                .build()) {
                
            List<String[]> rows = reader.readAll();
            for (String[] row : rows) {
                if (row.length >= 4) {
                    Film film = new Film(
                        row[0].trim(),
                        row[1].trim(),
                        Integer.parseInt(row[2].trim()),
                        row[3].trim()
                    );
                    films.add(film);
                }
            }
        }
        
        return films;
    }
}
