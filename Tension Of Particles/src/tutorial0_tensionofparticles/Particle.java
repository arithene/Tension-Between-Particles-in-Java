package tutorial0_tensionofparticles;

import java.awt.Graphics2D;
import java.awt.Point;

public class Particle {

    public int x;
    public int y;
    public int radius = 40;
    public int diameter = 2 * radius;
    private Point offset;

    public Particle(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics2D canvas) {
        canvas.drawOval(x - radius, y - radius, diameter, diameter);
    }

    public boolean isMouseInside(Point mouse) {
        int dx = x - mouse.x;
        int dy = y - mouse.y;
        double length = Math.hypot(dx, dy);
        return radius > length;
    }

    public void setOffset(Point mouse) {
        int dx = x - mouse.x;
        int dy = y - mouse.y;
        offset = new Point(dx, dy);
    }

    public void drag(Point current) {
        x = current.x + offset.x;
        y = current.y + offset.y;
    }

}
