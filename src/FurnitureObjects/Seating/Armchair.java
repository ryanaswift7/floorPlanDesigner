package FurnitureObjects.Seating;

import Templates.FurnitureObject;
import Templates.IconObject;
import Utils.PathConverter;

import java.awt.*;

public class Armchair extends IconObject {
    private static final String IMAGE_PATH =
            PathConverter.convertPathBasedOnOS("resources/012-armchair.png");
    private static final String NAME = "Armchair";

    public Armchair(int x, int y) {
        super(x, y);
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    // empty constructor used for creating rightPanel boxes
    public Armchair() {
        super();
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    @Override
    public FurnitureObject createObjectAtPosition(Point position) {
        return new Armchair(position.x, position.y);
    }

    @Override
    public void setSmall() {
        setSize(30);
    }

    @Override
    public void setMedium() {
        setSize(35);
    }

    @Override
    public void setLarge() {
        setSize(40);
    }
}
