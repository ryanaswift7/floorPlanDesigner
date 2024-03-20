package FurnitureObjects.Seating;

import Templates.FurnitureObject;
import Templates.IconObject;
import Utils.PathConverter;

import java.awt.*;

public class LSofa extends IconObject {
    private static final String IMAGE_PATH =
            PathConverter.convertPathBasedOnOS("resources/022-sofa-4.png");
    private static final String NAME = "L-Sofa";

    public LSofa(int x, int y) {
        super(x, y);
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    // empty constructor used for creating rightPanel boxes
    public LSofa() {
        super();
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    @Override
    public FurnitureObject createObjectAtPosition(Point position) {
        return new LSofa(position.x, position.y);
    }

    @Override
    public void setSmall() {
        setSize(50);
    }

    @Override
    public void setMedium() {
        setSize(70);
    }

    @Override
    public void setLarge() {
        setSize(90);
    }
}
