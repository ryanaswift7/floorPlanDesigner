package FurnitureObjects.Essentials;

import Templates.FurnitureObject;
import Templates.IconObject;
import Utils.PathConverter;

import java.awt.*;

public class Plant extends IconObject {
    private static final String IMAGE_PATH =
            PathConverter.convertPathBasedOnOS("resources/004-plant.png");
    private static final String NAME = "Plant";

    public Plant(int x, int y) {
        super(x, y);
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    // empty constructor used for creating rightPanel boxes
    public Plant() {
        super();
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    @Override
    public FurnitureObject createObjectAtPosition(Point position) {
        return new Plant(position.x, position.y);
    }

    @Override
    public void setSmall() {
        setSize(20);
    }

    @Override
    public void setMedium() {
        setSize(25);
    }

    @Override
    public void setLarge() {
        setSize(30);
    }
}
