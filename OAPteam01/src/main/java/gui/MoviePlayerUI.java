package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter; 
import java.awt.event.WindowEvent;  
import javax.swing.plaf.basic.BasicSliderUI;
import javax.swing.border.EmptyBorder;
import javax.swing.Timer;



/**
 * The MoviePlayerUI serves as a bare bones representation for how playing movies through the application might look like.
 * 
 * @author August Monen
 */
public class MoviePlayerUI extends JFrame {

    private JButton playPauseButton;
    private JButton quitButton;
    private JButton skipBackButton;
    private JButton skipForwardButton;
    private JSlider volumeSlider;
    private JSlider progressSlider;
    private Timer movieTimer;
    private boolean isPlaying = false;
    private final int movieDuration = 600; // Duration in seconds
    private String currentFilmTitle;

    // Constructor that initializes the movie player UI with the film title
    public MoviePlayerUI(String filmTitle) {
        this.currentFilmTitle = filmTitle;

        // Set up the frame
        setTitle("Movie Player");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(45, 45, 45)); // Medium grey background
        setLocationRelativeTo(null); //centers the window
        
     // Add window listener to handle closing event
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                confirmAndOpenReviewGUI(); // Ask for confirmation before opening Review GUI
            }
        });

        // Create buttons with text labels for visibility
        playPauseButton = new JButton("Play");
        styleButton(playPauseButton);

        quitButton = new JButton("Quit");
        styleButton(quitButton);

        skipBackButton = new JButton("Back 15s");
        styleButton(skipBackButton);

        skipForwardButton = new JButton("Forward 15s");
        styleButton(skipForwardButton);

        // Volume slider setup
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

        // Progress slider setup
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

        // Button actions setup
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

        quitButton.addActionListener(e -> {
            confirmAndOpenReviewGUI(); // Ask for confirmation before opening Review GUI on Quit button click
            dispose(); // Close the Movie Player UI
        });

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

        // Layout setup for control panel
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
         progressPanel.add(progressSlider, BorderLayout.CENTER);

         add(progressPanel, BorderLayout.NORTH);
         add(controlPanel, BorderLayout.SOUTH);

         setVisible(true); // Make the frame visible after all components are added
     }

 // Method to confirm and open Review GUI
    private void confirmAndOpenReviewGUI() {
        int response = JOptionPane.showConfirmDialog(this,
                "Do you want to rate \"" + currentFilmTitle + "\"?",
                "Rate Movie",
                JOptionPane.YES_NO_OPTION);

        if (response == JOptionPane.YES_OPTION) {
            openReviewGUI(); // Open Review GUI if user agrees
        }
    }
    
 // Method to open Review GUI
    private void openReviewGUI() {
        SwingUtilities.invokeLater(() -> {
            new ReviewGUI(currentFilmTitle); // Open Review GUI with current film title
        });
    }

     // Method to style buttons consistently
     private void styleButton(JButton button) {
         button.setBackground(Color.DARK_GRAY); // Dark grey background
         button.setForeground(Color.WHITE);     // White text
         button.setBorderPainted(false);       // Remove border
         button.setFocusPainted(false);         // Remove focus outline
     }
}