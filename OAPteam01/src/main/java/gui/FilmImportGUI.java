package gui;
/**
 * @author Erica Laub Varpe
 */
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import film.FilmImport;

import java.awt.*;
import java.io.File;

@SuppressWarnings("serial")
public class FilmImportGUI extends BaseGUI {
    private FilmImport filmImport;
    private JLabel statusLabel;
    private JTextArea logArea;
    private File selectedFile;
    private static final String FILES_DIRECTORY = "filer"; // Konstant for filmappen
    
    public FilmImportGUI() {
        super("Import films");
        filmImport = new FilmImport();
        createFilesDirectory(); // Opprett filmappen hvis den ikke eksisterer
        setupComponents();
    }
    
    private void createFilesDirectory() {
        File filesDir = new File(FILES_DIRECTORY);
        if (!filesDir.exists()) {
            boolean created = filesDir.mkdir();
            if (!created) {
                System.err.println("Kunne ikke opprette 'filer' mappe");
            }
        }
    }
    
    private void setupComponents() {
        // Main panel with padding
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Top panel for instructions
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel instructionsLabel = new JLabel(
            "<html>Importer filmer fra CSV-fil<br>" +
            "Format: \"title\",\"description\",release_year<br>" +
            "CSV-filer bør plasseres i 'filer' mappen</html>"
        );
        topPanel.add(instructionsLabel, BorderLayout.CENTER);
        
        // Center panel for controls
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JButton chooseFileButton = new JButton("Velg CSV-fil");
        statusLabel = new JLabel("Ingen fil valgt");
        JButton importButton = new JButton("Importer");
        importButton.setEnabled(false);
        
        centerPanel.add(chooseFileButton);
        centerPanel.add(statusLabel);
        centerPanel.add(importButton);
        
        // Bottom panel for log
        logArea = new JTextArea(10, 50);
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Import logg"));
        
        // Add action listeners
        chooseFileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            // Start i 'filer' mappen
            File filesDir = new File(FILES_DIRECTORY);
            if (filesDir.exists()) {
                fileChooser.setCurrentDirectory(filesDir);
            }
            
            FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV filer", "csv");
            fileChooser.setFileFilter(filter);
            
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();
                statusLabel.setText("Valgt fil: " + selectedFile.getName());
                importButton.setEnabled(true);
                
                // Hvis filen ikke er i 'filer' mappen, tilby å kopiere den dit
                if (!selectedFile.getParentFile().getAbsolutePath().equals(new File(FILES_DIRECTORY).getAbsolutePath())) {
                    int response = JOptionPane.showConfirmDialog(this,
                        "Vil du kopiere filen til 'filer' mappen?",
                        "Kopier fil",
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
                            statusLabel.setText("Valgt fil: " + selectedFile.getName() + " (kopiert til 'filer' mappen)");
                        } catch (Exception ex) {
                            logArea.append("Kunne ikke kopiere filen: " + ex.getMessage() + "\n");
                        }
                    }
                }
            }
        });
        
        importButton.addActionListener(e -> {
            if (selectedFile != null) {
                logArea.setText("Starter import...\n");
                importButton.setEnabled(false);
                
                new SwingWorker<Void, String>() {
                    @Override
                    protected Void doInBackground() {
                        try {
                            filmImport.importFilmsFromCSV(selectedFile.getAbsolutePath());
                        } catch (Exception ex) {
                            publish("Feil under import: " + ex.getMessage());
                        }
                        return null;
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
                    }
                }.execute();
            } else {
                logArea.append("Ingen fil er valgt!\n");
            }
        });
        
        // Add components to main panel
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);
        
        // Add main panel to frame
        add(mainPanel);
    }
}
