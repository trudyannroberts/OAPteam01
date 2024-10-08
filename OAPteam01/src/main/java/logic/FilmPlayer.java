package logic;

import db.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FilmPlayer extends JFrame {
    private JComboBox<String> filmDropdown; // Dropdown for film selection
    private JTextArea outputArea; // Area to display the current status
    private JButton playButton; // Button to play the film
    private JButton pauseButton; // Button to pause the film
    private JButton stopButton; // Button to stop the film
    private JSlider volumeSlider; // Slider for volume control

    // Database configuration
    private static final String DB_URL = "jdbc:mysql://localhost:3306/your_database_name"; // Update with your db name
    private static final String USER = "your_username"; // Your db username
    private static final String PASS = "your_password"; // Your db password

    public FilmPlayer() {
        setTitle("Film Player");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        filmDropdown = new JComboBox<>();
        outputArea = new JTextArea(10, 50);
        outputArea.setEditable(false); // Make the output area non-editable
        playButton = new JButton("Play");
        pauseButton = new JButton("Pause");
        stopButton = new JButton("Stop");
        volumeSlider = new JSlider(0, 100, 50); // Volume slider from 0 to 100
        volumeSlider.setMajorTickSpacing(20);
        volumeSlider.setPaintTicks(true);
        volumeSlider.setPaintLabels(true);

        // Control panel for buttons and volume slider
        JPanel controlPanel = new JPanel();
        controlPanel.add(filmDropdown);
        controlPanel.add(playButton);
        controlPanel.add(pauseButton);
        controlPanel.add(stopButton);
        controlPanel.add(new JLabel("Volume: "));
        controlPanel.add(volumeSlider);

        add(controlPanel, BorderLayout.SOUTH);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        loadFilmsIntoDropdown(); // Load films when the application starts
        initializeActionListeners(); // Set up button action listeners
    }

    // Load films from the db into the dropdown
    private void loadFilmsIntoDropdown() {
        List<String> films = getAvailableFilms(); // Fetch films from the db
        for (String film : films) {
            filmDropdown.addItem(film); // Add each film to the dropdown
        }
    }

    // Method to get available films from the db
    private List<String> getAvailableFilms() {
        List<String> films = new ArrayList<>();
        String query = "SELECT film_title FROM films"; // SQL query to get film titles

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                films.add(resultSet.getString("film_title")); // Add each film title to the list
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        }

        return films; // Return the list of film titles
    }

    // Initialize action listeners for buttons
    private void initializeActionListeners() {
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedFilm = (String) filmDropdown.getSelectedItem();
                outputArea.setText("Playing: " + selectedFilm); // Update output area
                // Logic to play the film would go here
            }
        });

        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedFilm = (String) filmDropdown.getSelectedItem();
                outputArea.setText("Paused: " + selectedFilm); // Update output area
                // Logic to pause the film would go here
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedFilm = (String) filmDropdown.getSelectedItem();
                outputArea.setText("Stopped: " + selectedFilm); // Update output area
                // Logic to stop the film would go here
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FilmPlayer player = new FilmPlayer();
            player.setVisible(true); // Display the FilmPlayer GUI
        });
    }
    
    /**
     * 
     * @return the selected film
     */
    public String getSelectedFilm() {
        return (String) filmDropdown.getSelectedItem(); // Return the selected film title
    }
}
