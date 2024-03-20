package FurnitureObjects.Essentials;
import Templates.*;
import Utils.PathConverter;

import java.awt.*;

public class Candelabrum extends IconObject {
    private static final String IMAGE_PATH =
            PathConverter.convertPathBasedOnOS("resources/007-candelabrum.png");
    private static final String NAME = "Candelabrum";

    public Candelabrum(int x, int y) {
        super(x, y);
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    // empty constructor used for creating rightPanel boxes
    public Candelabrum() {
        super();
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    @Override
    public FurnitureObject createObjectAtPosition(Point position) {
        return new Candelabrum(position.x, position.y);
    }

    @Override
    public void setSmall() {
        setSize(30);
    }

    @Override
    public void setMedium() {
        setSize(35);
    }

    @Override
    public void setLarge() {
        setSize(40);
    }
}