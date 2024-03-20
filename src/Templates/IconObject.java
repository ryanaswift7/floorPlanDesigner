package Templates;

import Utils.ImageManipulator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public abstract class IconObject implements FurnitureObject, Movable, Rotatable, Resizable {
    protected int x, y, size;
    protected String imagePath;
    protected String name;
    protected transient BufferedImage image;
    public IconObject(int x, int y) {
        this.x = x;
        this.y = y;
        setMedium();
    }
    // empty constructor used for creating rightPanel boxes
    public IconObject() {
        this.x = 0;
        this.y = 0;
        this.size = 50;  // image in menu is 50x50
    }
    // Setters for fields that can be overridden
    protected void setImagePath(String imagePath) {
        this.imagePath = imagePath;
        loadImage(); // Reload the image when the path is set
    }

    protected void setName(String name) {
        this.name = name;
    }
    protected void setSize(int size){
        this.size = size;
    }
    public void loadImage() {
        try {
            image = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void draw(Graphics2D g) {
        if (image != null) {
            BufferedImage resizedDoorImage = ImageManipulator.resizeImage(image, size, size);
            g.drawImage(resizedDoorImage, x, y, null);
        } else {
            // If image loading failed, draw a placeholder rectangle
            g.setColor(Color.RED);
            g.fillRect(x, y, size, size);
        }
    }
    @Override
    public String getName(){return name;}
    @Override
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
        image = ImageManipulator.rotateImage(image, 90);

    }

    @Override
    public void rotate180degrees() {
        image = ImageManipulator.rotateImage(image, 180);

    }

    @Override
    public void rotate270degrees() {
        image = ImageManipulator.rotateImage(image, 270);

    }
    @Override
    // Custom serialization method
    public void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject(); // Write other fields
        if (image != null) {
            // Convert BufferedImage to byte array
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
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
                image = ImageIO.read(bais); // Convert byte array back to BufferedImage
            }
        } else {
            image = null; // No image available
        }
    }
}
