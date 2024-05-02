package uk.minersonline.Pong;

import uk.minersonline.Pong.utils.GameState;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        JFrame window = new JFrame("Pong");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GameState.load();
        Board board = Board.getInstance();
        window.add(board);
        window.addKeyListener(board);

        window.setResizable(false);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
