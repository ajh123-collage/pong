import utils.Side;

import static utils.Constants.*;

public class Ball extends MovingSprite {
    private double speedMultiplier;
    private int vx;
    private int vy;

    public Ball(int x, int y) {
        super(BALL_IMAGE_PATH, x, y, BALL_WIDTH, BALL_HEIGHT);
        speedMultiplier = 0.4;
        vx = -1;
        vy = 1;
    }

    @Override
    void postCollide(Collidable other) {
        handleWall(other);
        handlePaddle(other);
        speedMultiplier += 0.005;
    }

    @Override
    public void tick() {
        dx = vx * BALL_SPEED * speedMultiplier;
        dy = vy * BALL_SPEED * speedMultiplier;

        super.tick();
    }

    private void handleWall(Collidable collidable) {
        if (collidable instanceof Wall wall) {
            Side side = wall.getSide();
            switch (side) {
                case TOP, BOTTOM -> {
                    vy = -vy;
                }
                case RIGHT, LEFT -> {
                    vx = -vx;
                }
            }
        }
    }

    private void handlePaddle(Collidable collidable) {
        if (collidable instanceof Player player) {
            if (player.getBottomRight().x <= pos.x) {
                vx = -vx;
            }
            if (player.getPos().x >= pos.x) {
                vx = -vx;
            }
        }
    }
}
