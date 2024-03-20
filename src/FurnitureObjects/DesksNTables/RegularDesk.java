package FurnitureObjects.DesksNTables;

import Templates.FurnitureObject;
import Templates.IconObject;
import Utils.PathConverter;

import java.awt.*;

public class RegularDesk extends IconObject {
    private static final String IMAGE_PATH =
            PathConverter.convertPathBasedOnOS("resources/013-desk-1.png");
    private static final String NAME = "Regular Desk";

    public RegularDesk(int x, int y) {
        super(x, y);
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    // empty constructor used for creating rightPanel boxes
    public RegularDesk() {
        super();
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    @Override
    public FurnitureObject createObjectAtPosition(Point position) {
        return new RegularDesk(position.x, position.y);
    }

    @Override
    public void setSmall() {
        setSize(55);
    }

    @Override
    public void setMedium() {
        setSize(65);
    }

    @Override
    public void setLarge() {
        setSize(75);
    }
}
