package gui;

import film.ImportResult;
import film.Film;
import javax.swing.*;
import java.awt.*;

/**
 * A dialog window that displays a summary of film import results.
 * Shows both successful and failed imports in a tabbed interface with detailed information
 * about each imported film.
 * @author Erica Laub Varpe
 */
@SuppressWarnings("serial")
public class ImportSummaryDialog extends JDialog {
    
    /**
     * Creates a new import summary dialog.
     * 
     * @param parent The parent frame that owns this dialog
     * @param result The ImportResult containing data about successful and failed imports
     */
    public ImportSummaryDialog(Frame parent, ImportResult result) {
        super(parent, "Import Summary", true);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Summary panel
        JPanel summaryPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        summaryPanel.add(new JLabel("Successfully imported: " + result.getTotalSuccessful() + " films"));
        summaryPanel.add(new JLabel("Failed to import: " + result.getTotalFailed() + " films"));
        
        // Create tabs
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Successful imports tab
        JTextArea successArea = new JTextArea(15, 40);
        successArea.setEditable(false);
        for (Film film : result.getSuccessfulImports()) {
            successArea.append(String.format("✓ %s (%d) - %s\n", 
                film.getTitle(), film.getReleaseYear(), film.getGenre()));
        }
        tabbedPane.addTab("Successful Imports", new JScrollPane(successArea));
        
        // Failed imports tab
        if (!result.getFailedImports().isEmpty()) {
            JTextArea failedArea = new JTextArea(15, 40);
            failedArea.setEditable(false);
            for (Film film : result.getFailedImports()) {
                failedArea.append(String.format("⨯ %s (%d) - %s\nReason: %s\n\n", 
                    film.getTitle(), film.getReleaseYear(), film.getGenre(), 
                    film.getFailureReason()));
            }
            tabbedPane.addTab("Failed Imports", new JScrollPane(failedArea));
        }
        
        // OK button
        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> dispose());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(okButton);
        
        mainPanel.add(summaryPanel, BorderLayout.NORTH);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        setContentPane(mainPanel);
        pack();
        setLocationRelativeTo(parent);
    }
}
