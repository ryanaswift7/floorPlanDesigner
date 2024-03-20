package Panels;
import Utils.ComplimentGenerator;

import javax.swing.*;
import java.awt.*;

public class TopPanel extends JPanel {

    private static TopPanel instance; // Singleton instance
    private static JTextField messageField;

    private TopPanel() {
        messageField = new JTextField();
        messageField.setEditable(false); // Make the text field read-only
        messageField.setPreferredSize(new Dimension(200, 20));

        JButton clearCanvasButton = new JButton("Clear");
        clearCanvasButton.addActionListener(e -> CanvasPanel.getInstance().clearCanvas());

        JButton addItemButton = new JButton("Add Item");
        addItemButton.addActionListener(e -> {
            JPopupMenu addItemMenu = new JPopupMenu();
            JMenuItem essentialsItem = new JMenuItem("Essentials");
            JMenuItem kitchenItem = new JMenuItem("Kitchen");
            JMenuItem bathroomItem = new JMenuItem("Bathroom");
            JMenuItem bedroomItem = new JMenuItem("Bedroom");
            JMenuItem seatingItem = new JMenuItem("Seating");
            JMenuItem deskNtableItem = new JMenuItem("Desks & Tables");
            JMenuItem rugItem = new JMenuItem("Rugs");
            JMenuItem funItem = new JMenuItem("Fun!");

            RightPanel rp = RightPanel.getInstance();
            essentialsItem.addActionListener(actionEvent -> {
                rp.addItems(RightPanel.getEssentialsItems());
            });

            kitchenItem.addActionListener(actionEvent -> {
                rp.addItems(RightPanel.getKitchenItems());
            });

            bathroomItem.addActionListener(actionEvent -> {
                rp.addItems(RightPanel.getBathroomItems());
            });
            bedroomItem.addActionListener(actionEvent -> {
                rp.addItems(RightPanel.getBedroomItems());
            });

            seatingItem.addActionListener(actionEvent -> {
                rp.addItems(RightPanel.getSeatingItems());
            });

            deskNtableItem.addActionListener(actionEvent -> {
                rp.addItems(RightPanel.getDeskNTableItems());
            });

            rugItem.addActionListener(actionEvent -> {
                rp.addItems(RightPanel.getRugItems());
            });

            funItem.addActionListener(actionEvent -> {
                rp.addItems(RightPanel.getFunItems());
            });

            addItemMenu.add(essentialsItem);
            addItemMenu.add(kitchenItem);
            addItemMenu.add(bathroomItem);
            addItemMenu.add(bedroomItem);
            addItemMenu.add(seatingItem);
            addItemMenu.add(deskNtableItem);
            addItemMenu.add(rugItem);
            addItemMenu.add(funItem);

            addItemMenu.show(addItemButton, 0, addItemButton.getHeight());
        });
        //JPanel leftPanel = new JPanel(); // Create a panel to hold the button
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add(Box.createHorizontalStrut(10));
        add(clearCanvasButton);
        add(Box.createHorizontalStrut(20));
        add(addItemButton);
        add(Box.createHorizontalStrut(20));
        add(messageField);
        // Create an instance of ComplimentGenerator and start generating compliments
        ComplimentGenerator generator = new ComplimentGenerator(messageField);
        generator.startGeneratingCompliments();

        add(messageField);
    }

    public static synchronized TopPanel getInstance() {
        if (instance == null) {
            instance = new TopPanel();
        }
        return instance;
    }

    public static void setMessage(String message) {
        messageField.setText(message);
    }
}
