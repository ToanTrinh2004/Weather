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
        setBorder(BorderFactory.createEmptyBorder(0, 100, 0, 100)); // Padding of 100 (left and right)

        // Use FlowLayout with CENTER alignment, and also set some gap between buttons
        FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT, 10, 10);  // Horizontal and vertical gap of 10
        setLayout(flowLayout);

        refreshButtons(); // Initial load of buttons
    }

    // Method to refresh the buttons based on the current location list
    public void refreshButtons() {
        removeAll(); // Clear existing buttons
        for (WeatherData location : locationList) {
            // Use LocationButton instead of the regular JButton
            LocationButton locationButton = new LocationButton(location.getName());

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
