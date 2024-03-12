package FurnitureObjects.Essentials;

import Interfaces.FurnitureObject;

import java.awt.*;
import java.awt.geom.*;

public class Lamp implements FurnitureObject {
    private int x, y;
    private String name;
    private int circleRadius;

    public Lamp(int x, int y) {
        this.x = x;
        this.y = y;
        this.name = "Lamp";
    }
    // no params => icon constructor
    public Lamp() {
        this.x = 25;
        this.y = 25;
        this.name = "Lamp";
    }

    @Override
    public void draw(Graphics2D g2d) {
        // Set the color of the lamp to light yellow
        g2d.setColor(new Color(255, 255, 153)); // light yellow

        // Calculate the coordinates for the circle to be centered at (x, y)
        circleRadius = 20; // radius of the circle
        int circleX = x - circleRadius; // x-coordinate of the top-left corner of the bounding box
        int circleY = y - circleRadius; // y-coordinate of the top-left corner of the bounding box

        // Draw the circle
        Ellipse2D.Double circle = new Ellipse2D.Double(circleX, circleY, 2 * circleRadius, 2 * circleRadius);
        g2d.fill(circle);

        // Draw a black outline around the circle
        g2d.setColor(Color.BLACK);
        g2d.draw(circle);
    }

    @Override
    public FurnitureObject createCopyAtPosition(Point position) {
        return new Lamp(position.x, position.y);
    }

    @Override
    public String getName() {
        return name;
    }
    public Rectangle getBoundingBox(){
        return new Rectangle(x, y, circleRadius, circleRadius);
    }
}

