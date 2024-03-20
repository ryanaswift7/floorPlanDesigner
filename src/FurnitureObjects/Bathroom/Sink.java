package FurnitureObjects.Bathroom;

import Templates.FurnitureObject;
import Templates.IconObject;
import Utils.PathConverter;

import java.awt.*;

public class Sink extends IconObject {
    private static final String IMAGE_PATH =
            PathConverter.convertPathBasedOnOS("resources/001-bidet.png");
    private static final String NAME = "Sink";

    public Sink(int x, int y) {
        super(x, y);
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    // empty constructor used for creating rightPanel boxes
    public Sink() {
        super();
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    @Override
    public FurnitureObject createObjectAtPosition(Point position) {
        return new Sink(position.x, position.y);
    }

    @Override
    public void setSmall() {
        setSize(25);
    }

    @Override
    public void setMedium() {
        setSize(30);
    }

    @Override
    public void setLarge() {
        setSize(35);
    }
}
