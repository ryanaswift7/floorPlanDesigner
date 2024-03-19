import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import FurnitureObjects.Essentials.*;
import Interfaces.*;
import Utils.ComplimentGenerator;
import Utils.FileHandler;
import Utils.MoveUtility;


/**
 * Simple Paint Application using Java Swing.
 * Allows users to draw, save, load, and clear drawings.
 *
 * @author ChatGPT
 */
public class App extends JFrame {

    private BufferedImage canvas;
    private JPanel rightPanel;
    private ArrayList<FurnitureObject> layoutItems;
    private ArrayList<FurnitureObject> selectedItems;
    private ArrayList<JButton> rightButtons;
    private FurnitureObject currentObjectToPlace;
    private Rectangle selectionBox;
    private Point selectionStartPoint;
    private Point selectionEndPoint;
    private static JTextField messageField;
    private JPanel canvasPanel;

    /**
     * Constructor to initialize the application.
     */
    public App() {
        super("Interactive Floor Plan Designer");
        initUI();
        initDrawing();
        addLeftPanel();
        initRightPanel();
        initFields();
    }

    private void initFields() {
        // Create an instance of ComplimentGenerator and start generating compliments
        ComplimentGenerator generator = new ComplimentGenerator(messageField);
        generator.startGeneratingCompliments();

        layoutItems = new ArrayList<>();
        selectedItems = new ArrayList<>();
        rightButtons = new ArrayList<>();
    }

    private void initUI() {
        canvas = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        clearCanvas();

        canvasPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(canvas, 0, 0, null);
                drawGrid(g);
                for (FurnitureObject item : layoutItems){
                    item.draw((Graphics2D) g);
                }
                Graphics2D g2d = (Graphics2D) g;
                if (selectionBox != null) {
                    g2d.setColor(Color.RED); // Change color to red
                    g2d.draw(selectionBox);
                }
            }

            private void drawGrid(Graphics g) {
                int cellWidth = 40;
                int cellHeight = 40;

                // Draw solid lines for full cells
                g.setColor(Color.BLACK);
                for (int x = 0; x < canvas.getWidth(); x += cellWidth) {
                    g.drawLine(x, 0, x, canvas.getHeight());
                }
                for (int y = 0; y < canvas.getHeight(); y += cellHeight) {
                    g.drawLine(0, y, canvas.getWidth(), y);
                }

                // Draw dashed lines for half cells
                Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{2}, 0);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setStroke(dashed);
                g2d.setColor(Color.GRAY);
                int halfCellWidth = cellWidth / 2;
                int halfCellHeight = cellHeight / 2;
                for (int x = halfCellWidth; x < canvas.getWidth(); x += cellWidth) {
                    g2d.drawLine(x, 0, x, canvas.getHeight());
                }
                for (int y = halfCellHeight; y < canvas.getHeight(); y += cellHeight) {
                    g2d.drawLine(0, y, canvas.getWidth(), y);
                }
                g2d.dispose();
            }
        };

        canvasPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                // If there is a selected object, place it on the canvas at the clicked position
                if (currentObjectToPlace != null && !(currentObjectToPlace instanceof Wall)) {
                    layoutItems.add(currentObjectToPlace.createObjectAtPosition(e.getPoint()));
                    canvasPanel.repaint();
                    currentObjectToPlace = null; // Reset the selected object after placing
                    resetRightButtonColors();
                }

            }
            @Override
            public void mousePressed(MouseEvent e) {
                if (currentObjectToPlace != null && currentObjectToPlace instanceof Wall){
                    layoutItems.add(currentObjectToPlace.createObjectAtPosition(e.getPoint()));
                }
                else {
                    selectedItems.clear();
                    selectionStartPoint = e.getPoint();
                    selectionEndPoint = selectionStartPoint;
                    selectionBox = new Rectangle(selectionStartPoint.x, selectionStartPoint.y, 0, 0);
                    canvasPanel.repaint();
                }
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                if (currentObjectToPlace != null && currentObjectToPlace instanceof Wall) {
                    currentObjectToPlace = null;
                    resetRightButtonColors();
                }
                else {
                    selectObjects();
                    if (!selectedItems.isEmpty()) {
                        displaySelectMenu(canvasPanel, e);
                    }
                }
            }
        });

        canvasPanel.setPreferredSize(new Dimension(800, 600));

        // Add a component listener to the canvas panel to handle resizing
        canvasPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                resizeCanvas(canvasPanel);
            }
        });

        canvasPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (currentObjectToPlace != null && currentObjectToPlace instanceof Wall){
                    Wall newWall = (Wall) layoutItems.getLast();
                    newWall.updateWallEndpoint(e.getPoint(), canvasPanel);
                    canvasPanel.repaint();
                }
                else {
                    selectionEndPoint = e.getPoint();
                    int x = Math.min(selectionStartPoint.x, selectionEndPoint.x);
                    int y = Math.min(selectionStartPoint.y, selectionEndPoint.y);
                    int width = Math.abs(selectionStartPoint.x - selectionEndPoint.x);
                    int height = Math.abs(selectionStartPoint.y - selectionEndPoint.y);
                    selectionBox.setBounds(x, y, width, height);
                    canvasPanel.repaint();
                }
            }
        });

        add(canvasPanel);
        setupMenuBar();
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


    }
    private void selectObjects() {
        for (FurnitureObject item : layoutItems) {
            Rectangle objBounds = item.getBoundingBox();
            if (objBounds != null && selectionBox.intersects(objBounds)) {
                System.out.println("Object selected: " + item.getName());
                selectedItems.add(item);
            }
        }
        selectionBox = null; // Reset selection box after selection is done
        repaint();
    }

    private void displaySelectMenu(JPanel canvasPanel, MouseEvent event) {
        JPopupMenu selectMenu = new JPopupMenu();

        // Menu options
        JMenuItem moveItem = new JMenuItem("Move");
        JMenuItem deleteItem = new JMenuItem("Delete");


        // Submenu options for Resize
        JMenu resizeSubMenu = new JMenu("Resize");
        JMenuItem smallResizeItem = new JMenuItem("Small");
        JMenuItem mediumResizeItem = new JMenuItem("Medium (Default)");
        JMenuItem largeResizeItem = new JMenuItem("Large");
        resizeSubMenu.add(smallResizeItem);
        resizeSubMenu.add(mediumResizeItem);
        resizeSubMenu.add(largeResizeItem);

        // Submenu options for Rotate
        JMenu rotateSubMenu = new JMenu("Rotate");
        JMenuItem rotate90Item = new JMenuItem("90 degrees");
        JMenuItem rotate180Item = new JMenuItem("180 degrees");
        JMenuItem rotate270Item = new JMenuItem("270 degrees");
        rotateSubMenu.add(rotate90Item);
        rotateSubMenu.add(rotate180Item);
        rotateSubMenu.add(rotate270Item);

        // Add menu items to popup menu
        selectMenu.add(moveItem);
        selectMenu.add(deleteItem);
        selectMenu.add(resizeSubMenu);
        selectMenu.add(rotateSubMenu);

        moveItem.addActionListener(e -> {
            MoveUtility.moveSelectedItemsOnPanel(selectedItems, canvasPanel);
            for (FurnitureObject item: selectedItems) {
                if (!(item instanceof Movable)) {
                    setMessage("Some of the selected items are not Movable");
                    break;
                }
            }
        });

        deleteItem.addActionListener(e -> {
            for (FurnitureObject item : selectedItems) {
                layoutItems.remove(item);
            }
            repaint();
            selectedItems.clear();
        });

        smallResizeItem.addActionListener(e -> {
            // Implement action for Small Resize
            for (FurnitureObject item : selectedItems) {
                if (item instanceof Resizable resizeItem) {
                    resizeItem.setSmall();
                }
                else {
                    setMessage("Some of the selected items are not Resizable");
                }
            }
            repaint();
            selectedItems.clear();

        });

        mediumResizeItem.addActionListener(e -> {
            // Implement action for Medium Resize
            for (FurnitureObject item : selectedItems) {
                if (item instanceof Resizable resizeItem) {
                    resizeItem.setMedium();
                }
                else {
                    setMessage("Some of the selected items are not Resizable");
                }
            }
            repaint();
            selectedItems.clear();

        });

        largeResizeItem.addActionListener(e -> {
            // Implement action for Large Resize
            for (FurnitureObject item : selectedItems) {
                if (item instanceof Resizable resizeItem) {
                    resizeItem.setLarge();
                }
                else {
                    setMessage("Some of the selected items are not Resizable");
                }
            }
            repaint();
            selectedItems.clear();

        });

        rotate90Item.addActionListener(e -> {
            // Implement action for Rotate 90 degrees
            for (FurnitureObject item : selectedItems) {
                if (item instanceof Rotatable rotateItem) {
                    rotateItem.rotate90degrees();
                }
                else {
                    setMessage("Some of the selected items are not Rotatable");
                }
            }
            repaint();
            selectedItems.clear();

        });

        rotate180Item.addActionListener(e -> {
            // Implement action for Rotate 180 degrees
            for (FurnitureObject item : selectedItems) {
                if (item instanceof Rotatable rotateItem) {
                    rotateItem.rotate180degrees();
                }
                else {
                    setMessage("Some of the selected items are not Rotatable");
                }
            }
            repaint();
            selectedItems.clear();

        });

        rotate270Item.addActionListener(e -> {
            // Implement action for Rotate 270 degrees
            for (FurnitureObject item : selectedItems) {
                if (item instanceof Rotatable rotateItem) {
                    rotateItem.rotate270degrees();
                }
                else {
                    setMessage("Some of the selected items are not Rotatable");
                }
            }
            repaint();
            selectedItems.clear();

        });
        // Display the popup menu
        selectMenu.show(SwingUtilities.getRoot(canvasPanel), event.getX(), event.getY());
    }

    private void initDrawing() {
        Graphics2D g2d = canvas.createGraphics();
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
    }

    private void clearCanvas() {
        if (layoutItems != null) {
            layoutItems.clear();
        }
        else {
            Graphics2D g2d = canvas.createGraphics();
            g2d.setComposite(AlphaComposite.Clear);
            g2d.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
            g2d.setComposite(AlphaComposite.SrcOver);
            g2d.dispose();
        }
        repaint();
    }



    /**
     * Saves the current drawing to a file.
     */
    private void saveFloorplan() {
        setMessage(FileHandler.saveToFileWithPreview(null, layoutItems, canvasPanel));
    }

    /**
     * Loads an image from a file into the canvas.
     */
    private void loadFloorplan() {
        setMessage(FileHandler.loadFromFileWithPreview(null, layoutItems, canvasPanel));
    }

    /**
     * Shows an About dialog with information about the application.
     */
    private void showAbout() {
        JOptionPane.showMessageDialog(this,
                "Interactive Floor Plan Designer\n" +
                        "Version 1.0\n" +
                        "Icons from Flaticon", "About", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Sets up the menu bar with File, Edit, and Help menus.
     */
    private void setupMenuBar() {
        JMenuBar menuBar = new JMenuBar();

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

        // Edit Menu
        JMenu editMenu = new JMenu("Edit");
        JMenuItem clearItem = new JMenuItem("Clear");
        clearItem.addActionListener(e -> clearCanvas());
        editMenu.add(clearItem);

        // Help Menu
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> showAbout());
        helpMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);
    }

    private void addLeftPanel() {
        messageField = new JTextField();
        messageField.setEditable(false); // Make the text field read-only
        messageField.setPreferredSize(new Dimension(200, 20));

        JButton clearCanvasButton = new JButton("Clear");
        clearCanvasButton.addActionListener(e -> clearCanvas());

        JButton addItemButton = new JButton("Add Item");
        addItemButton.addActionListener(e -> {
            JPopupMenu addItemMenu = new JPopupMenu();
            JMenuItem essentialsItem = new JMenuItem("Essentials");
            JMenuItem kitchenItem = new JMenuItem("Kitchen");
            JMenuItem bathroomItem = new JMenuItem("Bathroom");
            JMenuItem bedroomItem = new JMenuItem("Bedroom");
            JMenuItem livingRoomOfficeItem = new JMenuItem("Living Room/Office");

            essentialsItem.addActionListener(actionEvent -> {
                addRightPanel(getEssentialsItems());
            });
/*
            kitchenItem.addActionListener(actionEvent -> {
                populateRightPanel(getKitchenItems());
            });

            bathroomItem.addActionListener(actionEvent -> {
                populateRightPanel(getBathroomItems());
            });

            bedroomItem.addActionListener(actionEvent -> {
                populateRightPanel(getBedroomItems());
            });

            livingRoomOfficeItem.addActionListener(actionEvent -> {
                populateRightPanel(getLivingRoomOfficeItems());
            });
*/
            addItemMenu.add(essentialsItem);
            addItemMenu.add(kitchenItem);
            addItemMenu.add(bathroomItem);
            addItemMenu.add(bedroomItem);
            addItemMenu.add(livingRoomOfficeItem);

            addItemMenu.show(addItemButton, 0, addItemButton.getHeight());
        });
        JPanel leftPanel = new JPanel(); // Create a panel to hold the button
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.X_AXIS));
        leftPanel.add(Box.createHorizontalStrut(10));
        leftPanel.add(clearCanvasButton);
        leftPanel.add(Box.createHorizontalStrut(20));
        leftPanel.add(addItemButton);
        leftPanel.add(Box.createHorizontalStrut(20));
        leftPanel.add(messageField);
        add(leftPanel, BorderLayout.NORTH);
    }
    private static void setMessage(String message) {
        messageField.setText(message);
    }
    private void initRightPanel() {
        rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(0, 1));
        add(rightPanel, BorderLayout.EAST);
    }
    private void addRightPanel(ArrayList<FurnitureObject> items) {
        rightPanel.removeAll();
        for (FurnitureObject item : items) {
            JButton itemButton = new JButton();
            itemButton.setLayout(new BorderLayout());
            itemButton.add(new JLabel(new ImageIcon(createImageForItemMenu(item))), BorderLayout.CENTER);
            itemButton.add(new JLabel(item.getName(), SwingConstants.CENTER), BorderLayout.SOUTH);
            itemButton.addActionListener(e -> {
                currentObjectToPlace = item;
                resetRightButtonColors(); // Reset all button colors
                itemButton.setBackground(Color.YELLOW); // Highlight the clicked button
            });
            rightButtons.add(itemButton);
            rightPanel.add(itemButton);
        }
        revalidate();
        repaint();
    }

    // Add this method to reset all buttons' background colors
    private void resetRightButtonColors() {
        for (JButton but : rightButtons) {
            but.setBackground(null); // Reset background color of all buttons
        }
    }


    private Image createImageForItemMenu(FurnitureObject object) {  // -----------------------------------
        Image image = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) image.getGraphics();
        object.draw(g2d);
        g2d.dispose();
        return image;
    }

    private ArrayList<FurnitureObject> getEssentialsItems() {
        return new ArrayList<>(Arrays.asList(new Door(), new Wall())); //, new FurnitureObjects.Essentials.Window(), new Rug(), new Mirror(), new Lamp()));
    }

    private ArrayList<String> getKitchenItems() {
        return new ArrayList<>(Arrays.asList("Sink", "Refrigerator", "Dishwasher", "Counterspace", "Cabinets", "Microwave", "Table", "Chair", "Stool"));
    }

    private ArrayList<String> getBathroomItems() {
        return new ArrayList<>(Arrays.asList("Sink", "Double Sink", "Toilet", "Bathtub", "Shower", "Combination Tub/Shower", "Floormat"));
    }

    private ArrayList<String> getBedroomItems() {
        return new ArrayList<>(Arrays.asList("Bed", "Dresser", "Nightstand", "Chest", "Closet"));
    }

    private ArrayList<String> getLivingRoomOfficeItems() {
        return new ArrayList<>(Arrays.asList("Desk", "TV", "TV Stand", "Chair", "Couch", "Coffee Table"));
    }

    private void resizeCanvas(JPanel canvasPanel) {
        int width = canvasPanel.getWidth();
        int height = canvasPanel.getHeight();
        BufferedImage newCanvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = newCanvas.createGraphics();
        g2d.drawImage(canvas, 0, 0, width, height, null);
        g2d.dispose();
        canvas = newCanvas;
        repaint();
    }


    /**
     * Main method to run the application.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new App().setVisible(true));
    }
}
