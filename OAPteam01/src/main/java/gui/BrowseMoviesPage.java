package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import film.Film;
import film.FilmManager;

/**
 * BrowseMoviesPage is a GUI class that provides a user interface for browsing and searching for movies.
 * It inherits the layout and navigation of HomePageGUI.
 * Allows users to search by title, genre, and release year.
 * 
 * @Author: Stine Andreassen Skrøder
 */
public class BrowseMoviesPage extends HomePageGUI {
    public static JTextField searchBar;
    public static JButton searchButton;
    public static JButton showAllButton;
    public static JTable movieTable;
    public FilmManager filmManager;
    private JComboBox<String> searchTypeComboBox;

    public BrowseMoviesPage(HomePageGUI parent) {
        // Copy the state from the parent
        this.setTitle("Browse Movies");
        this.setSize(parent.getSize());
        this.setLocation(parent.getLocation());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Customize the content for BrowseMoviesPage
        initializeBrowseMoviesPanel();

        // Initialize FilmManager
        filmManager = new FilmManager();
        
     // Connect action listeners
        searchButton.addActionListener(e -> performSearch());
        showAllButton.addActionListener(e -> showAllMovies());
        
        showAllMovies();
        

        // Make this frame visible and dispose the parent
        this.setVisible(true);
        parent.dispose();
    }

    /**
     * Initializes the panel with search fields, buttons, and the movie table.
     */
    private void initializeBrowseMoviesPanel() {
        contentPanel.removeAll();
        
        // Create a panel for the search controls
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new GridLayout(2, 1));
        
        

        //Buttons panel
        JPanel searchBarPanel = new JPanel(new FlowLayout());
        
     // Add search type combo box
        String[] searchTypes = {"Title", "Genre", "Year"};
        searchTypeComboBox = new JComboBox<>(searchTypes);
        searchBarPanel.add(new JLabel("Search by: "));
        searchBarPanel.add(searchTypeComboBox);
        
        //Search bar
        searchBar = new JTextField(20);
        searchBarPanel.add(new JLabel("Search: "));
        searchBarPanel.add(searchBar);
     

        // Create search and show all buttons
        searchButton = new JButton("Search");
        showAllButton = new JButton("Show All Movies");

        // Add the buttons to the panel
        searchBarPanel.add(searchButton);
        searchBarPanel.add(showAllButton);

        searchPanel.add(searchBarPanel);

        // Create a table to display movie data
        String[] columnNames = {"Title", "Release Year", "Genre", "Description"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        movieTable = new JTable(tableModel);

        // Set the content panel layout and add components
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(searchPanel, BorderLayout.NORTH);
        contentPanel.add(new JScrollPane(movieTable), BorderLayout.CENTER);
        
     /**
      * @Author Erica Laub Varpe 
      * 
      */
        // MouseListener to be able to click on the film you want to watch
        BrowseMoviesPage.movieTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = BrowseMoviesPage.movieTable.getSelectedRow();
                if (row != -1) {
                    String filmTitle = BrowseMoviesPage.movieTable.getValueAt(row, 0).toString(); // "Title" is at index 0

                    int choice = JOptionPane.showConfirmDialog(
                        null,
                        "Do you want to watch " + filmTitle + "?",
                        "Confirm",
                        JOptionPane.YES_NO_OPTION
                    );

                    if (choice == JOptionPane.YES_OPTION) {
                        // Create and show the MoviePlayerUI with the selected film title
                        MoviePlayerUI moviePlayer = new MoviePlayerUI(filmTitle);
                        moviePlayer.setVisible(true);
                        
                        // Optionally, you might want to dispose of the current window
                        // if you don't want it to remain open while the movie is playing
                        // SwingUtilities.getWindowAncestor(BrowseMoviesPage.movieTable).dispose();
                    }
                }
            }
        });
        
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    /**
     * Performs a search based on the text in the search bar.
     */
    private void performSearch() {
        String searchText = searchBar.getText().trim();
        String searchType = (String) searchTypeComboBox.getSelectedItem();

        if (!searchText.isEmpty()) {
            List<Film> searchResults;
            switch (searchType) {
                case "Title":
                    searchResults = filmManager.getFilmByTitle(searchText);
                    break;
                case "Genre":
                    searchResults = filmManager.getFilmByGenre(searchText);
                    break;
                case "Year":
                    try {
                        int year = Integer.parseInt(searchText);
                        searchResults = filmManager.getFilmByReleaseYear(year);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(this, "Please enter a valid year.", "Invalid Year", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    break;
                default:
                    searchResults = new ArrayList<>();
            }
            updateMovieTable(searchResults);
            if (searchResults.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No films found for the given search criteria.", "No Results", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please enter a search term.", "Search Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showAllMovies() {
        List<Film> allFilms = filmManager.getAllFilms();
        updateMovieTable(allFilms);
    }

    private void updateMovieTable(List<Film> films) {
        DefaultTableModel model = (DefaultTableModel) movieTable.getModel();
        model.setRowCount(0); // Clear existing rows
        for (Film film : films) {
            model.addRow(new Object[]{film.getTitle(), film.getReleaseYear(), film.getGenre(), film.getDesc()});
        }
    }

    
}