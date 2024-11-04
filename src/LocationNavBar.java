import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LocationNavBar extends JPanel {
    private ArrayList<WeatherData> locationList;
    private Home homeFrame; // Reference to the Home frame

    public LocationNavBar(ArrayList<WeatherData> locationList, Home homeFrame) {
        this.locationList = locationList;
        this.homeFrame = homeFrame;

        setBounds(0, 50, 1920, 50); // Full width
        setBackground(Color.blue);
        setLayout(new FlowLayout(FlowLayout.LEFT)); // Layout for horizontal buttons

        refreshButtons(); // Initial load of buttons
    }

    // Method to refresh the buttons based on the current location list
    public void refreshButtons() {
        removeAll(); // Clear existing buttons
        for (WeatherData location : locationList) {
            JButton locationButton = new JButton(location.getName());
            locationButton.setForeground(Color.white);
            locationButton.setBackground(Color.blue);
            locationButton.setFocusPainted(false); // Remove focus border

            // Add action listener to update WeatherPanel on button click
            locationButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Update the weather panel with the specific location's data
                    homeFrame.updateWeatherPanel(location); // Pass the specific location
                }
            });

            // Add the button to the panel
            add(locationButton);
        }
        revalidate(); // Refresh the layout
        repaint(); // Update the panel
    }
}
