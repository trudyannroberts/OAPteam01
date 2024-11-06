package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import film.Film;
import film.FilmHandler;
import film.FilmManager;
import review.ReviewManager;

public class BrowseMoviesPage extends BaseGUI {
    private JTextField searchBar;
    private JButton searchButton;
    private JButton showAllButton;
    private JButton showAllReviewsButton;
    public static DefaultTableModel movieTable;
    private JComboBox<String> searchTypeComboBox;
    private FilmHandler filmHandler;
	private ReviewManager reviewManager;

    public BrowseMoviesPage() {
        super("Browse Movies");
        filmHandler = new FilmManager();
        this.reviewManager = new ReviewManager();
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
        showAllReviewsButton = new JButton("Show All Reviews");

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
        searchPanel.add(showAllReviewsButton);

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
        
        showAllReviewsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadAllReviews();
            }
        });
    }

    private void loadAllReviews() {
        try {
            List<String> reviews = reviewManager.loadAllReviews();
            
            if (reviews.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No reviews available.", "Reviews", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            JTextArea reviewsArea = new JTextArea(20, 40);
            reviewsArea.setEditable(false);
            
            for (String review : reviews) {
                reviewsArea.append(review + "\n\n");
            }
            
            JScrollPane scrollPane = new JScrollPane(reviewsArea);
            
            JDialog dialog = new JDialog(this, "All Reviews", true);
            dialog.setLayout(new BorderLayout());
            dialog.add(scrollPane, BorderLayout.CENTER);
            dialog.pack();
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading reviews: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    
    }
}