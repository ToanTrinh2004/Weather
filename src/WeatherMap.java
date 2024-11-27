import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URL;

public class WeatherMap extends JPanel {
    private BufferedImage mapImage;
    private String layer;
    private int zoom = 5; // Default zoom level
    private int cornerRadius = 20; // Corner radius for rounded corners

    public WeatherMap(double longitude, double latitude, String layer) {
        this.layer = layer;
        fetchMap(longitude, latitude); // Initial map fetch
    }

    // Method to fetch and update the map image
    public void fetchMap(double longitude, double latitude) {
        int tileSize = 256; // Tile size for OpenWeatherMap tiles (usually 256x256)

        // Calculate x and y tile coordinates
        int xTile = (int) Math.floor((longitude + 180) / 360 * Math.pow(2, zoom));
        int yTile = (int) Math.floor((1 - Math.log(Math.tan(Math.toRadians(latitude)) + 1 / Math.cos(Math.toRadians(latitude))) / Math.PI) / 2 * Math.pow(2, zoom));

        // Construct the OpenWeatherMap API URL
        String imageUrl = String.format(
                "https://maps.openweathermap.org/maps/2.0/weather/%s/%d/%d/%d?appid=ec5ff2a1d683585b203a4958a8c7e0e2&fill_bound=false",
                layer, zoom, xTile, yTile
        );

        try {
            URL url = new URL(imageUrl);
            InputStream is = url.openStream();
            mapImage = ImageIO.read(is); // Load the map image
            is.close();
            repaint(); // Repaint the panel with the updated map image
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load map image", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        int width = getWidth();
        int height = getHeight();

        // Enable anti-aliasing for smoother edges
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Create a rounded rectangle with the defined corner radius
        Shape roundedRectangle = new RoundRectangle2D.Float(0, 0, width, height, cornerRadius, cornerRadius);

        // Fill the background with a solid color or gradient (optional)
        g2.setColor(getBackground());
        g2.fill(roundedRectangle);

        // Set the clip to the rounded rectangle
        g2.setClip(roundedRectangle);

        // Draw the map image within the rounded rectangle
        if (mapImage != null) {
            g2.drawImage(mapImage, 0, 0, width, height, this);
        }

        g2.dispose();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(256, 256); // Tile dimensions
    }
}
