package uk.minersonline.Pong.utils;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

public class GraphicsUtils {
    /**
     * This method centers a <code>String</code> in
     * a bounding <code>Rectangle</code>.
     * derived from: <a href="https://stackoverflow.com/a/27711103">stackoverflow</a>
     * @param g - The <code>Graphics</code> instance.
     * @param r - The bounding <code>Rectangle</code>.
     * @param s - The <code>String</code> to center in the
     * bounding rectangle.
     * @param font - The display font of the <code>String</code>
     *
     * @see Graphics
     * @see Rectangle
     * @see String
     */
    public static void centerString(Graphics g, Rectangle r, String s, Font font, int y) {
        FontRenderContext frc = new FontRenderContext(null, true, true);

        Rectangle2D r2D = font.getStringBounds(s, frc);
        int rWidth = (int) Math.round(r2D.getWidth());
        int rHeight = (int) Math.round(r2D.getHeight());
        int rX = (int) Math.round(r2D.getX());
//        int rY = (int) Math.round(r2D.getY());

        int a = (r.width / 2) - (rWidth / 2) - rX;
        int b = y;//(r.height / 2) - (rHeight / 2) - rY;

        g.setFont(font);
        g.drawString(s, r.x + a, r.y + b);
    }

    public static void centerString(Graphics g, String s, Font font, int y) {
        centerString(g, getBoardBounds(), s, font, y);
    }

    public static Rectangle getBoardBounds() {
        return new Rectangle(0, 0, Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT);
    }
}
