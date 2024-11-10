import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class Home extends JFrame {
    public static ArrayList<WeatherData> locationList = new ArrayList<>();
    private WeatherPanel weatherPanel; // Declare weatherPanel as an instance variable
    private LocationNavBar locationNavBar;
    private SearchField searchField;

    public Home() {
        // Default data
        WeatherData a = new WeatherData();
        a.setName("Ho Chi Minh City");
        a.setLon(106.6667);
        a.setLat(10.75);
        locationList.add(a);

        // Set up the frame
        setTitle("Weather Forecast");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon(Objects.requireNonNull(Home.class.getResource("/Assets/Logo.png"))).getImage());
        setLayout(null);

        // Create a layered pane
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(1920, 2000)); // Set height for scrolling
        JScrollPane scrollPane = new JScrollPane(layeredPane);
        scrollPane.setBounds(0, 0, 1920, 1080); // Set scroll pane size

        // Add the JScrollPane to the JFrame
        add(scrollPane);

        // Add Header to the layered pane
        Home_Header header = new Home_Header(this);
        header.setBounds(0, 0, 1920, 50); // Adjust the height as needed
        layeredPane.add(header, JLayeredPane.DEFAULT_LAYER);

        // Add SearchField on top of the Header
        SearchField searchField = new SearchField(this);
        searchField.setBounds(1000, 0, 250, 400); // Adjust position to lay on Header
        layeredPane.add(searchField, JLayeredPane.PALETTE_LAYER);

        // Initialize and add the LocationNavBar
        locationNavBar = new LocationNavBar(locationList, this);
        layeredPane.add(locationNavBar, JLayeredPane.DEFAULT_LAYER);

        // Initialize and add the WeatherPanel
        WeatherData latestLocation = locationList.get(locationList.size() - 1);
        weatherPanel = new WeatherPanel(latestLocation.getLat(), latestLocation.getLon(), latestLocation.getName());
        weatherPanel.setBounds(0, 100, 1920, 800); // Set the position and size of the weather panel
        layeredPane.add(weatherPanel, JLayeredPane.DEFAULT_LAYER);
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
            weatherPanel.updateWeatherPanel(location.getLat(), location.getLon(), location.getName());
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
}
