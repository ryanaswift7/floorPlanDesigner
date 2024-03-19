package Utils;

import javax.swing.*;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ComplimentGenerator {
    private static final String[] compliments = {
            "Wow, that's so good!",
            "What an amazing idea!",
            "You're incredibly smart!",
            "That's absolutely brilliant!",
            "You're a creative genius!",
            "You're doing fantastic!",
            "Your skills and creativity are unmatched!",
            "You're really inspiring!",
            "You're making great progress!",
            "You're unstoppable!",
            "You're on fire!",
            "You're an inspiration to us all!",
            "You're truly outstanding!",
            "You're incredibly talented!",
            "You're exceptional!",
            "You're a star!",
            "You're awesome!",
            "You're fantastic!",
            "You're remarkable!",
            "You're extraordinary!"
    };

    private ScheduledExecutorService scheduler;
    private JTextField textField;
    private int currentIndex;

    public ComplimentGenerator(JTextField textField) {
        this.textField = textField;
        scheduler = Executors.newSingleThreadScheduledExecutor();
        currentIndex = -1;
    }

    public void startGeneratingCompliments() {
        // Set the initial compliment and delay the start of scheduled generation
        textField.setText("Welcome to the Interactive Floor Plan Designer!");
        scheduler.schedule(() -> {
            // Start generating compliments every 15 seconds
            scheduler.scheduleAtFixedRate(() -> {
                String compliment = getRandomCompliment();
                SwingUtilities.invokeLater(() -> textField.setText(compliment));
            }, 0, 30, TimeUnit.SECONDS);
        }, 30, TimeUnit.SECONDS);
    }

    private String getRandomCompliment() {
        Random random = new Random();
        int index = random.nextInt(compliments.length);
        return compliments[index];
    }
}
