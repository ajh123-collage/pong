import static utils.Constants.*;

public class Ball extends MovingSprite {
    private double speedMultiplier;

    public Ball(int x, int y) {
        super(BALL_IMAGE_PATH, x, y, BALL_WIDTH, BALL_HEIGHT);
        dx = -BALL_SPEED;
        speedMultiplier = 1;
    }

    @Override
    void postCollide(Collidable other) {
        if (other.getBottomRight().x <= pos.x) {
            dx = BALL_SPEED;
        }
        if (other.getPos().x >= pos.x) {
            dx = -BALL_SPEED;
        }
        if (other.getBottomRight().y <= pos.y) {
            dy = BALL_SPEED;
        }
        if (other.getPos().y >= pos.y) {
            dy = -BALL_SPEED;
        }
        speedMultiplier += 0.25;

        dx = dx * speedMultiplier;
        dy = dy * speedMultiplier;
    }
}
