package FurnitureObjects.Essentials;

import Templates.FurnitureObject;
import Templates.IconObject;
import Utils.PathConverter;

import java.awt.*;

public class Chandelier extends IconObject {
    private static final String IMAGE_PATH =
            PathConverter.convertPathBasedOnOS("resources/009-chandelier.png");
    private static final String NAME = "Chandelier";

    public Chandelier(int x, int y) {
        super(x, y);
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    // empty constructor used for creating rightPanel boxes
    public Chandelier() {
        super();
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    @Override
    public FurnitureObject createObjectAtPosition(Point position) {
        return new Chandelier(position.x, position.y);
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
        setSize(60);
    }
}
