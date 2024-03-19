import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;

public class chatgptTesting extends JFrame {

    private JPanel canvasPanel;
    private BufferedImage drawingCanvas;

    public chatgptTesting() {
        setTitle("Drawing Canvas Example");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize the canvas panel
        canvasPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (drawingCanvas != null) {
                    g.drawImage(drawingCanvas, 0, 0, null);
                }
            }
        };
        canvasPanel.setBackground(Color.WHITE);

        // Add a component listener to the canvas panel to handle resizing
        canvasPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                resizeCanvas();
            }
        });

        // Set layout manager for the canvas panel
        canvasPanel.setLayout(new BorderLayout());

        // Add some components to the canvas panel
        JButton button1 = new JButton("Button 1");
        JButton button2 = new JButton("Button 2");
        canvasPanel.add(button1, BorderLayout.NORTH);
        canvasPanel.add(button2, BorderLayout.SOUTH);

        // Add canvas panel to the frame
        add(canvasPanel);
    }

    // Method to resize the drawing canvas
    private void resizeCanvas() {
        int width = canvasPanel.getWidth();
        int height = canvasPanel.getHeight();

        // Create a new BufferedImage with the new dimensions
        drawingCanvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // Redraw your content on the resized canvas if needed
        // Example: Graphics2D g2d = drawingCanvas.createGraphics();
        // ...

        // Repaint the canvas panel to reflect the changes
        canvasPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            chatgptTesting example = new chatgptTesting();
            example.setVisible(true);
        });
    }
}
