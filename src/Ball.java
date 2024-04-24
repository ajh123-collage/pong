import utils.Side;

import static utils.Constants.*;

public class Ball extends MovingSprite {
//    private double speedMultiplier;

    public Ball(int x, int y) {
        super(BALL_IMAGE_PATH, x, y, BALL_WIDTH, BALL_HEIGHT);
        dx = -BALL_SPEED;
//        speedMultiplier = 1;
    }

    @Override
    void postCollide(Collidable other) {
        handleWall(other);
        handlePaddle(other);
//        speedMultiplier += 0.25;
//
//        dx = dx * speedMultiplier;
//        dy = dy * speedMultiplier;
    }

    private void handleWall(Collidable collidable) {
        if (collidable instanceof Wall wall) {
            Side side = wall.getSide();
            switch (side) {
                case TOP -> {
                    dy = BALL_SPEED;
                    dx = -BALL_SPEED;
                }
                case BOTTOM -> {
                    dy = -BALL_SPEED;
                    dx = BALL_SPEED;
                }
                case RIGHT -> {
                    dx = -BALL_SPEED;
                    dy = BALL_SPEED;
                }
                case LEFT -> {
                    dx = BALL_SPEED;
                    dy = BALL_SPEED;
                }
            }
        }
    }

    private void handlePaddle(Collidable collidable) {
        if (collidable instanceof Player player) {
            if (player.getBottomRight().x <= pos.x) {
                dx = BALL_SPEED;
                dy = BALL_SPEED;
            }
            if (player.getPos().x >= pos.x) {
                dx = -BALL_SPEED;
                dy = -BALL_SPEED;
            }
            if (player.getBottomRight().y <= pos.y) {
                dy = BALL_SPEED;
                dx = BALL_SPEED;
            }
            if (player.getPos().y >= pos.y) {
                dy = -BALL_SPEED;
                dx = -BALL_SPEED;
            }
        }
    }
}
