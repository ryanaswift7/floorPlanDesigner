package Utils;

import Templates.FurnitureObject;
import Templates.Movable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MoveUtility {
    private static Point initialMousePressLocation;
    private static Map<Movable, Point> initialPositions;

    public static void moveSelectedItemsOnPanel(ArrayList<FurnitureObject> selectedItems, JPanel canvasPanel) {
        // remove all existing (possibly conflicting) mouse motion adapters
        // to allow move functionality
        MouseAdapterHandler.setCanvasPanel(canvasPanel);
        MouseAdapterHandler.saveActiveMouseAdapters();
        MouseAdapterHandler.removeAllMouseAdapters();
        canvasPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Store initial positions of selected objects relative to the mouse press location
                initialMousePressLocation = e.getPoint();
                initialPositions = new HashMap<>(); // Initialize initialPositions

                for (FurnitureObject item : selectedItems) {
                    if (item instanceof Movable movableItem) {
                        storeInitialPositions(movableItem);
                    }
                }
            }

            private void storeInitialPositions(Movable movableItem) {
                initialPositions.put(movableItem, movableItem.getPosition());
            }

        });

        canvasPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (initialMousePressLocation != null && initialPositions != null) {
                    int offsetX = e.getX() - initialMousePressLocation.x;
                    int offsetY = e.getY() - initialMousePressLocation.y;

                    for (FurnitureObject item : selectedItems) {
                        if (item instanceof Movable) {
                            Movable movableItem = (Movable) item;
                            Point initialPosition = initialPositions.get(movableItem);
                            if (initialPosition != null) {
                                int newX = (int) initialPosition.getX() + offsetX;
                                int newY = (int) initialPosition.getY() + offsetY;
                                Point newPosition = new Point(newX, newY);
                                movableItem.setPosition(newPosition);
                            }
                        }
                    }

                    canvasPanel.repaint();
                }
            }
        });

        canvasPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                // Restore all previous adapters by removing the one we just added, then restoring all previous ones
                selectedItems.clear();
                MouseAdapterHandler.removeAllMouseAdapters();
                MouseAdapterHandler.restoreSavedMouseAdapters();
            }
        });


    }
}
