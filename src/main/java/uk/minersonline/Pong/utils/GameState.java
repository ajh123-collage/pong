package uk.minersonline.Pong.utils;

import com.google.gson.Gson;
import uk.minersonline.Pong.Ball;
import uk.minersonline.Pong.Side;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class GameState {
    private static GameState instance = null;

    private int leftPlayerScore;
    private int rightPlayerScore;
    private int rally;
    private int highestRally;

    private GameState() {
    }

    public int getLeftPlayerScore() {
        return leftPlayerScore;
    }

    public void setLeftPlayerScore(int leftPlayerScore) {
        this.leftPlayerScore = leftPlayerScore;
    }

    public int getRightPlayerScore() {
        return rightPlayerScore;
    }

    public void setRightPlayerScore(int rightPlayerScore) {
        this.rightPlayerScore = rightPlayerScore;
    }

    public int getRally() {
        return rally;
    }

    public void setRally(int rally) {
        this.rally = rally;
    }

    public int getHighestRally() {
        return highestRally;
    }

    public void setHighestRally(int highestRally) {
        this.highestRally = highestRally;
    }

    public static GameState getInstance() {
        if (instance == null) {
            instance = new GameState();
        }
        return instance;
    }

    public static String serialise() {
        Gson gson = new Gson();
        return gson.toJson(getInstance());
    }

    public static void deserialize(String raw) {
        Gson gson = new Gson();
        GameState loaded = gson.fromJson(raw, GameState.class);

        GameState current = GameState.getInstance();
        current.highestRally = loaded.getHighestRally();
        if (loaded.leftPlayerScore > loaded.rightPlayerScore) {
            Ball.WON_DIR = Side.LEFT;
        } else if (loaded.rightPlayerScore > loaded.leftPlayerScore) {
            Ball.WON_DIR = Side.RIGHT;
        }
    }

    public static void save() {
        String data = serialise();

        try {
            File file = new File("data.json");
            FileWriter writer = new FileWriter(file);
            writer.write(data);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void load() {
        try {
            File file = new File("data.json");
            Scanner scanner = new Scanner(file);
            if (scanner.hasNextLine()) {
                String raw = scanner.nextLine();

                deserialize(raw);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
