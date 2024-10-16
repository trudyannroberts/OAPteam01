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
 * This class represents the graphical user interface (GUI) for interacting with films.
 * It allows the user to search for films by title, genre, or release year, and to display all films in the database.
 * The results are shown in a table within the GUI.
 * 
 * @author Erica Laub Varpe
 */
@SuppressWarnings("serial")
public class FilmGUI extends JFrame {
    
    private JTextField titleTextField; // Text field for searching by title
    private JTextField genreTextField; // Text field for searching by genre
    private JTextField yearTextField;  // Text field for searching by release year
    
    private JButton searchTitleButton; // Button for title search
    private JButton searchButton;      // Button for genre search
    private JButton searchYearButton;  // Button for release year search
    private JButton showAllButton;     // Button to display all films
    
    private JTable filmTable;          // Table to display film data
    private DefaultTableModel tableModel; // Model for the film table
    private FilmManager filmManager;   // Manages film-related operations

    /**
     * Constructs the FilmGUI and sets up the user interface components.
     */
    public FilmGUI() {
        filmManager = new FilmManager();
        
        // Input panel for search fields and buttons
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        // Title search components
        JLabel titleLabel = new JLabel("Enter part of a title:");
        titleTextField = new JTextField(15);
        searchTitleButton = new JButton("Search by Title");

        // Genre search components
        JLabel genreLabel = new JLabel("Enter a genre:");
        genreTextField = new JTextField(15);
        searchButton = new JButton("Search");
        showAllButton = new JButton("Show all movies");

        // Release year search components
        JLabel yearLabel = new JLabel("Enter release year:");
        yearTextField = new JTextField(10);
        searchYearButton = new JButton("Search by Year");

        // Adding components to the input panel
        inputPanel.add(titleLabel);
        inputPanel.add(titleTextField);
        inputPanel.add(searchTitleButton);
        inputPanel.add(genreLabel);
        inputPanel.add(genreTextField);
        inputPanel.add(searchButton);
        inputPanel.add(showAllButton);
        inputPanel.add(yearLabel);
        inputPanel.add(yearTextField);
        inputPanel.add(searchYearButton);

        add(inputPanel, BorderLayout.NORTH);

        // Table to display the results
        filmTable = new JTable();
        tableModel = new DefaultTableModel(new Object[]{"Title", "Description", "Release Year", "Genre"}, 0);
        filmTable.setModel(tableModel);
        JScrollPane scrollPane = new JScrollPane(filmTable);
        add(scrollPane, BorderLayout.CENTER);

        // Action listener for title search button
        // TODO flyttes til FilmManager
        searchTitleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleTextField.getText();
                if (!title.isEmpty()) {
                    updateFilmTableTitle(title);
                } else {
                    JOptionPane.showMessageDialog(null, "Title can't be empty", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Action listener for genre search button
     // TODO flyttes til FilmManager
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String genre = genreTextField.getText();
                if (!genre.isEmpty()) {
                    updateFilmTableGenre(genre);
                } else {
                    JOptionPane.showMessageDialog(null, "Genre can't be empty", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Action listener for release year search button
     // TODO flyttes til FilmManager
        searchYearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String yearText = yearTextField.getText();
                if (!yearText.isEmpty()) {
                    try {
                        int year = Integer.parseInt(yearText); // Convert input to an integer
                        updateFilmTableYear(year);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid year format", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Year can't be empty", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Action listener for "Show all movies" button
     // TODO flyttes til FilmManager
        showAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateFilmTableAll();
            }
        });
    }

    /**
     * Updates the film table with films that match the specified title.
     * 
     * @param title the title to search for
     */
 // TODO flyttes til FilmManager
    private void updateFilmTableTitle(String title) {
        tableModel.setRowCount(0); // Clear previous data
        List<Film> films = filmManager.getFilmByTitle(title); // Get films based on title
        for (Film film : films) {
            tableModel.addRow(new Object[]{film.getTitle(), film.getDesc(), film.getReleaseYear(), film.getGenre()});
        }
        if (films.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No films found with title: " + title, "No results", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Updates the film table with films that match the specified genre.
     * 
     * @param genre the genre to search for
     */
 // TODO flyttes til FilmManager
    private void updateFilmTableGenre(String genre) {
        tableModel.setRowCount(0); // Clear previous data
        List<Film> films = filmManager.getFilmByGenre(genre); // Get films based on genre
        for (Film film : films) {
            tableModel.addRow(new Object[]{film.getTitle(), film.getDesc(), film.getReleaseYear(), film.getGenre()});
        }
        if (films.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No films found in genre: " + genre, "No results", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Updates the film table with films that were released in the specified year.
     * 
     * @param year the release year to search for
     */
 // TODO flyttes til FilmManager
    private void updateFilmTableYear(int year) {
        tableModel.setRowCount(0); // Clear previous data
        List<Film> films = filmManager.getFilmByReleaseYear(year); // Get films based on release year
        for (Film film : films) {
            tableModel.addRow(new Object[]{film.getTitle(), film.getDesc(), film.getReleaseYear(), film.getGenre()});
        }
        if (films.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No films found from the year: " + year, "No results", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Updates the film table with all films from the database.
     */
 // TODO flyttes til FilmManager
    private void updateFilmTableAll() {
        tableModel.setRowCount(0); // Clear previous data
        List<Film> films = filmManager.getAllFilms(); // Get all films
        for (Film film : films) {
            tableModel.addRow(new Object[]{film.getTitle(), film.getDesc(), film.getReleaseYear(), film.getGenre()});
        }
        if (films.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No films found in the database", "No results", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
