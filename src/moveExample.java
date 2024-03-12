import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class moveExample extends JFrame {
    private RectanglePanel rectanglePanel;

    public moveExample() {
        setTitle("Drag Rectangle");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the rectangle panel
        rectanglePanel = new RectanglePanel();
        add(rectanglePanel);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new moveExample());
    }
}

class RectanglePanel extends JPanel {
    private Rectangle rectangle;
    private Point startPoint;
    private boolean dragging;

    public RectanglePanel() {
        setPreferredSize(new Dimension(400, 400));
        setBackground(Color.WHITE);

        rectangle = new Rectangle(150, 150, 100, 100);
        startPoint = null;
        dragging = false;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (rectangle.contains(e.getPoint())) {
                    startPoint = e.getPoint();
                    dragging = true;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                startPoint = null;
                dragging = false;
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (dragging && startPoint != null) {
                    int dx = e.getX() - startPoint.x;
                    int dy = e.getY() - startPoint.y;
                    rectangle.translate(dx, dy);
                    startPoint = e.getPoint();
                    repaint();
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(Color.BLUE);
        g2d.fill(rectangle);
        g2d.dispose();
    }
}