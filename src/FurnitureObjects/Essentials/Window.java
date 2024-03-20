package FurnitureObjects.Essentials;

import Templates.BoundaryLineObject;
import Templates.FurnitureObject;
import Templates.Movable;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Window extends BoundaryLineObject {
    private static final String name = "Window";
    private static final float strokeWidth = 8;
    private static final BasicStroke stroke = new BasicStroke(strokeWidth);
    private static final Color color = Color.BLUE;

    public Window(int x1, int y1, int x2, int y2) {
        super(x1, y1, x2, y2);
        setName(name);
        setColor(color);
        setStrokeWidth(strokeWidth);
        setStroke(stroke);
    }
    public Window() {
        super();
        setName(name);
        setColor(color);
        setStrokeWidth(strokeWidth);
        setStroke(stroke);
    }
    @Override
    public FurnitureObject createObjectAtPosition(Point position) {
        return new Window(position.x, position.y, position.x, position.y + 5);
    }
}
