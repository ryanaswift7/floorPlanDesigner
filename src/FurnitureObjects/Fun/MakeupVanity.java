package FurnitureObjects.Fun;

import Templates.FurnitureObject;
import Templates.IconObject;
import Utils.PathConverter;

import java.awt.*;

public class MakeupVanity extends IconObject {
    private static final String IMAGE_PATH =
            PathConverter.convertPathBasedOnOS("resources/026-dressing-table.png");
    private static final String NAME = "Makeup Vanity";

    public MakeupVanity(int x, int y) {
        super(x, y);
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    // empty constructor used for creating rightPanel boxes
    public MakeupVanity() {
        super();
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    @Override
    public FurnitureObject createObjectAtPosition(Point position) {
        return new MakeupVanity(position.x, position.y);
    }

    @Override
    public void setSmall() {
        setSize(40);
    }

    @Override
    public void setMedium() {
        setSize(60);
    }

    @Override
    public void setLarge() {
        setSize(80);
    }
}
