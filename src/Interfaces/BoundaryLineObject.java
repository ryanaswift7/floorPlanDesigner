package Interfaces;

import java.awt.*;

public interface BoundaryLineObject extends FurnitureObject {
    void snapToGrid();
    void updateEndpoint(Point point);
}
