import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class RoundedPanel extends JPanel {
    private Color backgroundColor;
    private int cornerRadius = 15;
    private boolean isBlurred = false;  // To toggle the blur effect

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

        // Draw background with rounded corners
        if (isBlurred) {
            // Add blur effect (we'll simulate it with semi-transparency here)
            g2.setColor(new Color(255, 255, 255, 150)); // Semi-transparent white
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
        } else {
            // Set the background color if blur is not enabled
            g2.setColor(backgroundColor);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
        }

        // Optional: Draw the border around the rounded panel (if needed)
        g2.setColor(Color.DARK_GRAY);
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);
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

    // Method to toggle the blur effect
    public void setBlurred(boolean isBlurred) {
        this.isBlurred = isBlurred;
        repaint();
    }

}
