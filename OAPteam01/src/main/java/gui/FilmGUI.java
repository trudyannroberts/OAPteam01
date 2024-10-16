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

    }
}
