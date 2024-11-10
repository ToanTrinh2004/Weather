import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class LocationButton extends JButton {
    private int cornerRadius = 20; // Corner radius for the button

    public LocationButton(String text) {
        super(text);
        setFocusPainted(false); // Remove the focus border
        setBackground(Color.blue); // Set button background color
        setForeground(Color.white); // Set text color
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Enable antialiasing for smoother edges
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Set background color
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);

        // Paint the button text
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.darkGray); // Border color
        g2.setStroke(new BasicStroke(2)); // Border width
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius); // Draw rounded border
    }
}
