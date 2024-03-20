package FurnitureObjects.Bedroom;

import Templates.FurnitureObject;
import Templates.IconObject;
import Utils.PathConverter;

import java.awt.*;

public class RegularBed extends IconObject {
    private static final String IMAGE_PATH =
            PathConverter.convertPathBasedOnOS("resources/033-double-bed.png");
    private static final String NAME = "Regular Bed";

    public RegularBed(int x, int y) {
        super(x, y);
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    // empty constructor used for creating rightPanel boxes
    public RegularBed() {
        super();
        setImagePath(IMAGE_PATH);
        setName(NAME);
    }

    @Override
    public FurnitureObject createObjectAtPosition(Point position) {
        return new RegularBed(position.x, position.y);
    }

    @Override
    public void setSmall() {
        setSize(40);
    }

    @Override
    public void setMedium() {
        setSize(50);
    }

    @Override
    public void setLarge() {
        setSize(60);
    }
}
