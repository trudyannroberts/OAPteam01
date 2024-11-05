package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import film.Film;
import film.FilmHandler;
import film.FilmManager;

public class BrowseMoviesPage extends BaseGUI {
    private JTextField searchBar;
    private JButton searchButton;
    private JButton showAllButton;
    public static DefaultTableModel movieTable; // Changed to static DefaultTableModel
    private JComboBox<String> searchTypeComboBox;
    private FilmHandler filmHandler;

    public BrowseMoviesPage() {
        super("Browse Movies");
        filmHandler = new FilmManager();
        initializeBrowseMoviesPanel();
        initializeListeners();
        
        filmHandler.updateFilmTableAll();
        
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
        searchPanel.add(searchTypeComboBox);
        searchPanel.add(searchButton);
        searchPanel.add(showAllButton);

        browsePanel.add(searchPanel, BorderLayout.NORTH);
        
        String[] columnNames = {"Title", "Description", "Release Year", "Genre"};
        movieTable = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(movieTable);
        
        browsePanel.add(new JScrollPane(table), BorderLayout.CENTER);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    String filmTitle = table.getValueAt(row, 0).toString();
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
        
        updateContentPanel(browsePanel);
    }
        
    private void initializeListeners() {
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchText = searchBar.getText();
                String searchType = (String) searchTypeComboBox.getSelectedItem();
                
                if (!searchText.isEmpty()) {
                    switch (searchType) {
                        case "Title":
                            filmHandler.updateFilmTableTitle(searchText);
                            break;
                        case "Genre":
                            filmHandler.updateFilmTableGenre(searchText);
                            break;
                        case "Year":
                            try {
                                int year = Integer.parseInt(searchText);
                                filmHandler.updateFilmTableYear(year);
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(null, "Invalid year format", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            break;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Search text can't be empty", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        showAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filmHandler.updateFilmTableAll();
            }
        });
    }
}