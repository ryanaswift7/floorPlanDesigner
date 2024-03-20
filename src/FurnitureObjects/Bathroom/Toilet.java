package FurnitureObjects.Bathroom;

import Templates.FurnitureObject;
import Templates.IconObject;
import Utils.PathConverter;

import java.awt.*;

public class Toilet extends IconObject {
    private static final String IMAGE_PATH =
            PathConverter.convertPathBasedOnOS("resources/011-toilet.png");
    private static final String NAME = "Toilet";

    public Toilet(int x, int y) {
        super(x, y);
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    // empty constructor used for creating rightPanel boxes
    public Toilet() {
        super();
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    @Override
    public FurnitureObject createObjectAtPosition(Point position) {
        return new Toilet(position.x, position.y);
    }

    @Override
    public void setSmall() {
        setSize(25);
    }

    @Override
    public void setMedium() {
        setSize(30);
    }

    @Override
    public void setLarge() {
        setSize(35);
    }
}
