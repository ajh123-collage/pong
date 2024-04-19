import utils.KeyControls;

import java.util.Set;

import static utils.Constants.*;

public class Player extends MovingSprite {
    private final KeyControls keyControls;

    public Player(int x, KeyControls keyControls) {
        super(PLAYER_IMAGE_PATH, x, BOARD_HEIGHT / 2 - PLAYER_HEIGHT / 2, PLAYER_WIDTH, PLAYER_HEIGHT);
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
}
