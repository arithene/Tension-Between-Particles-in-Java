package tutorial0_tensionofparticles;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class Tension {
    private Particle a;
    private Particle b;
    private double initialLength;

    public Tension(Particle a, Particle b) {
        this.a = a;
        this.b = b;
        this.initialLength = length();
    }

    public double length() {
        int dx = a.x - b.x;
        int dy = a.y - b.y;
        return Math.hypot(dx, dy);
    }

    public double tension() {
        double currentLength = length();
        double difference = (initialLength - currentLength);
        return difference / currentLength;
    }

    public void draw(Graphics2D canvas) {
        double tension = tension();
        canvas.setColor(ColorUtils.interpolateColor(tension));
        canvas.drawLine(a.x, a.y, b.x, b.y);
        // i don't recommend compute in draw methods.
        int x = a.x + b.x;
        int y = a.y + b.y;
        canvas.setColor(new Color(20, 20, 20));
        canvas.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        canvas.drawString(String.format("%.1f", tension), x / 2, y / 2);
    }

}
