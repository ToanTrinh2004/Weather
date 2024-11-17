import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URL;

public class WeatherMap extends JPanel {
    private BufferedImage mapImage;

    public WeatherMap() {
        String imageUrl = "https://api.mapbox.com/styles/v1/mapbox/light-v11/static/-87.0186,32.4055,14/500x300?access_token=pk.eyJ1IjoicXVvY3RvYW4wNCIsImEiOiJjbHp5dTI5OHExNG9yMmxvZGJmZzJkdWxyIn0.f18Gkc2GTFJrmMBrxhHfvg";
        try {
            URL url = new URL(imageUrl);
            InputStream is = url.openStream();
            mapImage = ImageIO.read(is); // Load the map image
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load map image", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (mapImage != null) {
            Graphics2D g2 = (Graphics2D) g.create();
            int width = getWidth();
            int height = getHeight();

            // Enable anti-aliasing for smoother edges
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Create a rounded rectangle with a radius of 15 pixels
            Shape roundedRectangle = new RoundRectangle2D.Float(0, 0, width, height, 15, 15);
            g2.setClip(roundedRectangle); // Set the clip to the rounded rectangle

            // Draw the map image within the rounded rectangle
            g2.drawImage(mapImage, 0, 0, width, height, this);
            g2.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to load map", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(630, 600); // Map image dimensions
    }
}
