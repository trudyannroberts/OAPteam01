package gui;


import db.DatabaseConnection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.plaf.basic.BasicSliderUI;
import javax.swing.border.EmptyBorder;
import javax.swing.Timer;

/**
 * the movie player serves as a bare bones representation for how playing movies trough the application might look like
 *  
 *  @author August Monen
 *  
 *  */



public class MoviePlayerUI extends JFrame {

    private JButton playPauseButton;
    private JButton quitButton;
    private JButton skipBackButton;
    private JButton skipForwardButton;
    private JSlider volumeSlider;
    private JSlider progressSlider;
    private Timer movieTimer;
    private boolean isPlaying = false;
    private final int movieDuration = 600; // 10 minutes in seconds

    public MoviePlayerUI() {
        // Set up the frame with a medium-grey background
        setTitle("Movie Player");
        setSize(600, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(45, 45, 45));  // Medium grey background

        // Create buttons with text labels for visibility
        playPauseButton = new JButton("Play");
        styleButton(playPauseButton);

        quitButton = new JButton("Quit");
        styleButton(quitButton);

        skipBackButton = new JButton("Back 15s");
        styleButton(skipBackButton);

        skipForwardButton = new JButton("Forward 15s");
        styleButton(skipForwardButton);

        // Volume slider
        volumeSlider = new JSlider(0, 100, 50);
        volumeSlider.setUI(new BasicSliderUI(volumeSlider) {
            @Override
            public void paintThumb(Graphics g) {
                g.setColor(Color.WHITE);
                super.paintThumb(g);
            }
            @Override
            public void paintTrack(Graphics g) {
                g.setColor(Color.DARK_GRAY);
                super.paintTrack(g);
            }
        });
        volumeSlider.setBackground(new Color(45, 45, 45));

        // Progress slider
        progressSlider = new JSlider(0, movieDuration, 0); // 10 minutes duration (600 seconds)
        progressSlider.setBackground(new Color(45, 45, 45));
        progressSlider.setForeground(Color.WHITE);

        // Timer to simulate movie progress
        movieTimer = new Timer(1000, e -> {
            if (progressSlider.getValue() < movieDuration) {
                progressSlider.setValue(progressSlider.getValue() + 1);
            } else {
                movieTimer.stop();
                playPauseButton.setText("Play");
                isPlaying = false;
            }
        });

        // Button actions
        playPauseButton.addActionListener(e -> {
            if (isPlaying) {
                movieTimer.stop();
                playPauseButton.setText("Play");
            } else {
                movieTimer.start();
                playPauseButton.setText("Pause");
            }
            isPlaying = !isPlaying;
        });

        quitButton.addActionListener(e -> System.exit(0));

        skipBackButton.addActionListener(e -> {
            int newTime = Math.max(0, progressSlider.getValue() - 15);
            progressSlider.setValue(newTime);
            System.out.println("Skipped 15 seconds backward");
        });

        skipForwardButton.addActionListener(e -> {
            int newTime = Math.min(movieDuration, progressSlider.getValue() + 15);
            progressSlider.setValue(newTime);
            System.out.println("Skipped 15 seconds forward");
        });

        volumeSlider.addChangeListener(e -> System.out.println("Volume set to: " + volumeSlider.getValue()));

        progressSlider.addChangeListener(e -> System.out.println("Progress set to: " + progressSlider.getValue() + " seconds"));

        // Layout
        JPanel controlPanel = new JPanel();
        controlPanel.setBackground(Color.DARK_GRAY);
        controlPanel.setLayout(new GridBagLayout());
        controlPanel.setBorder(new EmptyBorder(20, 0, 20, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 10, 0, 10);

        // Add components to control panel
        gbc.gridx = 0;
        controlPanel.add(skipBackButton, gbc);

        gbc.gridx = 1;
        controlPanel.add(playPauseButton, gbc);

        gbc.gridx = 2;
        controlPanel.add(skipForwardButton, gbc);

        gbc.gridx = 3;
        controlPanel.add(new JLabel("Volume"), gbc);

        gbc.gridx = 4;
        controlPanel.add(volumeSlider, gbc);

        gbc.gridx = 5;
        controlPanel.add(quitButton, gbc);

        // Layout for the progress bar
        JPanel progressPanel = new JPanel();
        progressPanel.setBackground(new Color(45, 45, 45));
        progressPanel.setLayout(new BorderLayout());
        progressPanel.setBorder(new EmptyBorder(10, 20, 10, 20));
        progressPanel.add(progressSlider, BorderLayout.CENTER);

        add(progressPanel, BorderLayout.NORTH);
        add(controlPanel, BorderLayout.SOUTH);
    }

    // Method to style buttons consistently
    private void styleButton(JButton button) {
        button.setBackground(Color.DARK_GRAY); // Dark grey background
        button.setForeground(Color.WHITE);     // White text
        button.setBorderPainted(false);        // Remove border
        button.setFocusPainted(false);         // Remove focus outline
    }

    public static void main(String[] args) {
        // Suppress IMKClient warning on macOS
        System.setProperty("apple.awt.UIElement", "true");

        SwingUtilities.invokeLater(() -> {
            MoviePlayerUI playerUI = new MoviePlayerUI();
            playerUI.setVisible(true);
        });
    }
}