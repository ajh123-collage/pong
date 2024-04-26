import utils.KeyControls;
import utils.Side;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.List;

import static utils.Constants.*;

public class Board extends JPanel implements ActionListener, KeyListener {

    private final Ball ball;
    private final List<Player> players;
    private final List<Sprite> sprites;
    private final Set<Integer> activeKeyCodes;
    private static Board instance = null;
    private int rally = 0;

    private Board() {
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        Color color = new Color(38, 79, 108);
        setBackground(color);

        Player player = new Player(PLAYER_WIDTH, new KeyControls(KeyEvent.VK_W, KeyEvent.VK_S), Side.LEFT);
        Player player2 = new Player(BOARD_WIDTH - PLAYER_WIDTH * 2, new KeyControls(KeyEvent.VK_UP, KeyEvent.VK_DOWN), Side.RIGHT);
        ball = new Ball(BOARD_WIDTH / 2 - BALL_WIDTH / 2,
                BOARD_HEIGHT / 2 - BALL_WIDTH / 2);

        Wall left = Wall.buildWall(Side.LEFT);
        Wall right = Wall.buildWall(Side.RIGHT);
        Wall top = Wall.buildWall(Side.TOP);
        Wall down = Wall.buildWall(Side.BOTTOM);


        players = new ArrayList<>(List.of(player, player2));
        sprites = new ArrayList<>(List.of(ball, player, player2, left, right, top, down));

        activeKeyCodes = new HashSet<>();

        new Timer(TICK_DELAY, this).start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (Player player : players) {
            player.handleActiveKeys(activeKeyCodes);
        }

        for (Sprite sprite : sprites) {
            sprite.tick();
        }

        for (Sprite sprite : sprites) {
            if (sprite.isColliding(ball)) {
                ball.handleCollision(sprite);
            }
            for (Player player : players) {
                if (player.isColliding(sprite)) {
                    player.handleCollision(sprite);
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

        String ral = "Rally: "+rally;
        int ralWidth = ral.length() * 32;
        graphics.setFont(new Font(graphics.getFont().getFontName(), Font.PLAIN, 32));
        graphics.setColor(Color.GREEN);
        graphics.drawString(ral, (BOARD_WIDTH / 2) - (ralWidth / 2), 32);
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

    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    public int getRally() {
        return rally;
    }

    public void setRally(int rally) {
        this.rally = rally;
    }

    public Player getLeft() {
        for (Player player : Board.getInstance().getPlayers()) {
            if (player.getSide() == Side.LEFT) {
                return player;
            }
        }
        return null;
    }

    public Player getRight() {
        for (Player player : Board.getInstance().getPlayers()) {
            if (player.getSide() == Side.RIGHT) {
                return player;
            }
        }
        return null;
    }
}
