package FurnitureObjects.Essentials;

import Templates.FurnitureObject;
import Templates.IconObject;
import Utils.PathConverter;

import java.awt.*;

public class Lamp extends IconObject {
    private static final String IMAGE_PATH =
            PathConverter.convertPathBasedOnOS("resources/008-lamp.png");
    private static final String NAME = "Lamp";

    public Lamp(int x, int y) {
        super(x, y);
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    // empty constructor used for creating rightPanel boxes
    public Lamp() {
        super();
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    @Override
    public FurnitureObject createObjectAtPosition(Point position) {
        return new Lamp(position.x, position.y);
    }

    @Override
    public void setSmall() {
        setSize(20);
    }

    @Override
    public void setMedium() {
        setSize(30);
    }

    @Override
    public void setLarge() {
        setSize(40);
    }
}
