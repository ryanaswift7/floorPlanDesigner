package FurnitureObjects.Bedroom;

import Templates.FurnitureObject;
import Templates.IconObject;
import Utils.PathConverter;

import java.awt.*;

public class Wardrobe extends IconObject {
    private static final String IMAGE_PATH =
            PathConverter.convertPathBasedOnOS("resources/004-wardrobe.png");
    private static final String NAME = "Wardrobe";

    public Wardrobe(int x, int y) {
        super(x, y);
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    // empty constructor used for creating rightPanel boxes
    public Wardrobe() {
        super();
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    @Override
    public FurnitureObject createObjectAtPosition(Point position) {
        return new Wardrobe(position.x, position.y);
    }

    @Override
    public void setSmall() {
        setSize(30);
    }

    @Override
    public void setMedium() {
        setSize(40);
    }

    @Override
    public void setLarge() {
        setSize(50);
    }
}
