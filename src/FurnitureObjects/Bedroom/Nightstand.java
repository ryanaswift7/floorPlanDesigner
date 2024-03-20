package FurnitureObjects.Bedroom;

import Templates.FurnitureObject;
import Templates.IconObject;
import Utils.PathConverter;

import java.awt.*;

public class Nightstand extends IconObject {
    private static final String IMAGE_PATH =
            PathConverter.convertPathBasedOnOS("resources/021-night-stand.png");
    private static final String NAME = "Nightstand";

    public Nightstand(int x, int y) {
        super(x, y);
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    // empty constructor used for creating rightPanel boxes
    public Nightstand() {
        super();
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    @Override
    public FurnitureObject createObjectAtPosition(Point position) {
        return new Nightstand(position.x, position.y);
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
