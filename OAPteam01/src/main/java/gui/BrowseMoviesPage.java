package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import film.Film;
import film.FilmHandler;
import film.FilmManager;

public class BrowseMoviesPage extends BaseGUI {
    private JTextField searchBar;
    private JButton searchButton;
    private JButton showAllButton;
    public JTable movieTable;
    private JComboBox<String> searchTypeComboBox;

    public BrowseMoviesPage() {
        super("Browse Movies");
        initializeBrowseMoviesPanel(); // Initialize UI components
        showAllMovies(); // Load all movies initially
        setVisible(true);
    }

    private void initializeBrowseMoviesPanel() {
        JPanel browsePanel = new JPanel(new BorderLayout());

        // Initialize UI components
        searchBar = new JTextField(20);
        searchButton = new JButton("Search");
        showAllButton = new JButton("Show All Movies");

        // Initialize search type combo box
        String[] searchTypes = {"Title", "Genre", "Year"};
        searchTypeComboBox = new JComboBox<>(searchTypes);

        // Create a panel for search bar and buttons
        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchBar);
        searchPanel.add(searchTypeComboBox); // Add combo box here
        searchPanel.add(searchButton);
        searchPanel.add(showAllButton);

        // Add action listeners after initialization
        searchButton.addActionListener(e -> performSearch());
        showAllButton.addActionListener(e -> showAllMovies());

        browsePanel.add(searchPanel, BorderLayout.NORTH);
        
        String[] columnNames = {"Title","Description", "Release Year", "Genre"};
        movieTable = new JTable(new DefaultTableModel(columnNames, 0));
        
        browsePanel.add(new JScrollPane(movieTable), BorderLayout.CENTER);

         /**
         * Opens movie player when you click on film title
         *
         * @author Erica Laub Varpe 
         */
         movieTable.addMouseListener(new MouseAdapter() {
             @Override
             public void mouseClicked(MouseEvent e) {
                 int row = movieTable.getSelectedRow();
                 if (row != -1) {
                     String filmTitle = movieTable.getValueAt(row, 0).toString();
                     int choice = JOptionPane.showConfirmDialog(
                         null,
                         "Do you want to watch " + filmTitle + "?",
                         "Confirm",
                         JOptionPane.YES_NO_OPTION
                     );
                     if (choice == JOptionPane.YES_OPTION) {
                         MoviePlayerUI moviePlayer = new MoviePlayerUI(filmTitle);
                         moviePlayer.setVisible(true);
                     }
                 }
             }
         });
        
         updateContentPanel(browsePanel); // Update content panel with browse panel
    }

    private void performSearch() {
         String searchText = searchBar.getText().trim();
         String searchType = (String) searchTypeComboBox.getSelectedItem();

         if (!searchText.isEmpty()) {
             switch (searchType) {
                 case "Title":
                     FilmHandler.updateFilmTableTitle(movieTable, searchText);
                     break;
                 case "Genre":
                	 FilmHandler.updateFilmTableGenre(movieTable, searchText);
                     break;
                 case "Year":
                     try {
                         int year = Integer.parseInt(searchText);
                         FilmHandler.updateFilmTableYear(movieTable, year);
                     } catch (NumberFormatException e) {
                         JOptionPane.showMessageDialog(this, "Please enter a valid year.", "Invalid Year", JOptionPane.ERROR_MESSAGE);
                     }
                     break;
                 default:
                     break;
             }
         } else {
             JOptionPane.showMessageDialog(this, "Please enter a search term.", "Search Error", JOptionPane.ERROR_MESSAGE);
         }
    }

    private void showAllMovies() {
    	FilmHandler.updateFilmTableAll(movieTable); // Fetch and display all films
    }
}