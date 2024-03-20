package FurnitureObjects.Rugs;

import Templates.FurnitureObject;
import Templates.IconObject;
import Utils.PathConverter;

import java.awt.*;

public class RectangleRug extends IconObject {
    private static final String IMAGE_PATH =
            PathConverter.convertPathBasedOnOS("resources/028-carpet.png");
    private static final String NAME = "Rectangle Rug";

    public RectangleRug(int x, int y) {
        super(x, y);
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    // empty constructor used for creating rightPanel boxes
    public RectangleRug() {
        super();
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    @Override
    public FurnitureObject createObjectAtPosition(Point position) {
        return new RectangleRug(position.x, position.y);
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
