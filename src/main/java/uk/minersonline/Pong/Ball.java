package uk.minersonline.Pong;

import uk.minersonline.Pong.utils.Collidable;
import uk.minersonline.Pong.utils.GameState;
import uk.minersonline.Pong.utils.Side;

import java.util.concurrent.ThreadLocalRandom;

import static uk.minersonline.Pong.utils.Constants.*;

public class Ball extends MovingSprite {
    public static Side WON_DIR = null;
    private double speedMultiplier;
    private int vx;
    private int vy;
    private boolean allowPaddle = false;

    public Ball(int x, int y) {
        super(BALL_IMAGE_PATH, x, y, BALL_WIDTH, BALL_HEIGHT);
        speedMultiplier = 0.7;

        if (WON_DIR == null) {
            int ranDir = ThreadLocalRandom.current().nextInt(0, 4 + 1);

            if (ranDir == 0) {
                vx = -1;
                vy = 1;
            } else if (ranDir == 1) {
                vx = 1;
                vy = -1;
            } else if (ranDir == 2) {
                vx = -1;
                vy = -1;
            } else {
                vx = 1;
                vy = 1;
            }
        }

        if (WON_DIR != null) {
            switch (WON_DIR) {
                case LEFT -> {
                    vx = -1;
                    vy = 1;
                }
                case RIGHT -> {
                    vx = 1;
                    vy = 1;
                }
            }
        }
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

        Paddle left = Board.getInstance().getLeft();
        Paddle right = Board.getInstance().getRight();

        // Reset `allowPaddle` when the ball is in-between the paddles
        allowPaddle = this.pos.x >= (left.getBottomRight().x - 5) || this.pos.x <= (right.getTopLeft().x + 5);

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
            // We only want to increase score if we are allowed to
            if (allowPaddle) {
                Paddle left = Board.getInstance().getLeft();
                Paddle right = Board.getInstance().getRight();
                if (wall.getSide() == Side.LEFT) {
                    right.setScore(right.getScore() + 1);
                    allowPaddle = false;
                }
                if (wall.getSide() == Side.RIGHT) {
                    left.setScore(left.getScore() + 1);
                    allowPaddle = false;
                }
            }
        }
    }

    private void handlePaddle(Collidable collidable) {
        if (collidable instanceof Paddle paddle) {
            GameState.getInstance().setRally(GameState.getInstance().getRally() + 1);
            if (GameState.getInstance().getRally() > GameState.getInstance().getHighestRally()) {
                GameState.getInstance().setHighestRally(GameState.getInstance().getRally());
            }
            if (paddle.getBottomRight().x <= pos.x) {
                vx = -vx;
            }
            if (paddle.getPos().x >= pos.x) {
                vx = -vx;
            }
        }
    }
}
