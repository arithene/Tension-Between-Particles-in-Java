**Tension between particles computed with this formula:**
```math
\text{TENSION} = \frac{\text{INITIAL LENGTH} - \text{CURRENT LENGTH}}{\text{CURRENT LENGTH}}
```
**OR:**
```math
\text{TENSION} = \frac{\text{INITIAL LENGTH} - \text{NEW LENGTH}}{\text{NEW LENGTH}}
```
# CODE
- Tt's formula based method inside Tension.java file:
```java
public double tension() {
  double currentLength = length();
  double difference = (initialLength - currentLength);
  return difference / currentLength;
}
```
- Tension.java file:
```java
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
```
- ColorUtils.java file:
```java
package tutorial0_tensionofparticles;

import java.awt.Color;

public class ColorUtils {
    /**
     * <It's ChatGPT code.>
     * Interpolates between two colors (Blue and Red) based on a given fraction.
     * It's Linear Interpolation aka known LERP.
     *
     * @param colorStart The start color (e.g., Color.BLUE).
     * @param colorEnd   The end color (e.g., Color.RED).
     * @param fraction   The interpolation fraction (0.0 to 1.0).
     * @return The interpolated color.
     */
    public static Color interpolateColor(Color colorStart, Color colorEnd, double fraction) {
        // Clamp fraction between 0 and 1
        fraction = Math.max(0, Math.min(fraction, 1));

        // Extract ARGB components
        int startR = colorStart.getRed();
        int startG = colorStart.getGreen();
        int startB = colorStart.getBlue();

        int endR = colorEnd.getRed();
        int endG = colorEnd.getGreen();
        int endB = colorEnd.getBlue();

        // Interpolate each component. Lerp formula: c = a * fraction + b * (1 -
        // fraction)
        int r = (int) (startR + (endR - startR) * fraction);
        int g = (int) (startG + (endG - startG) * fraction);
        int b = (int) (startB + (endB - startB) * fraction);

        // Combine components back into a color
        return new Color(r, g, b);
    }

    /**
     * <It's ChatGPT code.>
     * Interpolates between three colors (Blue, Black, and Red) based on a given
     * fraction.
     * Supports fractions from -1.0 (Blue) to 1.0 (Red), with 0.0 corresponding to
     * Black.
     *
     * @param fraction The interpolation fraction (-1.0 to 1.0).
     * @return The interpolated color.
     */
    public static Color interpolateColor(double fraction) {
        // Clamp fraction between -1 and 1
        fraction = Math.max(-1, Math.min(fraction, 1));

        // Define the colors
        Color blue = new Color(0, 0, 255); // Blue
        Color black = new Color(0, 0, 0); // Black
        Color red = new Color(255, 0, 0); // Red

        // Determine which range to interpolate
        if (fraction < 0) {
            // Interpolate between Blue (-1) and Black (0)
            double localFraction = (fraction + 1); // Map [-1, 0] to [0, 1]
            int r = (int) (blue.getRed() + (black.getRed() - blue.getRed()) * localFraction);
            int g = (int) (blue.getGreen() + (black.getGreen() - blue.getGreen()) * localFraction);
            int b = (int) (blue.getBlue() + (black.getBlue() - blue.getBlue()) * localFraction);
            return new Color(r, g, b);
        } else {
            // Interpolate between Black (0) and Red (1)
            double localFraction = fraction; // Map [0, 1] to [0, 1]
            int r = (int) (black.getRed() + (red.getRed() - black.getRed()) * localFraction);
            int g = (int) (black.getGreen() + (red.getGreen() - black.getGreen()) * localFraction);
            int b = (int) (black.getBlue() + (red.getBlue() - black.getBlue()) * localFraction);
            return new Color(r, g, b);
        }
    }

}
```
- Particle.java file:
```java
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
```
### Main class:
- TensionOfParticles.java file:
```java
package tutorial0_tensionofparticles;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class TensionOfParticles extends JFrame {
    private int width = 1920;
    private int height = 1080;
    private int centerX = width / 2;
    private int centerY = height / 2;
    private ArrayList<Particle> particles;
    private int FPS = 24;
    protected Particle selected;
    protected Point mouse;
    private ArrayList<Tension> tensions;

    public TensionOfParticles(String title) {
        super(title);
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        particles = new ArrayList<Particle>();
        particles.add(new Particle(centerX - 100, centerY - 100));
        particles.add(new Particle(centerX + 100, centerY - 100));
        particles.add(new Particle(centerX + 100, centerY + 100));
        particles.add(new Particle(centerX - 100, centerY + 100));

        tensions = new ArrayList<Tension>();
        tensions.add(new Tension(particles.get(0), particles.get(1)));
        tensions.add(new Tension(particles.get(0), particles.get(2)));
        tensions.add(new Tension(particles.get(0), particles.get(3)));
        tensions.add(new Tension(particles.get(1), particles.get(2)));
        tensions.add(new Tension(particles.get(3), particles.get(2)));
        JPanel panel = new JPanel() {
            private Graphics2D canvas;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                canvas = (Graphics2D) g;
                canvas.setColor(Color.DARK_GRAY);
                canvas.fillRect(0, 0, width, height);
                canvas.setColor(Color.BLACK);
                canvas.setStroke(new BasicStroke(2));
                for (Particle particle : particles) {
                    particle.draw(canvas);
                }
                canvas.setStroke(new BasicStroke(10));
                for (Tension tension : tensions) {
                    tension.draw(canvas);
                }
                canvas.setColor(Color.BLACK);
                canvas.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
                canvas.drawString("Tension Formula:", centerX - 160, centerY + 300);
                canvas.drawString("Tension = (Initial Length - Current Length) / Current Length", centerX - 500,
                        centerY + 400);
            }
        };
        panel.setFocusable(true);
        panel.requestFocus();
        add(panel);
        Timer timer = new Timer(1000 / FPS, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.repaint();
            }
        });
        timer.start();
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                mouse = e.getPoint();
                for (Particle particle : particles) {
                    if (particle.isMouseInside(mouse)) {
                        selected = particle;
                        selected.setOffset(mouse);
                    }
                }
            }
        });
        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                Point current = e.getPoint();
                if (selected != null) {
                    selected.drag(current);
                }
                mouse = current;
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TensionOfParticles("Tension Of Particles");
            }
        });
    }

}
```
# DEMONSTRATION
**$${\color{blue}BLUE}$$ tension between -1 and 0, $${\color{gray}BLACK}$$ tension equal to 0, $${\color{red}RED}$$ tension between 0 and 1,:**

https://github.com/user-attachments/assets/042ae48d-780f-4129-9bc3-04b2ef860917

# EXTRACT ZIP FILE TO RUN JAR FILE
