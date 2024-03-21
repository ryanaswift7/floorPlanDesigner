package Templates;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public abstract class BoundaryLineObject implements FurnitureObject, Movable {
    protected int x1, y1, x2, y2, deltaX, deltaY;
    protected String name;
    protected Color color;
    protected transient BasicStroke stroke;

    // Additional fields for serialization
    protected float strokeWidth;


    public BoundaryLineObject(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }
    public BoundaryLineObject() {
        this.x1 = 25;
        this.y1 = 0;
        this.x2 = 25;
        this.y2 = 50;
    }
    protected void setName(String name) { this.name = name; }
    protected void setColor(Color color) { this.color = color; }
    protected void setStroke(BasicStroke stroke) { this.stroke = stroke; }
    protected void setStrokeWidth(float strokeWidth) { this.strokeWidth = strokeWidth; }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void draw(Graphics2D g) {
        Stroke defaultStroke = g.getStroke(); // Save the default stroke

        // initialize strokeWidth and stroke (necessary for loading file)
        setStroke(new BasicStroke(strokeWidth));

        // Set the thickness
        g.setStroke(stroke);

        g.setColor(color);

        // make sure lines are drawn perfectly horizontal or vertical
        int diff_x = Math.abs(x2-x1);
        int diff_y = Math.abs(y2-y1);
        if (diff_x <= diff_y){ x2 = x1; }
        else { y2 = y1; }


        snapToGrid();
        g.drawLine(x1, y1, x2, y2);
        g.setStroke(defaultStroke); // Reset stroke to default

    }

    public void updateEndpoint(Point point) {
        x2 = point.x;
        y2 = point.y;
    }
    @Override
    public Rectangle getBoundingBox() {
        int minX = Math.min(x1, x2);
        int minY = Math.min(y1, y2);
        int width = Math.abs(x2 - x1);
        int height = Math.abs(y2 - y1);
        // constants create small bounding box over 1D line
        return new Rectangle(minX-2, minY-2, width + 4, height + 4);
    }


    public void snapToGrid() {
        int gridSize = 20;
        x1 = (int) (Math.round(x1 / (double) gridSize) * gridSize);
        y1 = (int) (Math.round(y1 / (double) gridSize) * gridSize);
        x2 = (int) (Math.round(x2 / (double) gridSize) * gridSize);
        y2 = (int) (Math.round(y2 / (double) gridSize) * gridSize);
    }

    @Override
    public Point getPosition() {
        deltaX = x2 - x1;
        deltaY = y2 - y1;
        return new Point(x1, y1);
    }

    @Override
    public void setPosition(Point newPosition) {
        x1 = newPosition.x;
        y1 = newPosition.y;
        x2 = x1 + deltaX;
        y2 = y1 + deltaY;
    }
    // Custom serialization method
    public void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject(); // Write other fields
        out.writeFloat(strokeWidth); // Serialize stroke width
    }


    // Custom deserialization method
    public void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject(); // Read other fields
        this.strokeWidth = in.readFloat(); // Deserialize stroke width
    }



}
