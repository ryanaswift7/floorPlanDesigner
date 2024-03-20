package FurnitureObjects.DesksNTables;

import Templates.FurnitureObject;
import Templates.IconObject;
import Utils.PathConverter;

import java.awt.*;

public class LDesk extends IconObject {
    private static final String IMAGE_PATH =
            PathConverter.convertPathBasedOnOS("resources/005-desk.png");
    private static final String NAME = "L-Desk";

    public LDesk(int x, int y) {
        super(x, y);
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    // empty constructor used for creating rightPanel boxes
    public LDesk() {
        super();
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    @Override
    public FurnitureObject createObjectAtPosition(Point position) {
        return new LDesk(position.x, position.y);
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
