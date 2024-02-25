// Door.java
import java.awt.*;
import javax.swing.JPanel;

public class Door implements FurnitureObject {
    private int x, y, width;
    private static String name;

    public Door(int x, int y, int width) {
        this.x = x;
        this.y = y;
        this.width = width;
    }

    @Override
    public void draw(Graphics2D g) {
        // Draw the door frame
        g.setColor(Color.BLACK);
        g.drawLine(x, y, x + width, y); // Horizontal line
        g.drawLine(x + width, y, x + width, y + width); // Vertical line

        // Calculate the center point of the arc
        int centerX = x + width;
        int centerY = y;

        // Calculate the radius of the arc
        int radius = width;

        // Calculate the start angle based on the orientation of the lines
        int startAngle;
        if (width >= 0) {
            startAngle = 180; // If the vertical line is going downwards
        } else {
            startAngle = 90; // If the vertical line is going upwards
        }

        // Calculate the extent of the arc
        int extent = 90; // 90 degrees for a right angle

        // Set the stroke to a dashed stroke
        float[] dashPattern = {5f, 5f}; // Dash pattern: 5 pixels on, 5 pixels off
        g.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 1.0f, dashPattern, 0));


        // Draw the arc for door swing range
        g.drawArc(centerX - radius, centerY - radius, 2 * radius, 2 * radius, startAngle, extent);
    }

    public String getName(){return name;}
}
