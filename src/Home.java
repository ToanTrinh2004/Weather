import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class Home extends JFrame {
    public static ArrayList<WeatherData> locationList = new ArrayList<>();
    private WeatherPanel weatherPanel;
    private LocationNavBar locationNavBar;
    private SearchField searchField;

    public Home() {
        // Set up the frame
        setTitle("Weather Forecast");
        setSize(1920, 1080);  // Adjusted frame size for better fit
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon(Objects.requireNonNull(Home.class.getResource("/Assets/Logo.png"))).getImage());
        setLayout(null);

        // Create a layered pane
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 1920, 1080); // Set bounds to match the frame
        add(layeredPane);

        // Add Header to the layered pane
        Home_Header header = new Home_Header(this);
        header.setBounds(0, 0, 1920, 50); // Adjust the height as needed
        layeredPane.add(header, JLayeredPane.DEFAULT_LAYER);

        // Add SearchField on top of the Header
        searchField = new SearchField(this);
        searchField.setBounds(1000, 0, 250, 160); // Position on top of the Header
        layeredPane.add(searchField, JLayeredPane.PALETTE_LAYER);

        // Initialize and add the LocationNavBar
        locationNavBar = new LocationNavBar(locationList, this);
        locationNavBar.setBounds(0, 50, 1920, 80);  // Adjust locationNavBar position
        layeredPane.add(locationNavBar, JLayeredPane.DEFAULT_LAYER);
        // Initialize the WeatherPanel
        WeatherData latestLocation = locationList.get(locationList.size() - 1);
        weatherPanel = new WeatherPanel(latestLocation.getLat(), latestLocation.getLon(), latestLocation.getName(), latestLocation.getIndex(), this);
// Add mock content to the WeatherPanel for testing scroll
// Set a preferred size for the WeatherPanel to enable scrolling
        weatherPanel.setPreferredSize(new Dimension(1920, 4500)); // Content height exceeds visible area

// Create a JScrollPane for the WeatherPanel
        JScrollPane weatherScrollPane = new JScrollPane(weatherPanel);
        weatherScrollPane.setBounds(0, 130, 1920, 850); // Set the position and size for scrolling
        weatherScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS); // Enable vertical scroll bar
        weatherScrollPane.getVerticalScrollBar().setUnitIncrement(20); // Set scroll speed
        layeredPane.add(weatherScrollPane, JLayeredPane.DEFAULT_LAYER);


        setVisible(true);
    }

    // Method to add a new location and update the weather panel
    public void addLocation(WeatherData newLocation) {
        locationList.add(newLocation);
        locationNavBar.refreshButtons(); // Refresh the buttons in the location nav bar
        updateWeatherPanel(newLocation);  // Update the weather panel with the new location
    }

    // Update the weather panel with specific location data
    public void updateWeatherPanel(WeatherData location) {
        if (location != null) { // Ensure the location is not null
            weatherPanel.updateWeatherPanel(location.getLat(), location.getLon(), location.getName(), location.getIndex());
            weatherPanel.repaint();
        }
    }

    // Optionally, add a method to update weather panel using the latest location
    public void updateWeatherPanelFromLatest() {
        if (!locationList.isEmpty()) { // Ensure locationList is not empty
            WeatherData latestLocation = locationList.get(locationList.size() - 1);
            updateWeatherPanel(latestLocation); // Call the new update method
            locationNavBar.refreshButtons();
        }
    }

    public void swapLocation(int fromIndex, int toIndex) {
        if (fromIndex != toIndex) {
            // Swap the location in the list (basic array swap)
            WeatherData temp = locationList.get(fromIndex);
            locationList.set(fromIndex, locationList.get(toIndex));
            locationList.set(toIndex, temp);

            // Optionally, you can update the LocationPanel view as well here
        }
    }
    // Method to update the main content area (weather or fallback panel)
    private void updateContentArea(JLayeredPane layeredPane, ArrayList<WeatherData> locationList) {
        // Define the bounds for the content area
        final int contentY = 130; // Start below the header and locationNavBar
        final int contentHeight = 850;

        // Remove existing content in the specified area
        for (Component comp : layeredPane.getComponents()) {
            if (comp.getBounds().y == contentY && comp.getBounds().height == contentHeight) {
                layeredPane.remove(comp);
            }
        }

        // Add new content based on the state of the location list
        if (locationList.isEmpty()) {
            // Fallback panel for empty location list
            JPanel fallbackPanel = new JPanel();
            fallbackPanel.setLayout(new BorderLayout());
            fallbackPanel.setPreferredSize(new Dimension(1920, contentHeight));

            // Add an image to the fallback panel
            JLabel imageLabel = new JLabel(new ImageIcon("path/to/your/image.png"));
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            fallbackPanel.add(imageLabel, BorderLayout.CENTER);

            // Wrap in a JScrollPane if needed
            JScrollPane fallbackScrollPane = new JScrollPane(fallbackPanel);
            fallbackScrollPane.setBounds(0, contentY, 1920, contentHeight);
            fallbackScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

            layeredPane.add(fallbackScrollPane, JLayeredPane.DEFAULT_LAYER);
        } else {
            // Weather panel for non-empty location list
            WeatherData latestLocation = locationList.get(locationList.size() - 1);
            WeatherPanel weatherPanel = new WeatherPanel(latestLocation.getLat(), latestLocation.getLon(),
                    latestLocation.getName(), latestLocation.getIndex(), this);

            // Set preferred size for scrolling
            weatherPanel.setPreferredSize(new Dimension(1920, 4500)); // Content height exceeds visible area

            // Create a JScrollPane for the WeatherPanel
            JScrollPane weatherScrollPane = new JScrollPane(weatherPanel);
            weatherScrollPane.setBounds(0, contentY, 1920, contentHeight);
            weatherScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            weatherScrollPane.getVerticalScrollBar().setUnitIncrement(20);

            layeredPane.add(weatherScrollPane, JLayeredPane.DEFAULT_LAYER);
        }

        // Refresh the layered pane to apply changes
        layeredPane.revalidate();
        layeredPane.repaint();
    }

}
