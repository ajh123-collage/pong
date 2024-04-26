import utils.KeyControls;
import utils.Side;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.Set;

import static utils.Constants.*;

public class Player extends MovingSprite {
    private final KeyControls keyControls;
    private int score;
    private final Side side;

    public Player(int x, KeyControls keyControls, Side side) {
        super(PLAYER_IMAGE_PATH, x, BOARD_HEIGHT / 2 - PLAYER_HEIGHT / 2, PLAYER_WIDTH, PLAYER_HEIGHT);
        this.side = side;
        this.keyControls = keyControls;
        this.score = 0;
    }

    public void handleActiveKeys(Set<Integer> activeKeyCodes) {
        dx = 0;
        dy = 0;

        if (activeKeyCodes.contains(keyControls.upKey())) {
            dy -= PLAYER_SPEED;
        }
        if (activeKeyCodes.contains(keyControls.downKey())) {
            dy += PLAYER_SPEED;
        }
    }

    @Override
    void postCollide(Collidable other) {
        // Unused
    }

    @Override
    public void draw(Graphics graphics, ImageObserver observer) {
        super.draw(graphics, observer);

        graphics.setColor(Color.GREEN);
        graphics.setFont(new Font(graphics.getFont().getFontName(), Font.PLAIN, 32));
        graphics.drawString(String.valueOf(score), this.pos.x, 32);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Side getSide() {
        return side;
    }
}
