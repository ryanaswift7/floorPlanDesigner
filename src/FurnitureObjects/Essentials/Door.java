package FurnitureObjects.Essentials;

import Templates.FurnitureObject;
import Templates.IconObject;
import Utils.PathConverter;

import java.awt.*;

public class Door extends IconObject {
    private static final String IMAGE_PATH =
            PathConverter.convertPathBasedOnOS("resources/door.png");
    private static final String NAME = "Door";

    public Door(int x, int y) {
        super(x, y);
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    // empty constructor used for creating rightPanel boxes
    public Door() {
        super();
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    @Override
    public FurnitureObject createObjectAtPosition(Point position) {
        return new Door(position.x, position.y);
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
