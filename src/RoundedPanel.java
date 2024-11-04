import javax.swing.*;
import java.awt.*;

public class RoundedPanel extends JPanel {
    private Color backgroundColor;
    private int cornerRadius = 15;

    public RoundedPanel(int radius, Color bgColor) {
        super();
        this.cornerRadius = radius;
        this.backgroundColor = bgColor;
        setOpaque(false); // Make the panel background transparent
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Enable anti-aliasing for smoother corners
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Set background color for the rounded panel
        g2.setColor(backgroundColor);

        // Draw rounded rectangle (x, y, width, height, arcWidth, arcHeight)
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
    }

    // Optional: method to set corner radius
    public void setCornerRadius(int radius) {
        this.cornerRadius = radius;
        repaint();
    }

    // Optional: method to set background color
    public void setBackgroundColor(Color bgColor) {
        this.backgroundColor = bgColor;
        repaint();
    }
}
