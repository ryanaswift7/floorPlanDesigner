package FurnitureObjects.Fun;

import Templates.FurnitureObject;
import Templates.IconObject;
import Utils.PathConverter;

import java.awt.*;

public class Car extends IconObject {
    private static final String IMAGE_PATH =
            PathConverter.convertPathBasedOnOS("resources/001-car.png");
    private static final String NAME = "Car";

    public Car(int x, int y) {
        super(x, y);
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    // empty constructor used for creating rightPanel boxes
    public Car() {
        super();
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    @Override
    public FurnitureObject createObjectAtPosition(Point position) {
        return new Car(position.x, position.y);
    }

    @Override
    public void setSmall() {
        setSize(90);
    }

    @Override
    public void setMedium() {
        setSize(105);
    }

    @Override
    public void setLarge() {
        setSize(120);
    }
}
