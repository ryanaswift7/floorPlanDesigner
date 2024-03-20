package Panels;
import Templates.*;
import Utils.MoveUtility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class CanvasPanel extends JPanel {

    private BufferedImage canvas;
    private ArrayList<FurnitureObject> layoutItems;
    private ArrayList<FurnitureObject> selectedItems;
    private static CanvasPanel instance; // Singleton instance
    private static FurnitureObject currentObjectToPlace;
    private Rectangle selectionBox;
    private Point selectionStartPoint;
    private Point selectionEndPoint;

    private CanvasPanel() {
        canvas = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        clearCanvas();
        layoutItems = new ArrayList<>();
        selectedItems = new ArrayList<>();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (currentObjectToPlace != null && !(currentObjectToPlace instanceof BoundaryLineObject)) {
                    layoutItems.add(currentObjectToPlace.createObjectAtPosition(e.getPoint()));
                    repaint();
                    currentObjectToPlace = null;
                    RightPanel.resetButtonColors();

                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (currentObjectToPlace != null && currentObjectToPlace instanceof BoundaryLineObject) {
                    layoutItems.add(currentObjectToPlace.createObjectAtPosition(e.getPoint()));
                } else {
                    selectedItems.clear();
                    selectionStartPoint = e.getPoint();
                    selectionEndPoint = selectionStartPoint;
                    selectionBox = new Rectangle(selectionStartPoint.x, selectionStartPoint.y, 0, 0);
                    repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (currentObjectToPlace != null && currentObjectToPlace instanceof BoundaryLineObject) {
                    currentObjectToPlace = null;
                    RightPanel.resetButtonColors();
                } else {
                    selectObjects();
                    if (!selectedItems.isEmpty()) {
                        // Display menu
                        displaySelectMenu(e);
                    }
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (currentObjectToPlace != null && currentObjectToPlace instanceof BoundaryLineObject) {
                    // Update boundary line object
                    BoundaryLineObject newBLO = (BoundaryLineObject) layoutItems.get(layoutItems.size() - 1);
                    newBLO.updateEndpoint(e.getPoint());
                    repaint();
                } else {
                    // Update selection box
                    selectionEndPoint = e.getPoint();
                    int x = Math.min(selectionStartPoint.x, selectionEndPoint.x);
                    int y = Math.min(selectionStartPoint.y, selectionEndPoint.y);
                    int width = Math.abs(selectionStartPoint.x - selectionEndPoint.x);
                    int height = Math.abs(selectionStartPoint.y - selectionEndPoint.y);
                    selectionBox.setBounds(x, y, width, height);
                    repaint();
                }
            }
        });

        // Add a component listener to the canvas panel to handle resizing
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                resizeCanvas();
            }
        });

        setPreferredSize(new Dimension(1000, 900));
    }

    public static synchronized CanvasPanel getInstance() {
        if (instance == null) {
            instance = new CanvasPanel();
        }
        return instance;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(canvas, 0, 0, null);
        drawGrid(g);
        for (FurnitureObject item : layoutItems) {
            item.draw((Graphics2D) g);
        }
        if (selectionBox != null) {
            g.setColor(Color.RED);
            ((Graphics2D) g).draw(selectionBox);
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

    public void clearCanvas() {
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

    private void resizeCanvas() {
        int width = this.getWidth();
        int height = this.getHeight();
        BufferedImage newCanvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = newCanvas.createGraphics();
        g2d.drawImage(canvas, 0, 0, width, height, null);
        g2d.dispose();
        canvas = newCanvas;
        repaint();
    }

    private void displaySelectMenu(MouseEvent event) {
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
            MoveUtility.moveSelectedItemsOnPanel(selectedItems, this);
            for (FurnitureObject item: selectedItems) {
                if (!(item instanceof Movable)) {
                    TopPanel.setMessage("Some of the selected items are not Movable");
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
                    TopPanel.setMessage("Some of the selected items are not Resizable");
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
                    TopPanel.setMessage("Some of the selected items are not Resizable");
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
                    TopPanel.setMessage("Some of the selected items are not Resizable");
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
                    TopPanel.setMessage("Some of the selected items are not Rotatable");
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
                    TopPanel.setMessage("Some of the selected items are not Rotatable");
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
                    TopPanel.setMessage("Some of the selected items are not Rotatable");
                }
            }
            repaint();
            selectedItems.clear();

        });
        // Display the popup menu
        selectMenu.show(SwingUtilities.getRoot(this), event.getX(), event.getY());
    }

    public static void setCurrentObjectToPlace(FurnitureObject newObject) {
        currentObjectToPlace = newObject;
    }

    public ArrayList<FurnitureObject> getLayoutItems() {
        return layoutItems;
    }
}
