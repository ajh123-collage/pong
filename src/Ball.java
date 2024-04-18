import java.awt.*;

import static utils.Constants.*;

public class Ball extends MovingSprite {

    public Ball(int x, int y) {
        super(BALL_IMAGE_PATH, x, y, BALL_WIDTH, BALL_HEIGHT);
        dx = -BALL_SPEED;
    }

    @Override
    public void tick() {
        pos.translate((int)dx, (int)dy);

        pos.x = Math.clamp(pos.x, 0, BOARD_WIDTH - BALL_HEIGHT);
        pos.y = Math.clamp(pos.y, 0, BOARD_HEIGHT - PLAYER_HEIGHT);
    }

    @Override
    void postCollide() {
        System.out.println();
        System.err.println(pos);
    }
}
