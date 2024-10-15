package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import logic.FilmManager;
import logic.Film;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
/**
 * @author Erica Laub Varpe
 */
@SuppressWarnings("serial")
public class FilmGUI extends JFrame {
    private JTextField genreTextField;
    private JTextField yearTextField;
    private JButton searchButton;
    private JButton searchYearButton;
    private JButton showAllButton;
    private JTable filmTable;
    private DefaultTableModel tableModel;
    private FilmManager FilmManager;
    
    public FilmGUI() {
    	FilmManager = new FilmManager();
        
        //Input panel (genre and button)
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        
        JLabel genreLabel = new JLabel("Enter a genre:");
        genreTextField = new JTextField(15);
        searchButton = new JButton("Search");
        showAllButton = new JButton("Show all movies");
        
        // Add components for release year search
        JLabel yearLabel = new JLabel("Enter release year:");
        yearTextField = new JTextField(10);
        searchYearButton = new JButton("Search by Year");
        
        inputPanel.add(genreLabel);
        inputPanel.add(genreTextField);
        inputPanel.add(searchButton);
        inputPanel.add(showAllButton);
        
        inputPanel.add(yearLabel); // Add label for year
        inputPanel.add(yearTextField); // Add text field for year
        inputPanel.add(searchYearButton); // Add search by year button
        
        add(inputPanel, BorderLayout.NORTH);    
        
        //Table to show the results
        filmTable = new JTable();
        tableModel = new DefaultTableModel(new Object[] {"Title", "Description", "Release Year", "Genre"},0);
        filmTable.setModel(tableModel);
        JScrollPane scrollPane = new JScrollPane(filmTable);
        
        add(scrollPane, BorderLayout.CENTER);
        
        //action listener for search button
        searchButton.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		String genre = genreTextField.getText();
        		if(!genre.isEmpty()) {
        			updateFilmTableGenre(genre);
        		} else {
        			JOptionPane.showMessageDialog(null, "Genre can't be empty", "wrong", JOptionPane.ERROR_MESSAGE);
        		}
        	}
        }); 
        
    
    // Action listener for searchYearButton (by release year)
    searchYearButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String yearText = yearTextField.getText();
            if (!yearText.isEmpty()) {
                try {
                    int year = Integer.parseInt(yearText); // Convert year to an integer
                    updateFilmTableYear(year);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid year format", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Year can't be empty", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    });
    
    
    // Action listener for "Vis alle filmer" knappen
    showAllButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            updateFilmTableAll();
        }
    });
    }
    
    //Method to get films based on genre and update the table
    private void updateFilmTableGenre(String genre) {
        // Tøm tidligere data
        tableModel.setRowCount(0);

        // Hent filmer fra DAO basert på sjanger
        List<Film> films = FilmManager.getFilmByGenre(genre);

        // Legg til filmene i tabellen
        for (Film film : films) {
            tableModel.addRow(new Object[]{
                    film.getTitle(),
                    film.getDesc(),
                    film.getReleaseYear(),
                    film.getGenre()
            });
        }

        // Vis melding hvis ingen filmer ble funnet
        if (films.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No films found in genre: " + genre, "No results", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    // New method to get films based on release year and update the table
    private void updateFilmTableYear(int year) {
        tableModel.setRowCount(0); // Clear previous data
        List<Film> films = FilmManager.getFilmByReleaseYear(year); // Get films from DAO by release year

        for (Film film : films) {
            tableModel.addRow(new Object[]{
                    film.getTitle(),
                    film.getDesc(),
                    film.getReleaseYear(),
                    film.getGenre()
            });
        }

        if (films.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No films found from the year: " + year, "No results", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    // Ny metode for å hente alle filmer og oppdatere tabellen
    private void updateFilmTableAll() {
        // Tøm tidligere data
        tableModel.setRowCount(0);

        // Hent alle filmer fra DAO
        List<Film> films = FilmManager.getAllFilms();

        // Legg til filmene i tabellen
        for (Film film : films) {
            tableModel.addRow(new Object[]{
            		film.getTitle(),
                    film.getDesc(),
                    film.getReleaseYear(),
                    film.getGenre()
            });
        }

        // Vis melding hvis ingen filmer ble funnet
        if (films.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No films found in the database", "No results", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}