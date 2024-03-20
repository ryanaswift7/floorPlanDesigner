package Templates;

import java.awt.*;
import java.awt.Graphics2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public interface FurnitureObject extends Serializable {
    public String getName();
    public void draw(Graphics2D g2d);
    public FurnitureObject createObjectAtPosition(Point position);
    public Rectangle getBoundingBox();
    public void loadImage();
    public void writeObject(ObjectOutputStream oos) throws IOException;
    public void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException;
}
