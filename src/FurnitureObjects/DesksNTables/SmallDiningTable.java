package FurnitureObjects.DesksNTables;

import Templates.FurnitureObject;
import Templates.IconObject;
import Utils.PathConverter;

import java.awt.*;

public class SmallDiningTable extends IconObject {
    private static final String IMAGE_PATH =
            PathConverter.convertPathBasedOnOS("resources/024-table.png");
    private static final String NAME = "Small Dining Table";

    public SmallDiningTable(int x, int y) {
        super(x, y);
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    // empty constructor used for creating rightPanel boxes
    public SmallDiningTable() {
        super();
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    @Override
    public FurnitureObject createObjectAtPosition(Point position) {
        return new SmallDiningTable(position.x, position.y);
    }

    @Override
    public void setSmall() {
        setSize(60);
    }

    @Override
    public void setMedium() {
        setSize(75);
    }

    @Override
    public void setLarge() {
        setSize(90);
    }
}
