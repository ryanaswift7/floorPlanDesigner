package FurnitureObjects.Kitchen;
import Templates.*;
import Utils.PathConverter;

import java.awt.*;

public class SmallKitchen extends IconObject {
    private static final String IMAGE_PATH =
            PathConverter.convertPathBasedOnOS("resources/023-kitchen-1.png");
    private static final String NAME = "Small Kitchen";

    public SmallKitchen(int x, int y) {
        super(x, y);
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    // empty constructor used for creating rightPanel boxes
    public SmallKitchen() {
        super();
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    @Override
    public FurnitureObject createObjectAtPosition(Point position) {
        return new SmallKitchen(position.x, position.y);
    }

    @Override
    public void setSmall() {
        setSize(40);
    }

    @Override
    public void setMedium() {
        setSize(60);
    }

    @Override
    public void setLarge() {
        setSize(80);
    }
}

