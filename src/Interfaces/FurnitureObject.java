package Interfaces;

import java.awt.*;
import java.awt.geom.*;
import java.awt.Graphics2D;
public interface FurnitureObject {
    public String getName();
    public void draw(Graphics2D g2d);
    public FurnitureObject createCopyAtPosition(Point position);
    // public void drawIcon(Graphics2D g2d);
    // public boolean isResizable();
    // public boolean isRotatable();
    // public boolean isMovable();
}
