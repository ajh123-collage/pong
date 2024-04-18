import utils.KeyControls;

import java.util.Set;

import static utils.Constants.*;

public class Player extends MovingSprite {
    private final KeyControls keyControls;

    public Player(int x, KeyControls keyControls) {
        super(PLAYER_IMAGE_PATH, x, BOARD_HEIGHT / 2 - PLAYER_HEIGHT / 2, PLAYER_WIDTH, PLAYER_HEIGHT);
        this.keyControls = keyControls;
    }

    @Override
    public void tick() {
        pos.translate((int)dx, (int)dy);

        pos.x = Math.clamp(pos.x, 0, BOARD_WIDTH - PLAYER_WIDTH);
        pos.y = Math.clamp(pos.y, 0, BOARD_HEIGHT - PLAYER_HEIGHT);
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

        normalizeDeltas();
    }

    private void normalizeDeltas() {
        if (dx != 0 && dy != 0) {
            dx /= Math.sqrt(2);
            dy /= Math.sqrt(2);
        }
    }

    @Override
    void postCollide() {
        // Unused
    }
}
