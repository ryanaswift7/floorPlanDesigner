package FurnitureObjects.Rugs;

import Templates.FurnitureObject;
import Templates.IconObject;
import Utils.PathConverter;

import java.awt.*;

public class CircleRug extends IconObject {
    private static final String IMAGE_PATH =
            PathConverter.convertPathBasedOnOS("resources/030-carpet-1.png");
    private static final String NAME = "Circle Rug";

    public CircleRug(int x, int y) {
        super(x, y);
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    // empty constructor used for creating rightPanel boxes
    public CircleRug() {
        super();
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    @Override
    public FurnitureObject createObjectAtPosition(Point position) {
        return new CircleRug(position.x, position.y);
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
