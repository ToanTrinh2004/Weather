import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class ImageBackgroundPanel extends JPanel {
    private JLabel imageLabel;
    // Constructor to set up the panel with the image
    public ImageBackgroundPanel(String imagePath) {
        // Set the panel properties
        setBackground(Color.WHITE); // Set background color to white
        setLayout(null); // Use GridBagLayout to center the label
        // Create and configure the label with the image icon
        imageLabel = new JLabel();
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource(imagePath)));
        // Resize the image to fit within desired dimensions
        Image resizedImage = icon.getImage().getScaledInstance(500, 500, Image.SCALE_SMOOTH); // Adjust size as needed
        imageLabel.setIcon(new ImageIcon(resizedImage));
        imageLabel.setBounds(400,0,500,500);
        // Add the label to the panel
        add(imageLabel);
    }

    // Method to update the image dynamically
    public void setImage(String imagePath) {
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource(imagePath)));
        Image resizedImage = icon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH); // Adjust size as needed
        imageLabel.setIcon(new ImageIcon(resizedImage));
        revalidate();
        repaint();
    }
}
