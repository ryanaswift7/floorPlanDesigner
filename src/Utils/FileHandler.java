// FileHandler.java
package Utils;

import Templates.FurnitureObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class FileHandler {

    // Method to save the furniture list along with a preview PNG image
    // Method to save the furniture list along with a preview PNG image
    public static String saveToFileWithPreview(Component parentComponent,
                                               ArrayList<FurnitureObject> layoutItems,
                                               Component canvasPanel) {
        // Open file save dialog for saving floor plan
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Floor Plan");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Floor Plan Files (*.floorplan)", "floorplan"));

        int userSelection = fileChooser.showSaveDialog(parentComponent);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String fileName = fileToSave.getAbsolutePath();

            // Add file extension if not already present
            if (!fileName.toLowerCase().endsWith(".floorplan")) {
                fileName += ".floorplan";
            }

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
                // Write layoutItems list to the stream
                oos.writeObject(layoutItems);

                // Display dialog box to ask if the user wants to save a preview image
                int result = JOptionPane.showConfirmDialog(parentComponent,
                        "Would you like to save a preview image?", "Save Preview Image",
                        JOptionPane.YES_NO_OPTION);

                if (result == JOptionPane.YES_OPTION) {
                    // Open file save dialog for saving preview image
                    JFileChooser imageFileChooser = new JFileChooser();
                    imageFileChooser.setDialogTitle("Save Preview Image");
                    imageFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    imageFileChooser.setAcceptAllFileFilterUsed(false);
                    imageFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("PNG Image (*.png)", "png"));

                    int imageUserSelection = imageFileChooser.showSaveDialog(parentComponent);

                    if (imageUserSelection == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = imageFileChooser.getSelectedFile();
                        String imageFileName = selectedFile.getAbsolutePath();

                        // Ensure the file has a PNG extension
                        if (!imageFileName.toLowerCase().endsWith(".png")) {
                            imageFileName += ".png";
                        }

                        // Capture preview image of the canvas
                        BufferedImage previewImage = new BufferedImage(canvasPanel.getWidth(), canvasPanel.getHeight(), BufferedImage.TYPE_INT_ARGB);
                        Graphics2D g2d = previewImage.createGraphics();
                        canvasPanel.paint(g2d);
                        g2d.dispose();

                        // Write the preview image data to the file
                        ImageIO.write(previewImage, "PNG", new File(imageFileName));
                        return "Floor plan saved successfully. Preview image saved as: " + imageFileName;
                    } else {
                        return "Preview image not saved.";
                    }
                }
                return "Floor plan saved successfully.";
            } catch (IOException e) {
                e.printStackTrace();
                return "Uh oh! Something went wrong trying to save the floor plan.";
            }
        }
        return "Not sure what happened there...";
    }



    // Method to load the furniture list from a custom file format
    // Method to load the furniture list from a custom file format
    public static String loadFromFileWithPreview(Component parentComponent,
                                                 ArrayList<FurnitureObject> layoutItems,
                                                 Component canvasPanel) {
        // Open file chooser dialog for selecting a file to load
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Load Floor Plan");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Floor Plan Files (*.floorplan)", "floorplan"));

        int userSelection = fileChooser.showOpenDialog(parentComponent);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(selectedFile))) {
                // Clear the existing layoutItems list
                layoutItems.clear();

                // Read the layoutItems list from the file
                ArrayList<FurnitureObject> loadedItems = (ArrayList<FurnitureObject>) ois.readObject();

                // Add loaded items to the layoutItems list
                layoutItems.addAll(loadedItems);
                for (FurnitureObject item: layoutItems) {
                    item.loadImage();
                }

                // Repaint the canvas panel to reflect the changes
                canvasPanel.repaint();

                return "Floor plan loaded successfully.";
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return "Uh oh! There was an error loading the file.";
            }
        }
        return "Not sure what happened there...";
    }

}
