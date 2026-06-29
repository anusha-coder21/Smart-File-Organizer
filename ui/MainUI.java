package ui;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import logic.FileOrganizer;

public class MainUI {

    private Map<String, String> moveHistory = new LinkedHashMap<>();

    public void createWindow() {

        JFrame frame = new JFrame("Smart File Organizer");
        frame.setSize(900, 650);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new java.awt.BorderLayout());

        JLabel title = new JLabel("SMART FILE ORGANIZER", JLabel.CENTER);
        title.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 24));

        frame.add(title, java.awt.BorderLayout.NORTH);

        JButton selectButton = new JButton("Select Folder");
        JButton undoButton = new JButton("Undo");

        JLabel pathLabel = new JLabel("No folder selected");
        JLabel statsLabel = new JLabel("Stats");

        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> fileList = new JList<>(listModel);

        JScrollPane scrollPane = new JScrollPane(fileList);

        JPanel topPanel = new JPanel();
        topPanel.add(selectButton);
        topPanel.add(undoButton);
        topPanel.add(pathLabel);

        frame.add(topPanel, java.awt.BorderLayout.NORTH);
        frame.add(scrollPane, java.awt.BorderLayout.CENTER);
        frame.add(statsLabel, java.awt.BorderLayout.SOUTH);

        JDialog loading = new JDialog(frame, "Processing", true);
        loading.setSize(250, 100);
        loading.add(new JLabel("Organizing files...", JLabel.CENTER));

        // ---------------- SELECT BUTTON ----------------
        selectButton.addActionListener(e -> {

            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {

                File folder = chooser.getSelectedFile();
                pathLabel.setText(folder.getAbsolutePath());

                listModel.clear();
                moveHistory.clear();

                List<String> output = new java.util.ArrayList<>();

                SwingWorker<Void, Void> worker = new SwingWorker<>() {

                    @Override
                    protected Void doInBackground() {
                        loading.setVisible(true);
                        moveHistory = FileOrganizer.organize(folder, output);
                        return null;
                    }

                    @Override
                    protected void done() {

                        loading.dispose();

                        for (String line : output) {
                            listModel.addElement(line);
                        }

                        statsLabel.setText("Files organized: " + output.size());
                    }
                };

                worker.execute();
            }
        });

        // ---------------- UNDO BUTTON ----------------
        undoButton.addActionListener(e -> {

            if (moveHistory.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Nothing to undo!");
                return;
            }

            FileOrganizer.undo(moveHistory);
            listModel.clear();
            statsLabel.setText("Undo completed");

            JOptionPane.showMessageDialog(frame, "Undo successful!");
        });

        frame.setVisible(true);
    }
}