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
