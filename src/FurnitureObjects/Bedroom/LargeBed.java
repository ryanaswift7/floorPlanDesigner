package FurnitureObjects.Bedroom;

import Templates.FurnitureObject;
import Templates.IconObject;
import Utils.PathConverter;

import java.awt.*;

public class LargeBed extends IconObject {
    private static final String IMAGE_PATH =
            PathConverter.convertPathBasedOnOS("resources/002-bed.png");
    private static final String NAME = "Large Bed";

    public LargeBed(int x, int y) {
        super(x, y);
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    // empty constructor used for creating rightPanel boxes
    public LargeBed() {
        super();
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    @Override
    public FurnitureObject createObjectAtPosition(Point position) {
        return new LargeBed(position.x, position.y);
    }

    @Override
    public void setSmall() {
        setSize(80);
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
