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
 * Service class responsible for importing films from CSV files into the database.
 * Handles the reading of CSV files, film data validation, and database operations.
 */
public class FilmImportService {
    
    /**
     * Imports films from a CSV file into the database.
     * Reads the CSV file, processes each row, and attempts to insert the films
     * into the database. Tracks successful and failed imports.
     * 
     * @param filePath The path to the CSV file containing film data
     * @return ImportResult containing information about successful and failed imports
     * @throws Exception If there is an error reading the file or accessing the database
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
     * Reads film data from a CSV file and converts it to Film objects.
     * Skips the header row and processes each subsequent row as a film entry.
     * 
     * @param filePath The path to the CSV file to read
     * @return List of Film objects created from the CSV data
     * @throws Exception If there is an error reading or parsing the CSV file
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
