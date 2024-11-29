import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class LocationPanel extends JPanel {
    private JLabel Location;
    private JButton homeIcon;
    private JLabel homePlace;
    private int index; // Store the index of the location
    private Home homeFrame; // Reference to the Home frame

    // Constructor with reference to Home
    public LocationPanel() {
        this.homeFrame = homeFrame; // Store the reference to Home
        Location = new JLabel();
        homeIcon = new JButton();
        homePlace = new JLabel();
        Location.setFont(new Font("Default", Font.BOLD, 18));
        homeIcon.setOpaque(false);
        homeIcon.setContentAreaFilled(false); // Disable button content area fill
        homeIcon.setBorderPainted(false); // Remove button border
        homeIcon.setFocusPainted(false); // Remove focus outline
        setOpaque(false);
        setLayout(new FlowLayout(FlowLayout.LEFT, 8, 8));
    }

    public void setNameForLocation(String name, boolean isDay, int index) {
        this.index = index; // Set the index of the location panel
        Location.setText(name);
        add(homePlace);
        add(Location);
        add(homeIcon);
        if (isDay) {
            homeIcon.setIcon(new ImageIcon(Objects.requireNonNull(WeatherPanel.class.getResource("/Assets/selectedHomeDark.png"))));
            Location.setForeground(Color.BLACK);
            homePlace.setIcon(new ImageIcon(Objects.requireNonNull(WeatherPanel.class.getResource("/Assets/homeDark.png"))));
        } else {
            homeIcon.setIcon(new ImageIcon(Objects.requireNonNull(WeatherPanel.class.getResource("/Assets/selectedHomeLight.png"))));
            Location.setForeground(Color.WHITE);
            homePlace.setIcon(new ImageIcon(Objects.requireNonNull(WeatherPanel.class.getResource("/Assets/homeLight.png"))));
        }

        if (index == 0) {
            homeIcon.setVisible(false);
            homePlace.setVisible(true);
        } else {
            homePlace.setVisible(false);
            homeIcon.setVisible(true);
            // Add listener for the homeIcon button
            homeIcon.addActionListener(e -> {
                // Call the method in WeatherPanel to swap locations in Home
                WeatherPanel weatherPanel = (WeatherPanel) getParent(); // Assuming WeatherPanel is the parent of LocationPanel
                if (weatherPanel != null) {
                }
            });
        }
    }

}
