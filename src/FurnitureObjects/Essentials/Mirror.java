package FurnitureObjects.Essentials;

import Interfaces.FurnitureObject;

import java.awt.*;
import java.awt.geom.*;

public abstract class Mirror implements FurnitureObject {
    private int x1, y1, x2, y2;
    private String name;

    public Mirror(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.name = "Window";
    }

    public Mirror() {
        this.x1 = 0;
        this.y1 = 0;
        this.x2 = 0;
        this.y2 = 0;
        this.name = "Window";
    }

    @Override
    public void draw(Graphics2D g2d) {
        // Set the color of the window to light blue
        g2d.setColor(new Color(192, 192, 192)); // Light gray

        // Set the thickness of the window line
        g2d.setStroke(new BasicStroke(4)); // 4-pixel thick line

        // make sure lines are drawn perfectly horizontal or vertical
        int diff_x = Math.abs(x2-x1);
        int diff_y = Math.abs(y2-y1);
        if (diff_x <= diff_y){ x2 = x1; }
        else { y2 = y1; }

        // Draw the window line
        g2d.drawLine(x1, y1, x2, y2);
    }

    @Override
    public FurnitureObject createObjectAtPosition(Point position) {
        return null;
    }

    @Override
    public String getName() {
        return name;
    }
    public Rectangle getBoundingBox() {
        if (y1 == y2) {
            return new Rectangle(x1, y1, x2-x1, 0);
        }
        else { // x1 == x2
            return new Rectangle(x1, y1, 0, y2-y1);
        }
    }

}
