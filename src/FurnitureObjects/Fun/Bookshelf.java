package FurnitureObjects.Fun;

import Templates.FurnitureObject;
import Templates.IconObject;
import Utils.PathConverter;

import java.awt.*;

public class Bookshelf extends IconObject {
    private static final String IMAGE_PATH =
            PathConverter.convertPathBasedOnOS("resources/002-bookshelf.png");
    private static final String NAME = "Bookshelf";

    public Bookshelf(int x, int y) {
        super(x, y);
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    // empty constructor used for creating rightPanel boxes
    public Bookshelf() {
        super();
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    @Override
    public FurnitureObject createObjectAtPosition(Point position) {
        return new Bookshelf(position.x, position.y);
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
