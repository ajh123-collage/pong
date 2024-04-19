import utils.KeyControls;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static utils.Constants.*;

public class Board extends JPanel implements ActionListener, KeyListener {

    private final Ball ball;
    private final List<Player> players;
    private final List<Sprite> sprites;
    private final Set<Integer> activeKeyCodes;

    public Board() {
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        setBackground(Color.GRAY);

        Player player = new Player(PLAYER_WIDTH, new KeyControls(KeyEvent.VK_W, KeyEvent.VK_S));
        Player player2 = new Player(BOARD_WIDTH - PLAYER_WIDTH * 2, new KeyControls(KeyEvent.VK_UP, KeyEvent.VK_DOWN));
        ball = new Ball(BOARD_WIDTH / 2 - BALL_WIDTH / 2,
                BOARD_HEIGHT / 2 - BALL_WIDTH / 2);

        Wall left = new Wall(-1, 0, 1, BOARD_HEIGHT);
        Wall right = new Wall(BOARD_WIDTH + 1, 0, 1, BOARD_HEIGHT);
        Wall top = new Wall(-1, -1, BOARD_WIDTH, 1);
        Wall down = new Wall(-1, BOARD_HEIGHT + 1, BOARD_WIDTH, 1);


        players = new ArrayList<>(List.of(player, player2));
        sprites = new ArrayList<>(List.of(left, right, top, down, player, player2, ball));

        activeKeyCodes = new HashSet<>();

        new Timer(TICK_DELAY, this).start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(Player player : players) {
            player.handleActiveKeys(activeKeyCodes);
        }

        for(Sprite sprite : sprites) {
            sprite.tick();
        }

        for(Sprite sprite : sprites) {
            if(ball.isColliding(sprite)) {
                ball.handleCollision(sprite);
            }
            for(Player player : players) {
                if(player.isColliding(sprite)) {
                    player.handleCollision(sprite);
                }
            }
        }

        repaint();
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);

        for(Sprite sprite : sprites) {
            sprite.draw(graphics, this);
        }
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
}
