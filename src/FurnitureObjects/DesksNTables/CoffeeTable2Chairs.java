package FurnitureObjects.DesksNTables;

import Templates.FurnitureObject;
import Templates.IconObject;
import Utils.PathConverter;

import java.awt.*;

public class CoffeeTable2Chairs extends IconObject {
    private static final String IMAGE_PATH =
            PathConverter.convertPathBasedOnOS("resources/017-coffee-table-1.png");
    private static final String NAME = "Coffee Table (2 Chairs)";

    public CoffeeTable2Chairs(int x, int y) {
        super(x, y);
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    // empty constructor used for creating rightPanel boxes
    public CoffeeTable2Chairs() {
        super();
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    @Override
    public FurnitureObject createObjectAtPosition(Point position) {
        return new CoffeeTable2Chairs(position.x, position.y);
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
