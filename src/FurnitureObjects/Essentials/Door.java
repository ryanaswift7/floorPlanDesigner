// Door.java
package FurnitureObjects.Essentials;
import Interfaces.FurnitureObject;
import Interfaces.Movable;
import Interfaces.Resizable;
import Interfaces.Rotatable;
import Utils.ImageManipulator;
import Utils.PathConverter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class Door implements FurnitureObject, Movable, Rotatable, Resizable {
    private int x, y, size;
    private static final String imagePath =
            PathConverter.convertPathBasedOnOS("FurnitureObjects/Essentials/door.png");
    private static final String name = "Door";
    private transient BufferedImage doorImage;


    public Door(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
        loadImage();
    }
    // empty constructor used for creating rightPanel boxes
    public Door() {
        this.x = 0;
        this.y = 0;
        this.size = 50;
        loadImage();
    }
    public void loadImage() {
        try {
            doorImage = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void draw(Graphics2D g) {
        if (doorImage != null) {
            BufferedImage resizedDoorImage = ImageManipulator.resizeImage(doorImage, size, size);
            g.drawImage(resizedDoorImage, x, y, null);
        } else {
            // If image loading failed, draw a placeholder rectangle
            g.setColor(Color.RED);
            g.fillRect(x, y, size, size);
        }
    }

    public String getName(){return name;}
    public FurnitureObject createObjectAtPosition(Point position) {
        return new Door(position.x, position.y, 50);
    }
    public Rectangle getBoundingBox() {
        return new Rectangle(x, y, size, size);
    }

    @Override
    public Point getPosition() {
        return new Point(x, y);
    }

    @Override
    public void setPosition(Point newPosition) {
        this.x = newPosition.x;
        this.y = newPosition.y;
    }

    @Override
    public void rotate90degrees() {
        doorImage = ImageManipulator.rotateImage(doorImage, 90);

    }

    @Override
    public void rotate180degrees() {
        doorImage = ImageManipulator.rotateImage(doorImage, 180);

    }

    @Override
    public void rotate270degrees() {
        doorImage = ImageManipulator.rotateImage(doorImage, 270);

    }

    @Override
    public void setSmall() {
        size = 30;
    }

    @Override
    public void setMedium() {
        size = 50;
    }

    @Override
    public void setLarge() {
        size = 80;
    }


    @Override
    // Custom serialization method
    public void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject(); // Write other fields
        if (doorImage != null) {
            // Convert BufferedImage to byte array
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(doorImage, "png", baos);
            byte[] imageBytes = baos.toByteArray();
            out.writeInt(imageBytes.length);
            out.write(imageBytes); // Write image data to the stream
        } else {
            out.writeInt(0); // Indicate that no image is available
        }
    }

    @Override
    // Custom deserialization method
    public void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject(); // Read other fields
        int imageLength = in.readInt();
        if (imageLength > 0) {
            byte[] imageBytes = new byte[imageLength];
            in.readFully(imageBytes); // Read image data from the stream
            try (ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes)) {
                doorImage = ImageIO.read(bais); // Convert byte array back to BufferedImage
            }
        } else {
            doorImage = null; // No image available
        }
    }
}
