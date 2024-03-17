// Door.java
package FurnitureObjects.Essentials;
import Interfaces.FurnitureObject;
import Interfaces.Movable;
import Interfaces.Resizable;
import Interfaces.Rotatable;
import Utils.ImageManipulator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Door implements FurnitureObject, Movable, Rotatable, Resizable {
    private int x, y, width;
    private static final String imagePath = "FurnitureObjects/Essentials/door.png";
    private static final String name = "Door";
    private BufferedImage doorImage;


    public Door(int x, int y, int width) {
        this.x = x;
        this.y = y;
        this.width = width;
        loadImage();
    }
    // empty constructor used for creating rightPanel boxes
    public Door() {
        this.x = 0;
        this.y = 0;
        this.width = 50;
        loadImage();
    }
    private void loadImage() {
        try {
            doorImage = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void draw(Graphics2D g) {
        if (doorImage != null) {
            BufferedImage resizedDoorImage = ImageManipulator.resizeImage(doorImage, width, width);
            g.drawImage(resizedDoorImage, x, y, null);
        } else {
            // If image loading failed, draw a placeholder rectangle
            g.setColor(Color.RED);
            g.fillRect(x, y, width, width);
        }
    }

    public String getName(){return name;}
    public FurnitureObject createObjectAtPosition(Point position) {
        return new Door(position.x, position.y, 50);
    }
    public Rectangle getBoundingBox() {
        return new Rectangle(x, y, width, width);
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
        width = 30;
    }

    @Override
    public void setMedium() {
        width = 50;
    }

    @Override
    public void setLarge() {
        width = 80;
    }
}
