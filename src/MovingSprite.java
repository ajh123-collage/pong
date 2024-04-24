import java.awt.*;

import static utils.Constants.*;

public abstract class MovingSprite extends Sprite {
    protected double dx;
    protected double dy;

    public MovingSprite(String imagePath, int x, int y) {
        super(imagePath, x, y);
    }

    public MovingSprite(String imagePath, int x, int y, int width, int height) {
        super(imagePath, x, y, width, height);
    }

    public final void handleCollision(Sprite other) {
        Point previousPos = new Point(pos.x - (int) dx, pos.y - (int) dy);

        if (dx > 0 && previousPos.x + size.width <= other.getTopLeft().x) {
            pos.x = other.getTopLeft().x - size.width;
        }
        else if (dx < 0 && previousPos.x >= other.getBottomRight().x) {
            pos.x = other.getBottomRight().x;
        }

        if (dy > 0 && previousPos.y + size.height <= other.getTopLeft().y) {
            pos.y = other.getTopLeft().y - size.height;
        }
        else if (dy < 0 && previousPos.y >= other.getBottomRight().y) {
            pos.y = other.getBottomRight().y;
        }

        postCollide(other);
    }

    abstract void postCollide(Collidable other);

    @Override
    public void tick() {
        pos.translate((int)dx, (int)dy);

        pos.x = Math.clamp(pos.x, -1, (BOARD_WIDTH + 1));
        pos.y = Math.clamp(pos.y, -1, (BOARD_HEIGHT + 1));
    }
}
