import java.awt.*;
import java.awt.geom.*;

public class Rug implements FurnitureObject {
    private int x, y;
    private String name;

    public Rug(int x, int y) {
        this.x = x;
        this.y = y;
        this.name = "Rug";
    }

    @Override
    public void draw(Graphics2D g2d) {
        // Set the color of the rug to light gray
        g2d.setColor(new Color(192, 192, 192)); // light gray

        // Calculate the coordinates for the rectangle to be centered at (x, y)
        int rectWidth = 100; // width of the rectangle
        int rectHeight = 50; // height of the rectangle
        int rectX = x - rectWidth / 2; // x-coordinate of the top-left corner of the rectangle
        int rectY = y - rectHeight / 2; // y-coordinate of the top-left corner of the rectangle

        // Draw the rectangle (rug)
        Rectangle2D.Double rug = new Rectangle2D.Double(rectX, rectY, rectWidth, rectHeight);
        g2d.fill(rug);
    }

    @Override
    public String getName() {
        return name;
    }
}
