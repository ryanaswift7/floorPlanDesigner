import Panels.CanvasPanel;
import Panels.RightPanel;
import Panels.TopPanel;
import Utils.FileHandler;

import javax.swing.*;
import java.awt.*;

public class FloorPlanDesigner extends JFrame {

    private CanvasPanel canvasPanel;
    private TopPanel topPanel;
    private RightPanel rightPanel;

    public FloorPlanDesigner() {
        super("Interactive Floor Plan Designer");
        initComponents();
        initUI();
    }

    private void initComponents() {
        canvasPanel = CanvasPanel.getInstance();
        topPanel = TopPanel.getInstance();
        rightPanel = RightPanel.getInstance();
    }

    private void initUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        add(canvasPanel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);
        add(rightPanel, BorderLayout.EAST);
        setupMenuBar();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setupMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        // Setup menu bar
        // File Menu
        JMenu fileMenu = new JMenu("File");
        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(e -> saveFloorplan());
        fileMenu.add(saveItem);

        JMenuItem loadItem = new JMenuItem("Load");
        loadItem.addActionListener(e -> loadFloorplan());
        fileMenu.add(loadItem);

        fileMenu.add(new JSeparator()); // Separator

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);

        // Help Menu
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> showAbout());
        helpMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);
    }
    private void saveFloorplan() {
        TopPanel.setMessage(FileHandler.saveToFileWithPreview(null,
                canvasPanel.getLayoutItems(), canvasPanel));
    }

    private void loadFloorplan() {
        TopPanel.setMessage(FileHandler.loadFromFileWithPreview(null,
                canvasPanel.getLayoutItems(), canvasPanel));
    }

    private void showAbout() {
        JOptionPane.showMessageDialog(this,
                "Interactive Floor Plan Designer\n" +
                        "Version 1.0\n" +
                        "Icons from Flaticon", "About", JOptionPane.INFORMATION_MESSAGE);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(FloorPlanDesigner::new);
    }
}
