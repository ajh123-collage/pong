package uk.minersonline.Pong.utils;

import java.awt.*;

public interface Collidable {
    Point getPos();
    Dimension getSize();
    Point getTopLeft();
    Point getBottomRight();
}
