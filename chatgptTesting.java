import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class chatgptTesting extends JFrame {

    private BufferedImage canvas;
    private Point lastPoint;
    private JPanel canvasPanel; // Declare canvasPanel as a class-level variable

    public chatgptTesting() {
        super("Simple Paint Application");
        initUI();
        initDrawing();
    }

    private void initUI() {
        JPanel gridPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawGrid(g);
            }
        };

        canvasPanel = new JPanel() { // Initialize canvasPanel here
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(canvas, 0, 0, null);
            }
        };

        gridPanel.setPreferredSize(new Dimension(800, 600));
        canvasPanel.setPreferredSize(new Dimension(800, 600));

        gridPanel.setLayout(new GridLayout(1, 1));
        canvasPanel.setLayout(new GridLayout(1, 1));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1, 1));
        mainPanel.add(gridPanel);
        mainPanel.add(canvasPanel);

        getContentPane().add(mainPanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void initDrawing() {
        canvas = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        clearCanvas();

        canvasPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                lastPoint = e.getPoint();
            }
        });

        canvasPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                drawLine(lastPoint, e.getPoint());
                lastPoint = e.getPoint();
                canvasPanel.repaint(); // Repaint canvasPanel here
            }
        });
    }

    private void drawLine(Point start, Point end) {
        Graphics2D g2d = canvas.createGraphics();
        g2d.setColor(Color.BLACK);
        g2d.drawLine(start.x, start.y, end.x, end.y);
        g2d.dispose();
    }

    private void clearCanvas() {
        Graphics2D g2d = canvas.createGraphics();
        g2d.setComposite(AlphaComposite.Clear);
        g2d.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        g2d.setComposite(AlphaComposite.SrcOver);
        g2d.dispose();
        canvasPanel.repaint();
    }

    private void drawGrid(Graphics g) {
        int cellWidth = 40;
        int cellHeight = 40;

        // Draw solid lines for full cells
        g.setColor(Color.BLACK);
        for (int x = 0; x < canvas.getWidth(); x += cellWidth) {
            g.drawLine(x, 0, x, canvas.getHeight());
        }
        for (int y = 0; y < canvas.getHeight(); y += cellHeight) {
            g.drawLine(0, y, canvas.getWidth(), y);
        }

        // Draw dashed lines for half cells
        Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{2}, 0);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setStroke(dashed);
        g2d.setColor(Color.GRAY);
        int halfCellWidth = cellWidth / 2;
        int halfCellHeight = cellHeight / 2;
        for (int x = halfCellWidth; x < canvas.getWidth(); x += cellWidth) {
            g2d.drawLine(x, 0, x, canvas.getHeight());
        }
        for (int y = halfCellHeight; y < canvas.getHeight(); y += cellHeight) {
            g2d.drawLine(0, y, canvas.getWidth(), y);
        }
        g2d.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new App().setVisible(true));
    }
}
