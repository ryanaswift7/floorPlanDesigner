package FurnitureObjects.Bathroom;

import Templates.FurnitureObject;
import Templates.IconObject;
import Utils.PathConverter;

import java.awt.*;

public class Shower extends IconObject {
    private static final String IMAGE_PATH =
            PathConverter.convertPathBasedOnOS("resources/010-shower.png");
    private static final String NAME = "Shower";

    public Shower(int x, int y) {
        super(x, y);
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    // empty constructor used for creating rightPanel boxes
    public Shower() {
        super();
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    @Override
    public FurnitureObject createObjectAtPosition(Point position) {
        return new Shower(position.x, position.y);
    }

    @Override
    public void setSmall() {
        setSize(35);
    }

    @Override
    public void setMedium() {
        setSize(40);
    }

    @Override
    public void setLarge() {
        setSize(45);
    }
}
