import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import FurnitureObjects.Essentials.*;
import Interfaces.*;


/**
 * Simple Paint Application using Java Swing.
 * Allows users to draw, save, load, and clear drawings.
 *
 * @author ChatGPT
 */
public class App extends JFrame {

    private BufferedImage canvas;
    private Point lastPoint;
    private JPanel rightPanel;
    private ArrayList<FurnitureObject> layoutItems;
    private ArrayList<JButton> objects;
    private FurnitureObject currentObjectToPlace;
    /**
     * Constructor to initialize the application.
     */
    public App() {
        super("Simple Paint Application");
        initUI();
        initDrawing();
        addAddItemButton();

        rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(0, 1));
        add(rightPanel, BorderLayout.EAST);

        layoutItems = new ArrayList<>();
        objects = new ArrayList<>();
    }

    /**
     * Initializes the User Interface components of the application.
     */

    private void initUI() {
        canvas = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        clearCanvas();

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(canvas, 0, 0, null);
                drawGrid(g);
                for (FurnitureObject item : layoutItems){
                    item.draw((Graphics2D) g);
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

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                // If there is a selected object, place it on the canvas at the clicked position
                if (currentObjectToPlace != null) {
                    layoutItems.add(currentObjectToPlace.createCopyAtPosition(e.getPoint()));
                    panel.repaint();
                    currentObjectToPlace = null; // Reset the selected object after placing
                }
            }
        });

        panel.setPreferredSize(new Dimension(800, 600));
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                lastPoint = e.getPoint();
            }
        });

        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                drawLine(lastPoint, e.getPoint());
                lastPoint = e.getPoint();
                repaint();
            }
        });

        add(panel);
        setupMenuBar();
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


    }

    /**
     * Initializes drawing settings for the canvas.
     */
    private void initDrawing() {
        Graphics2D g2d = canvas.createGraphics();
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
    }

    /**
     * Draws a line between two points.
     *
     * @param start The starting point of the line.
     * @param end The ending point of the line.
     */
    private void drawLine(Point start, Point end) {
        Graphics2D g2d = canvas.createGraphics();
        g2d.setColor(Color.BLACK);
        g2d.drawLine(start.x, start.y, end.x, end.y);
        g2d.dispose();
    }

    /**
     * Clears the canvas.
     */
    private void clearCanvas() {
        Graphics2D g2d = canvas.createGraphics();
        g2d.setComposite(AlphaComposite.Clear);
        g2d.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        g2d.setComposite(AlphaComposite.SrcOver);
        g2d.dispose();
        repaint();
    }



    /**
     * Saves the current drawing to a file.
     */
    private void saveImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Image");
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                ImageIO.write(canvas, "PNG", file);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Loads an image from a file into the canvas.
     */
    private void loadImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Open Image");
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                canvas = ImageIO.read(file);
                repaint();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Shows an About dialog with information about the application.
     */
    private void showAbout() {
        JOptionPane.showMessageDialog(this, "Simple Paint Application\nVersion 1.0\nCreated by ChatGPT", "About", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Sets up the menu bar with File, Edit, and Help menus.
     */
    private void setupMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // File Menu
        JMenu fileMenu = new JMenu("File");
        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(e -> saveImage());
        fileMenu.add(saveItem);

        JMenuItem loadItem = new JMenuItem("Load");
        loadItem.addActionListener(e -> loadImage());
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

    private void addAddItemButton() {
        JButton addItemButton = new JButton("Add Item"); // Create a new button with text "Add Item"
        /**
        addItemButton.addActionListener(new ActionListener() { // Add an action listener to handle button clicks
            public void actionPerformed(ActionEvent e) {
                clearCanvas();  //displayRoomMenu();
            }
        });
       */
        addItemButton.addActionListener(e -> {
            JPopupMenu addItemMenu = new JPopupMenu();
            JMenuItem essentialsItem = new JMenuItem("Essentials");
            JMenuItem kitchenItem = new JMenuItem("Kitchen");
            JMenuItem bathroomItem = new JMenuItem("Bathroom");
            JMenuItem bedroomItem = new JMenuItem("Bedroom");
            JMenuItem livingRoomOfficeItem = new JMenuItem("Living Room/Office");

            essentialsItem.addActionListener(actionEvent -> {
                populateRightPanel(getEssentialsItems());
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
        JPanel buttonPanel = new JPanel(); // Create a panel to hold the button
        buttonPanel.add(addItemButton); // Add the button to the panel

        add(buttonPanel, BorderLayout.WEST);

    }
    private void populateRightPanel(ArrayList<FurnitureObject> items) {
        rightPanel.removeAll();
        for (FurnitureObject item : items) {
            JButton itemButton = new JButton();
            itemButton.setLayout(new BorderLayout());
            itemButton.add(new JLabel(new ImageIcon(createImageForFurnitureObject(item))), BorderLayout.CENTER);
            itemButton.add(new JLabel(item.getName(), SwingConstants.CENTER), BorderLayout.SOUTH);
            itemButton.addActionListener(e -> {
                currentObjectToPlace = item;
                // selectedItems.add(item);
                for (JButton obj : objects) {
                    obj.setBackground(null);
                }
                itemButton.setBackground(Color.YELLOW);

            });
            objects.add(itemButton);
            rightPanel.add(itemButton);
        }
            /*
            JButton button = new JButton(item.getName());
            button.addActionListener(e -> {
                selectedItems.add(item);
                for (JButton obj : objects) {
                    obj.setBackground(null);
                }
                button.setBackground(Color.YELLOW);
            });
            objects.add(button);
            rightPanel.add(button);
        }

             */
        revalidate();
        repaint();
    }

    private Image createImageForFurnitureObject(FurnitureObject object) {  // -----------------------------------
        Image image = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) image.getGraphics();
        object.draw(g2d);
        g2d.dispose();
        return image;
    }

    private ArrayList<FurnitureObject> getEssentialsItems() {
        return new ArrayList<>(Arrays.asList(new Door(), new FurnitureObjects.Essentials.Window(), new Wall(), new Rug(), new Mirror(), new Lamp()));
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

    private void resizeCanvas(int width, int height) {
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
