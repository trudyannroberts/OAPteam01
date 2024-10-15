package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import logic.Film;
import logic.FilmManager;

/**
 * The BrowseMoviesPage class extends HomePageGUI and displays a custom page for browsing movies.
 * It includes functionality to show all movies, search movies by title, and filter by genre.
 * 
 * @author Stine Andreassen Skrøder 
 */
public class BrowseMoviesPage extends HomePageGUI {

    private FilmManager filmManager = new FilmManager();
    private JTable movieTable;
    private DefaultTableModel tableModel;

    /**
     * Constructs a BrowseMoviesPage object and displays the browse page.
     */
    public BrowseMoviesPage() {
        super();
        showBrowsePage();
    }

    /**
     * Overrides the showBrowsePage method to display the custom browse page.
     * The page includes a search bar, genre filter, and a table displaying movies.
     */
    @Override
    protected void showBrowsePage() {
        contentPanel.removeAll();

        JLabel browseLabel = new JLabel("Browse Movies", SwingConstants.CENTER);
        browseLabel.setForeground(Color.BLACK);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));
        topPanel.setBackground(new Color(240, 248, 255));

        JButton showAllButton = new JButton("Show All Films");
        showAllButton.addActionListener(e -> loadAllFilms());
        topPanel.add(showAllButton);

        JTextField searchBar = new JTextField();
        searchBar.setPreferredSize(new Dimension(400, 25));
        topPanel.add(searchBar);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> searchFilms(searchBar.getText()));
        topPanel.add(searchButton);

        String[] genres = {"All", "Action", "Animation", "Children", "Classics", "Comedy", "Documentary",
                           "Drama", "Family", "Foreign", "Games", "Horror", "Music", "New", "Sci-Fi",
                           "Sports", "Travel"};

        JComboBox<String> genreComboBox = new JComboBox<>(genres);
        topPanel.add(genreComboBox);

        genreComboBox.addActionListener(e -> {
            String selectedGenre = (String) genreComboBox.getSelectedItem();
            if ("All".equals(selectedGenre)) {
                loadAllFilms();
            } else {
                loadFilmsByGenre(selectedGenre);
            }
        });

        String[] columnNames = {"Title", "Description", "Release Year", "Genre"};
        tableModel = new DefaultTableModel(columnNames, 0);
        movieTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(movieTable);

        loadAllFilms();

        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(browseLabel, BorderLayout.NORTH);
        contentPanel.add(topPanel, BorderLayout.CENTER);
        contentPanel.add(scrollPane, BorderLayout.SOUTH);

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    /**
     * Loads all films from the database and displays them in the JTable.
     */
    private void loadAllFilms() {
        List<Film> films = filmManager.getAllFilms();
        tableModel.setRowCount(0);
        for (Film film : films) {
            Object[] row = {film.getTitle(), film.getDesc(), film.getReleaseYear(), film.getGenre()};
            tableModel.addRow(row);
        }
    }

    /**
     * Searches for films based on the given title and displays them in the JTable.
     *
     * @param searchQuery The title or partial title of the film to search for.
     */
    private void searchFilms(String searchQuery) {
        List<Film> films = filmManager.getAllFilms();
        tableModel.setRowCount(0);
        for (Film film : films) {
            if (film.getTitle().toLowerCase().contains(searchQuery.toLowerCase())) {
                Object[] row = {film.getTitle(), film.getDesc(), film.getReleaseYear(), film.getGenre()};
                tableModel.addRow(row);
            }
        }
    }

    /**
     * Loads films by the selected genre and displays them in the JTable.
     *
     * @param genre The genre of films to load.
     */
    private void loadFilmsByGenre(String genre) {
        List<Film> films = filmManager.getFilmByGenre(genre);
        tableModel.setRowCount(0);
        for (Film film : films) {
            Object[] row = {film.getTitle(), film.getDesc(), film.getReleaseYear(), film.getGenre()};
            tableModel.addRow(row);
        }
    }

    /**
     * The main method to launch the BrowseMoviesPage.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new BrowseMoviesPage();
        });
    }
}
