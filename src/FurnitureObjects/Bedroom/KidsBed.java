package FurnitureObjects.Bedroom;

import Templates.FurnitureObject;
import Templates.IconObject;
import Utils.PathConverter;

import java.awt.*;

public class KidsBed extends IconObject {
    private static final String IMAGE_PATH =
            PathConverter.convertPathBasedOnOS("resources/008-bed.png");
    private static final String NAME = "Kids Bed";

    public KidsBed(int x, int y) {
        super(x, y);
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    // empty constructor used for creating rightPanel boxes
    public KidsBed() {
        super();
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    @Override
    public FurnitureObject createObjectAtPosition(Point position) {
        return new KidsBed(position.x, position.y);
    }

    @Override
    public void setSmall() {
        setSize(45);
    }

    @Override
    public void setMedium() {
        setSize(50);
    }

    @Override
    public void setLarge() {
        setSize(55);
    }
}
