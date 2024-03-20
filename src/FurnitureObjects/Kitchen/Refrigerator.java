package FurnitureObjects.Kitchen;

import Templates.FurnitureObject;
import Templates.IconObject;
import Utils.PathConverter;

import java.awt.*;

public class Refrigerator extends IconObject {
    private static final String IMAGE_PATH =
            PathConverter.convertPathBasedOnOS("resources/003-fridge.png");
    private static final String NAME = "Refrigerator";

    public Refrigerator(int x, int y) {
        super(x, y);
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    // empty constructor used for creating rightPanel boxes
    public Refrigerator() {
        super();
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    @Override
    public FurnitureObject createObjectAtPosition(Point position) {
        return new Refrigerator(position.x, position.y);
    }

    @Override
    public void setSmall() {
        setSize(25);
    }

    @Override
    public void setMedium() {
        setSize(35);
    }

    @Override
    public void setLarge() {
        setSize(45);
    }
}
