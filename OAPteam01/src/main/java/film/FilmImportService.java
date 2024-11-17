package film;

import db.FilmDAO;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import db.DatabaseConnection;

import java.io.FileReader;
import java.sql.Connection;
import java.util.List;
import java.util.ArrayList;

public class FilmImportService {
    
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
