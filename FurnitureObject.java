import java.awt.*;
import java.awt.geom.*;

public abstract class FurnitureObject {
    private String name;
    public FurnitureObject(){};
    public String getName() {return name;}
    public abstract void drawFurniture(Graphics2D g2d);
    public abstract void rotate90degrees(Graphics2D g2d);
    public abstract void moveFurnitureObject(Graphics2D g2d);

}
