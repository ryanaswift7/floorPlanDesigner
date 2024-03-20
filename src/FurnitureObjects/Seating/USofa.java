package FurnitureObjects.Seating;

import Templates.FurnitureObject;
import Templates.IconObject;
import Utils.PathConverter;

import java.awt.*;

public class USofa extends IconObject {
    private static final String IMAGE_PATH =
            PathConverter.convertPathBasedOnOS("resources/005-sofa.png");
    private static final String NAME = "U-Sofa";

    public USofa(int x, int y) {
        super(x, y);
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    // empty constructor used for creating rightPanel boxes
    public USofa() {
        super();
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    @Override
    public FurnitureObject createObjectAtPosition(Point position) {
        return new USofa(position.x, position.y);
    }

    @Override
    public void setSmall() {
        setSize(90);
    }

    @Override
    public void setMedium() {
        setSize(110);
    }

    @Override
    public void setLarge() {
        setSize(130);
    }
}
