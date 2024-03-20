package FurnitureObjects.Seating;

import Templates.FurnitureObject;
import Templates.IconObject;
import Utils.PathConverter;

import java.awt.*;

public class YellowLoveseat extends IconObject {
    private static final String IMAGE_PATH =
            PathConverter.convertPathBasedOnOS("resources/020-sofa-3.png");
    private static final String NAME = "Yellow Loveseat";

    public YellowLoveseat(int x, int y) {
        super(x, y);
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    // empty constructor used for creating rightPanel boxes
    public YellowLoveseat() {
        super();
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    @Override
    public FurnitureObject createObjectAtPosition(Point position) {
        return new YellowLoveseat(position.x, position.y);
    }

    @Override
    public void setSmall() {
        setSize(50);
    }

    @Override
    public void setMedium() {
        setSize(60);
    }

    @Override
    public void setLarge() {
        setSize(70);
    }
}
