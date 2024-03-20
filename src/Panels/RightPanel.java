package Panels;
import FurnitureObjects.Bathroom.Bath;
import FurnitureObjects.Bathroom.Shower;
import FurnitureObjects.Bathroom.Sink;
import FurnitureObjects.Bathroom.Toilet;
import FurnitureObjects.Bedroom.*;
import FurnitureObjects.DesksNTables.*;
import FurnitureObjects.Essentials.*;
import FurnitureObjects.Fun.*;
import FurnitureObjects.Kitchen.BreakfastNook;
import FurnitureObjects.Kitchen.LargeKitchen;
import FurnitureObjects.Kitchen.Refrigerator;
import FurnitureObjects.Kitchen.SmallKitchen;
import FurnitureObjects.Rugs.CircleRug;
import FurnitureObjects.Rugs.RectangleRug;
import FurnitureObjects.Seating.*;
import Templates.FurnitureObject;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

public class RightPanel extends JPanel {

    private static RightPanel instance; // Singleton instance
    private static ArrayList<JButton> rightButtons;

    private RightPanel() {
        setLayout(new GridLayout(0, 1));
        rightButtons = new ArrayList<>();
    }

    public static synchronized RightPanel getInstance() {
        if (instance == null) {
            instance = new RightPanel();
        }
        return instance;
    }

    public void addItems(ArrayList<FurnitureObject> items) {
        removeAll();
        for (FurnitureObject item : items) {
            JButton itemButton = new JButton();
            // Configure item button
            itemButton.setLayout(new BorderLayout());
            itemButton.add(new JLabel(new ImageIcon(createImageForItemMenu(item))), BorderLayout.CENTER);
            itemButton.add(new JLabel(item.getName(), SwingConstants.CENTER), BorderLayout.SOUTH);
            itemButton.addActionListener(e -> {
                CanvasPanel.setCurrentObjectToPlace(item);
                RightPanel.resetButtonColors(); // Reset all button colors
                itemButton.setBackground(Color.YELLOW); // Highlight the clicked button
            });
            add(itemButton);
            rightButtons.add(itemButton);
        }
        revalidate();
        repaint();
    }

    private Image createImageForItemMenu(FurnitureObject object) {
        Image image = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) image.getGraphics();
        object.draw(g2d);
        g2d.dispose();
        return image;
    }

    public static void resetButtonColors() {
        for (JButton button : rightButtons) {
            button.setBackground(null);
        }
    }
    public static ArrayList<FurnitureObject> getEssentialsItems() {
        return new ArrayList<>(Arrays.asList(new Door(), new DoubleDoor(), new Wall(), new Mirror(),
                new FurnitureObjects.Essentials.Window(),
                new Chandelier(), new Lamp(), new Candelabrum(),
                new Plant()));
    }

    public static ArrayList<FurnitureObject> getKitchenItems() {
        return new ArrayList<>(Arrays.asList(new SmallKitchen(), new LargeKitchen(), new Refrigerator(),
                new BreakfastNook()));
    }

    public static ArrayList<FurnitureObject> getBathroomItems() {
        return new ArrayList<>(Arrays.asList(new Sink(), new Shower(), new Toilet(), new Bath()));
    }

    public static ArrayList<FurnitureObject> getBedroomItems() {
        return new ArrayList<>(Arrays.asList(new LargeBed(), new KidsBed(), new RegularBed(),
                new CircularBed(), new Wardrobe(), new Nightstand()));
    }

    public static ArrayList<FurnitureObject> getSeatingItems() {
        return new ArrayList<>(Arrays.asList(new Sectional(), new GreenLoveseat(), new YellowLoveseat(),
                new LSofa(), new USofa(), new StraightSofa(),
                new SemicircularChair(), new Armchair()));
    }
    public static ArrayList<FurnitureObject> getDeskNTableItems() {
        return new ArrayList<>(Arrays.asList(new LDesk(), new RegularDesk(),new CoffeeTable4Chairs(),
                new CoffeeTable2Chairs(), new CoffeeTable(),
                new SmallDiningTable(), new LargeDiningTable()));
    }
    public static ArrayList<FurnitureObject> getRugItems() {
        return new ArrayList<>(Arrays.asList(new RectangleRug(), new CircleRug()));
    }
    public static ArrayList<FurnitureObject> getFunItems() {
        return new ArrayList<>(Arrays.asList(new Car(), new Bookshelf(), new TV(),
                new MakeupVanity(), new Synthesizer()));
    }
}
