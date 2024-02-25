import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class chatgptTesting extends JFrame {

    private ArrayList<myFurnitureObject> objects;
    private myFurnitureObject currentObjectToPlace;

    public chatgptTesting() {
        super("Furniture Drawing App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        // Initialize the objects list
        objects = new ArrayList<>();

        // Right panel to contain buttons for each object
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        add(rightPanel, BorderLayout.EAST);

        // Create buttons with drawings and names for each object and add them to the right panel
        addFurnitureObjectButton(rightPanel, new myDoor());
        addFurnitureObjectButton(rightPanel, new myWall());
        addFurnitureObjectButton(rightPanel, new myRug());
        addFurnitureObjectButton(rightPanel, new myWindow());
        addFurnitureObjectButton(rightPanel, new myMirror());
        addFurnitureObjectButton(rightPanel, new myLamp());

        // Canvas panel to draw objects
        JPanel canvasPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw objects
                for (myFurnitureObject object : objects) {
                    object.draw((Graphics2D) g);
                }
            }
        };
        canvasPanel.setBackground(Color.WHITE);
        canvasPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                // If there is a selected object, place it on the canvas at the clicked position
                if (currentObjectToPlace != null) {
                    objects.add(currentObjectToPlace.createCopyAtPosition(e.getPoint()));
                    canvasPanel.repaint();
                    currentObjectToPlace = null; // Reset the selected object after placing
                }
            }
        });
        add(canvasPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    // Method to add a button for a furniture object to the right panel
    private void addFurnitureObjectButton(JPanel panel, myFurnitureObject object) {
        JButton objectButton = new JButton();
        objectButton.setLayout(new BorderLayout());
        objectButton.add(new JLabel(new ImageIcon(createImageForFurnitureObject(object))), BorderLayout.CENTER);
        objectButton.add(new JLabel(object.getName(), SwingConstants.CENTER), BorderLayout.SOUTH);
        objectButton.addActionListener(e -> currentObjectToPlace = object);
        panel.add(objectButton);
    }

    // Method to create an image for a furniture object to be used in a button
    private Image createImageForFurnitureObject(myFurnitureObject object) {  // -----------------------------------
        Image image = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) image.getGraphics();
        // g2d.setColor(Color.WHITE);
        // g2d.fillRect(0, 0, 50, 50);
        object.draw(g2d);
        g2d.dispose();
        return image;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(chatgptTesting::new);
    }
}

interface myFurnitureObject {
    String getName();

    void draw(Graphics2D g);

    boolean containsPoint(Point point);

    myFurnitureObject createCopyAtPosition(Point position);
}

class myDoor implements myFurnitureObject {
    private final int width = 40;
    private final int height = 90;
    private double x;
    private double y;

    public myDoor(double x, double y){
        this.x = x;
        this.y = y;
    }
    public myDoor(){
        this.x = 0;
        this.y = 0;
    }
    @Override
    public String getName() {
        return "Door";
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.BLUE);
        g.fillRect((int)x, (int)y, width, height);
    }

    @Override
    public boolean containsPoint(Point point) {
        return true; // Always place at the clicked position
    }

    @Override
    public myFurnitureObject createCopyAtPosition(Point position) {
        return new myDoor(position.getX(), position.getY());
    }
}

class myWall implements myFurnitureObject {
    private final int width = 90;
    private final int height = 10;

    @Override
    public String getName() {
        return "Wall";
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, width, height);
    }

    @Override
    public boolean containsPoint(Point point) {
        return true; // Always place at the clicked position
    }

    @Override
    public myFurnitureObject createCopyAtPosition(Point position) {
        return new myWall();
    }
}

class myRug implements myFurnitureObject {
    private final int width = 80;
    private final int height = 40;

    @Override
    public String getName() {
        return "Rug";
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.RED);
        g.fillRect(0, 0, width, height);
    }

    @Override
    public boolean containsPoint(Point point) {
        return true; // Always place at the clicked position
    }

    @Override
    public myFurnitureObject createCopyAtPosition(Point position) {
        return new myRug();
    }
}

class myWindow implements myFurnitureObject {
    private final int width = 30;
    private final int height = 30;

    @Override
    public String getName() {
        return "Window";
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, width, height);
    }

    @Override
    public boolean containsPoint(Point point) {
        return true; // Always place at the clicked position
    }

    @Override
    public myFurnitureObject createCopyAtPosition(Point position) {
        return new myWindow();
    }
}

class myMirror implements myFurnitureObject {
    private final int width = 40;
    private final int height = 40;

    @Override
    public String getName() {
        return "Mirror";
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.CYAN);
        g.fillRect(0, 0, width, height);
    }

    @Override
    public boolean containsPoint(Point point) {
        return true; // Always place at the clicked position
    }

    @Override
    public myFurnitureObject createCopyAtPosition(Point position) {
        return new myMirror();
    }
}

class myLamp implements myFurnitureObject {
    private final int width = 20;
    private final int height = 40;

    @Override
    public String getName() {
        return "Lamp";
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.YELLOW);
        g.fillRect(0, 0, width, height);
    }

    @Override
    public boolean containsPoint(Point point) {
        return true; // Always place at the clicked position
    }

    @Override
    public myFurnitureObject createCopyAtPosition(Point position) {
        return new myLamp();
    }
}
