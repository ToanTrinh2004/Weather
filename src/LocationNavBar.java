import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LocationNavBar extends JPanel {
    private ArrayList<WeatherData> locationList;
    private Home homeFrame; // Reference to the Home frame
    private LocationButton selectedButton; // To track the selected button

    public LocationNavBar(ArrayList<WeatherData> locationList, Home homeFrame) {
        this.locationList = locationList;
        this.homeFrame = homeFrame;

        setBounds(0, 50, 1920, 80); // Full width
        setBackground(Color.decode("#DCE0FA"));
        setBorder(BorderFactory.createEmptyBorder(10, 140, 0, 140)); // Padding of 100 (left and right)

        // Use FlowLayout with LEFT alignment, and also set some gap between buttons
        FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT, 10, 10);  // Horizontal and vertical gap of 10
        setLayout(flowLayout);

        refreshButtons(); // Initial load of buttons
    }

    // Method to refresh the buttons based on the current location list
    public void refreshButtons() {
        removeAll(); // Clear existing buttons
        for (int i = 0; i < locationList.size(); i++) {
            WeatherData location = locationList.get(i);

            // Use LocationButton instead of the regular JButton
            LocationButton locationButton = new LocationButton(location.getName(), location.getIndex());

            // Add action listener to update WeatherPanel on button click
            locationButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Check if the clicked button is already selected
                    if (selectedButton != locationButton) {
                        // Reset background color of the previously selected button
                        if (selectedButton != null) {
                            selectedButton.setBackground(Color.decode("#EBEFFF")); // Default color
                        }

                        // Set the clicked button's background to white
                        locationButton.setBackground(Color.WHITE);

                        // Update the selected button reference
                        selectedButton = locationButton;
                    }

                    // Update the weather panel with the specific location's data
                    homeFrame.updateWeatherPanel(location); // Pass the specific location
                }
            });

            // Set default background color for the button
            locationButton.setBackground(Color.decode("#EBEFFF"));

            // Add the button to the panel
            add(locationButton);

            // Set the first button as selected
            if (i == 0 && selectedButton == null) {
                selectedButton = locationButton;
                locationButton.setBackground(Color.WHITE); // Set the first button background to white
            }
        }
        revalidate(); // Refresh the layout
        repaint(); // Update the panel
    }
}
