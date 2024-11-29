import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.Objects;

public class LocationButton extends JButton {
    private int cornerRadius = 20; // Corner radius for the button
    public LocationButton(String text,int index) {
        super(text);
        setFocusPainted(false); // Remove the focus border
        setBackground(Color.decode("#EBEFFF")); // Set button background color
        setForeground(Color.BLACK); // Set text color
        setPreferredSize(new Dimension(150, 40));
        if (index == 0) {
            // Set an icon (example icon, you can replace with your own)
            Icon icon = new ImageIcon(Objects.requireNonNull(WeatherPanel.class.getResource("/Assets/homeDark.png"))); // Replace with a custom icon if needed
            setIcon(icon);
            setHorizontalTextPosition(SwingConstants.RIGHT); // Position text to the right of the icon
            setIconTextGap(8); // Set gap between the icon and the text
        }
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
