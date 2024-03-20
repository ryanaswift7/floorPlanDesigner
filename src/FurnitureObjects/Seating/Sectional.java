package FurnitureObjects.Seating;

import Templates.FurnitureObject;
import Templates.IconObject;
import Utils.PathConverter;

import java.awt.*;

public class Sectional extends IconObject {
    private static final String IMAGE_PATH =
            PathConverter.convertPathBasedOnOS("resources/003-sofa.png");
    private static final String NAME = "Sectional";

    public Sectional(int x, int y) {
        super(x, y);
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    // empty constructor used for creating rightPanel boxes
    public Sectional() {
        super();
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    @Override
    public FurnitureObject createObjectAtPosition(Point position) {
        return new Sectional(position.x, position.y);
    }

    @Override
    public void setSmall() {
        setSize(50);
    }

    @Override
    public void setMedium() {
        setSize(70);
    }

    @Override
    public void setLarge() {
        setSize(90);
    }
}
