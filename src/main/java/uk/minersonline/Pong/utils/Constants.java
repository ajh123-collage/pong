package uk.minersonline.Pong.utils;

public final class Constants {
    private Constants() {
        // prevents instantiation
    }

    // uk.minersonline.Pong.Board constants
    public static final int BOARD_WIDTH = 640;
    public static final int BOARD_HEIGHT = 480;
    // A delay of 25 milliseconds results in a frame rate of 45 FPS.
    public static final int TICK_DELAY = 25;

    // Player constants
    public static final String PLAYER_IMAGE_PATH = "paddle.png";
    public static final int PLAYER_WIDTH = 32;
    public static final int PLAYER_HEIGHT = 128;
    public static final int PLAYER_SPEED = 10;

    // uk.minersonline.Pong.Wall constants
    public static final String BALL_IMAGE_PATH = "ball.png";
    public static final int BALL_WIDTH = 32;
    public static final int BALL_HEIGHT = 32;
    public static final int BALL_SPEED = 10;
}
