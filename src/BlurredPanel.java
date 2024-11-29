import javax.swing.*;
import java.awt.*;

public class BlurredPanel extends JPanel {
    public BlurredPanel() {
        setOpaque(false); // Make the panel transparent
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        // Set a semi-transparent white color as the overlay
        g2d.setColor(new Color(255, 255, 255, 150)); // Alpha between 0 and 255
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.dispose();
    }
}
