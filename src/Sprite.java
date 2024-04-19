import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

public abstract class Sprite implements Collidable {

    protected Point pos;
    protected Dimension size;
    private BufferedImage image;

    public Sprite(String imagePath, int x, int y) {
        pos = new Point(x, y);
        loadImage(imagePath);
        size = new Dimension(image.getWidth(), image.getHeight());
    }

    public Sprite(String imagePath, int x, int y, int width, int height) {
        pos = new Point(x, y);
        loadImage(imagePath);
        size = new Dimension(width, height);
    }

    private void loadImage(String imagePath) {
        if (imagePath != null) {
            try {
                image = ImageIO.read(new File(imagePath));
            } catch (IOException exception) {
                System.err.println("Error opening image file: " + imagePath);
            }
        }
    }

    public void draw(Graphics graphics, ImageObserver observer) {
        if (image != null) {
            graphics.drawImage(image, pos.x, pos.y, size.width, size.height, observer);
        }
    }

    @Override
    public Point getPos() {
        return pos;
    }

    @Override
    public Dimension getSize() {
        return size;
    }

    @Override
    public Point getTopLeft() {
        return pos;
    }

    @Override
    public Point getBottomRight() {
        return new Point(pos.x + size.width, pos.y + size.height);
    }

    public boolean isColliding(Sprite other) {
        return this != other
                && this.getTopLeft().x < other.getBottomRight().x
                && this.getBottomRight().x > other.getTopLeft().x
                && this.getTopLeft().y < other.getBottomRight().y
                && this.getBottomRight().y > other.getTopLeft().y;
    }

    public abstract void tick();
}
