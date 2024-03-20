package FurnitureObjects.DesksNTables;

import Templates.FurnitureObject;
import Templates.IconObject;
import Utils.PathConverter;

import java.awt.*;

public class CoffeeTable4Chairs extends IconObject {
    private static final String IMAGE_PATH =
            PathConverter.convertPathBasedOnOS("resources/006-coffee-table.png");
    private static final String NAME = "Coffee Table (4 Chairs)";

    public CoffeeTable4Chairs(int x, int y) {
        super(x, y);
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    // empty constructor used for creating rightPanel boxes
    public CoffeeTable4Chairs() {
        super();
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    @Override
    public FurnitureObject createObjectAtPosition(Point position) {
        return new CoffeeTable4Chairs(position.x, position.y);
    }

    @Override
    public void setSmall() {
        setSize(70);
    }

    @Override
    public void setMedium() {
        setSize(80);
    }

    @Override
    public void setLarge() {
        setSize(90);
    }
}
