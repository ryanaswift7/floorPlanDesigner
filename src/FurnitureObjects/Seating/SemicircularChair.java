package FurnitureObjects.Seating;

import Templates.FurnitureObject;
import Templates.IconObject;
import Utils.PathConverter;

import java.awt.*;

public class SemicircularChair extends IconObject {
    private static final String IMAGE_PATH =
            PathConverter.convertPathBasedOnOS("resources/018-chair.png");
    private static final String NAME = "Semicircular Chair";

    public SemicircularChair(int x, int y) {
        super(x, y);
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    // empty constructor used for creating rightPanel boxes
    public SemicircularChair() {
        super();
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    @Override
    public FurnitureObject createObjectAtPosition(Point position) {
        return new SemicircularChair(position.x, position.y);
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
