package uk.minersonline.Pong;

import uk.minersonline.Pong.utils.GameState;
import uk.minersonline.Pong.utils.KeyControls;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.List;

import static uk.minersonline.Pong.utils.Constants.*;

public class Board extends JPanel implements ActionListener, KeyListener {

    private final Ball ball;
    private final List<Paddle> paddles;
    private final List<Sprite> sprites;
    private final Set<Integer> activeKeyCodes;

    public static boolean GAME_ON = true;
    private static Board instance = null;

    private Board() {
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        Color color = new Color(38, 79, 108);
        setBackground(color);

        Paddle paddle = new Paddle(PLAYER_WIDTH, new KeyControls(KeyEvent.VK_W, KeyEvent.VK_S), Side.LEFT);
        Paddle paddle2 = new Paddle(BOARD_WIDTH - PLAYER_WIDTH * 2, new KeyControls(KeyEvent.VK_UP, KeyEvent.VK_DOWN), Side.RIGHT);
        ball = new Ball(BOARD_WIDTH / 2 - BALL_WIDTH / 2,
                BOARD_HEIGHT / 2 - BALL_WIDTH / 2);

        Wall left = Wall.buildWall(Side.LEFT);
        Wall right = Wall.buildWall(Side.RIGHT);
        Wall top = Wall.buildWall(Side.TOP);
        Wall down = Wall.buildWall(Side.BOTTOM);


        paddles = new ArrayList<>(List.of(paddle, paddle2));
        sprites = new ArrayList<>(List.of(ball, paddle, paddle2, left, right, top, down));

        activeKeyCodes = new HashSet<>();

        new Timer(TICK_DELAY, this).start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (GAME_ON) {
            for (Paddle paddle : paddles) {
                paddle.handleActiveKeys(activeKeyCodes);
            }

            for (Sprite sprite : sprites) {
                sprite.tick();
            }

            for (Sprite sprite : sprites) {
                if (sprite.isColliding(ball)) {
                    ball.handleCollision(sprite);
                }
                for (Paddle paddle : paddles) {
                    if (paddle.isColliding(sprite)) {
                        paddle.handleCollision(sprite);
                    }
                }
            }
        }

        repaint();
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);

        for (Sprite sprite : sprites) {
            sprite.draw(graphics, this);
        }

        String ral = "Rally: "+ GameState.getInstance().getRally();
        int ralWidth = ral.length() * 32;
        graphics.setFont(new Font(graphics.getFont().getFontName(), Font.PLAIN, 32));
        graphics.setColor(Color.GREEN);
        graphics.drawString(ral, (BOARD_WIDTH / 2) - (ralWidth / 2), 32);

        String ral2 = "Highest Rally: "+GameState.getInstance().getHighestRally();
        int ralWidth2 = ral2.length() * 32;
        graphics.setFont(new Font(graphics.getFont().getFontName(), Font.PLAIN, 32));
        graphics.setColor(Color.GREEN);
        graphics.drawString(ral2, (BOARD_WIDTH / 2) - (ralWidth2 / 2), 64);
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
        // Unused
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        activeKeyCodes.add(keyEvent.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        activeKeyCodes.remove(keyEvent.getKeyCode());
    }

    public static Board getInstance() {
        if (instance == null) {
            instance = new Board();
        }
        return instance;
    }

    public List<Paddle> getPlayers() {
        return Collections.unmodifiableList(paddles);
    }

    public Paddle getLeft() {
        for (Paddle paddle : Board.getInstance().getPlayers()) {
            if (paddle.getSide() == Side.LEFT) {
                return paddle;
            }
        }
        return null;
    }

    public Paddle getRight() {
        for (Paddle paddle : Board.getInstance().getPlayers()) {
            if (paddle.getSide() == Side.RIGHT) {
                return paddle;
            }
        }
        return null;
    }
}
