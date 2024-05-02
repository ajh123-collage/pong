package uk.minersonline.Pong;

import uk.minersonline.Pong.utils.Collidable;
import uk.minersonline.Pong.utils.GameState;
import uk.minersonline.Pong.utils.KeyControls;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.Set;

import static uk.minersonline.Pong.utils.Constants.*;

public class Paddle extends MovingSprite {
    private final KeyControls keyControls;
    private final Side side;

    public Paddle(int x, KeyControls keyControls, Side side) {
        super(PLAYER_IMAGE_PATH, x, BOARD_HEIGHT / 2 - PLAYER_HEIGHT / 2, PLAYER_WIDTH, PLAYER_HEIGHT);
        this.side = side;
        this.keyControls = keyControls;
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
        if (side == Side.LEFT) {
            graphics.drawString(String.valueOf(GameState.getInstance().getLeftPlayerScore()), this.pos.x, 32);
        } else {
            graphics.drawString(String.valueOf(GameState.getInstance().getRightPlayerScore()), this.pos.x, 32);
        }
    }

    public int getScore() {
        if (side == Side.LEFT) {
            return GameState.getInstance().getLeftPlayerScore();
        } else {
            return GameState.getInstance().getRightPlayerScore();
        }
    }

    public void setScore(int score) {
        if (side == Side.LEFT) {
            GameState.getInstance().setLeftPlayerScore(score);
        } else {
            GameState.getInstance().setRightPlayerScore(score);
        }
        if (getScore() == 11) {
            Board.GAME_ON = false;
            GameState.save();
        }
    }

    public Side getSide() {
        return side;
    }
}
