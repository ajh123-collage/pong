public class Wall extends Sprite {
    public Wall(int x, int y) {
        super(null, x, y);
    }

    public Wall(int x, int y, int width, int height) {
        super(null, x, y, width, height);
    }

    @Override
    public void tick() {
        // unused
    }
}
