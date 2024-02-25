import java.awt.*;

public class Wall implements FurnitureObject {
    private int x1, y1, x2, y2;
    private String name;

    public Wall(int x1, int y1, int x2, int y2) {
        // Snap the coordinates to the grid
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.name = "Wall";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void draw(Graphics2D g) {
        // Set the thickness of the wall
        g.setStroke(new BasicStroke(3)); // Thick black line

        // Set the color of the wall
        g.setColor(Color.BLACK);

        // make sure lines are drawn perfectly horizontal or vertical
        int diff_x = Math.abs(x2-x1);
        int diff_y = Math.abs(y2-y1);
        if (diff_x <= diff_y){ x2 = x1; }
        else { y2 = y1; }


        // Draw the wall
        g.drawLine(x1, y1, x2, y2);
    }

}
