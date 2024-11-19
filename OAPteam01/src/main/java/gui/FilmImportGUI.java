package gui;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import film.FilmImportService;
import film.ImportResult;

import java.awt.*;
import java.io.File;

/**
 * A graphical user interface for importing films from CSV files.
 * The GUI provides functionality to select CSV files, import film data, and view import results.
 * CSV files should follow the format: "title","description",release_year,"genre"
 *
 * @author Erica Laub Varpe
 */
@SuppressWarnings("serial")
public class FilmImportGUI extends BaseGUI {
    private FilmImportService importService; // Service handling the film import operations.
    private JLabel statusLabel; // Label showing the current status of file selection and import.
    private JTextArea logArea; // Text area for displaing import logs and progress.
    private File selectedFile; // Currently selected file for import.
    private static final String FILES_DIRECTORY = "resources"; // Directory for storing resource files.
    
    /**
     * Constructs a new FilmImportGUI.
     * Initializes the import service, creates the resources directory if needed,
     * and sets up the GUI components.
     */
    public FilmImportGUI() {
        super("Import Films");
        importService = new FilmImportService();
        createFilesDirectory();
        setupComponents();
    }
    
    /**
     * Creates the resources directory if it doesn't exist.
     * Prints an error message if directory creation fails.
     */
    private void createFilesDirectory() {
        File filesDir = new File(FILES_DIRECTORY);
        if (!filesDir.exists()) {
            boolean created = filesDir.mkdir();
            if (!created) {
                System.err.println("Could not create 'resources' folder");
            }
        }
    }
    
    /**
     * Sets up all GUI components including file chooser, import button,
     * status label, and log area. Configures layout and event listeners
     * for handling file selection and import operations.
     */
    private void setupComponents() {
        // Main panel with padding
    	JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Top panel for instructions
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel instructionsLabel = new JLabel(
            "<html>Import films from a CSV-file<br>" +
            "Format: \"title\",\"description\",release_year,\"genre\"<br>" +
            "CSV-files are located in the resources folder</html>"
        );
        topPanel.add(instructionsLabel, BorderLayout.CENTER);
        
        // Center panel for controls
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        
        JButton chooseFileButton = new JButton("Choose CSV-file");
        chooseFileButton.setToolTipText("Opens up the resource folder located in the project by default");
        
        statusLabel = new JLabel("No file is chosen");
        JButton importButton = new JButton("Import");
        importButton.setEnabled(false);
        
        centerPanel.add(chooseFileButton);
        centerPanel.add(statusLabel);
        centerPanel.add(importButton);
        
        // Bottom panel for log
        logArea = new JTextArea(10, 50);
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Import log"));
        
        // Add action listeners
        chooseFileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            // Start i 'filer' mappen
            File filesDir = new File(FILES_DIRECTORY);
            if (filesDir.exists()) {
                fileChooser.setCurrentDirectory(filesDir);
            }
            
            FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV files", "csv");
            fileChooser.setFileFilter(filter);
            
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();
                statusLabel.setText("Chosen file: " + selectedFile.getName());
                importButton.setEnabled(true);
                
                // Hvis filen ikke er i 'filer' mappen, tilby å kopiere den dit
                if (!selectedFile.getParentFile().getAbsolutePath().equals(new File(FILES_DIRECTORY).getAbsolutePath())) {
                    int response = JOptionPane.showConfirmDialog(this,
                        "Do you want to copy this file to the 'resources' folder?",
                        "Copy file",
                        JOptionPane.YES_NO_OPTION);
                        
                    if (response == JOptionPane.YES_OPTION) {
                        try {
                            File newFile = new File(FILES_DIRECTORY + File.separator + selectedFile.getName());
                            java.nio.file.Files.copy(
                                selectedFile.toPath(),
                                newFile.toPath(),
                                java.nio.file.StandardCopyOption.REPLACE_EXISTING
                            );
                            selectedFile = newFile;
                            statusLabel.setText("Chosen file: " + selectedFile.getName() + " (copied to 'resources' folder)");
                        } catch (Exception ex) {
                            logArea.append("Could not import file: " + ex.getMessage() + "\n");
                        }
                    }
                }
            }
        });
        
        importButton.addActionListener(e -> {
            if (selectedFile != null) {
                logArea.setText("Starting import...\n");
                importButton.setEnabled(false);
                
                new SwingWorker<ImportResult, String>() {
                    @Override
                    protected ImportResult doInBackground() throws Exception {
                        publish("Starting import from " + selectedFile.getName());
                        return importService.importFilmsFromCSV(selectedFile.getAbsolutePath());
                    }

                    @Override
                    protected void process(java.util.List<String> chunks) {
                        for (String message : chunks) {
                            logArea.append(message + "\n");
                        }
                    }

                    @Override
                    protected void done() {
                        importButton.setEnabled(true);
                        try {
                            ImportResult result = get();
                            logArea.append("Import completed!\n");
                            logArea.append("Successfully imported: " + result.getTotalSuccessful() + " films\n");
                            logArea.append("Failed to import: " + result.getTotalFailed() + " films\n");
                            
                            // Show the summary dialog
                            ImportSummaryDialog summaryDialog = new ImportSummaryDialog(FilmImportGUI.this, result);
                            summaryDialog.setVisible(true);
                            
                        } catch (Exception ex) {
                            logArea.append("An error occurred during the import.\n");
                            JOptionPane.showMessageDialog(FilmImportGUI.this,
                                "An error occurred: " + ex.getMessage(),
                                "Import Failed",
                                JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }.execute();
            } else {
                logArea.append("No file is chosen!\n");
            }
        });
        
        // Add components to main panel
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);
        
        // Add main panel to contentPanel instead of directly to the frame
        contentPanel.add(mainPanel, BorderLayout.CENTER);
        
        // Refresh the display
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}
