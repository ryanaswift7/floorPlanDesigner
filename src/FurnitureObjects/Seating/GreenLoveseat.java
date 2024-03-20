package FurnitureObjects.Seating;

import Templates.FurnitureObject;
import Templates.IconObject;
import Utils.PathConverter;

import java.awt.*;

public class GreenLoveseat extends IconObject {
    private static final String IMAGE_PATH =
            PathConverter.convertPathBasedOnOS("resources/007-sofa-1.png");
    private static final String NAME = "Green Loveseat";

    public GreenLoveseat(int x, int y) {
        super(x, y);
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    // empty constructor used for creating rightPanel boxes
    public GreenLoveseat() {
        super();
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    @Override
    public FurnitureObject createObjectAtPosition(Point position) {
        return new GreenLoveseat(position.x, position.y);
    }

    @Override
    public void setSmall() {
        setSize(45);
    }

    @Override
    public void setMedium() {
        setSize(55);
    }

    @Override
    public void setLarge() {
        setSize(65);
    }
}
