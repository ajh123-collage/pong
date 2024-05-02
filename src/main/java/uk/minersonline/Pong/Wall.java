package uk.minersonline.Pong;

import uk.minersonline.Pong.utils.Side;

import static uk.minersonline.Pong.utils.Constants.BOARD_HEIGHT;
import static uk.minersonline.Pong.utils.Constants.BOARD_WIDTH;

public class Wall extends Sprite {
    private final Side side;

    private Wall(int x, int y, int width, int height, Side side) {
        super(null, x, y, width, height);
        this.side = side;
    }

    @Override
    public void tick() {
        // unused
    }

    public Side getSide() {
        return side;
    }

    public static Wall buildWall(Side side) {
        switch (side) {
            case LEFT -> {
                return new Wall(-1, 0, 1, BOARD_HEIGHT, side);
            }
            case RIGHT -> {
                return new Wall(BOARD_WIDTH + 1, 0, 1, BOARD_HEIGHT, side);
            }
            case TOP -> {
                return new Wall(-1, -1, BOARD_WIDTH, 1, side);
            }
            case BOTTOM -> {
                return new Wall(-1, BOARD_HEIGHT + 1, BOARD_WIDTH, 1, Side.BOTTOM);
            }
        }
        throw new RuntimeException("Invalid wall side "+side);
    }
}
