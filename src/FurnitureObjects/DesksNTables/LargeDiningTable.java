package FurnitureObjects.DesksNTables;

import Templates.FurnitureObject;
import Templates.IconObject;
import Utils.PathConverter;

import java.awt.*;

public class LargeDiningTable extends IconObject {
    private static final String IMAGE_PATH =
            PathConverter.convertPathBasedOnOS("resources/025-table-1.png");
    private static final String NAME = "Large Dining Table";

    public LargeDiningTable(int x, int y) {
        super(x, y);
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    // empty constructor used for creating rightPanel boxes
    public LargeDiningTable() {
        super();
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    @Override
    public FurnitureObject createObjectAtPosition(Point position) {
        return new LargeDiningTable(position.x, position.y);
    }

    @Override
    public void setSmall() {
        setSize(60);
    }

    @Override
    public void setMedium() {
        setSize(80);
    }

    @Override
    public void setLarge() {
        setSize(100);
    }
}
