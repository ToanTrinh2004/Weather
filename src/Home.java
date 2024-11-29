import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class Home extends JFrame {
    public static ArrayList<WeatherData> locationList = new ArrayList<>();
    private WeatherPanel weatherPanel;
    private final LocationNavBar locationNavBar;
    private final SearchField searchField;
    private ImageBackgroundPanel backgroundPanel;

    public Home() {
        // Set up the frame
        setTitle("Weather Forecast");
        setSize(1920, 1080);
        // Adjusted frame size for better fit
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon(Objects.requireNonNull(Home.class.getResource("/Assets/Logo.png"))).getImage());
        setLayout(null);
        // Create a layered pane
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 1920, 1080);// Set bounds to match the frame
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
        backgroundPanel = new ImageBackgroundPanel("Assets/initialHome.png");
        backgroundPanel.setBounds(0, 130, 1920, 850); // Set the position for the background
        layeredPane.add(backgroundPanel, JLayeredPane.DEFAULT_LAYER);
        setVisible(true);
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
            JLayeredPane layeredPane = (JLayeredPane) getContentPane().getComponent(0); // Get the layeredPane
            WeatherData latestLocation = locationList.get(locationList.size() - 1);

            // Check if this is the first location being added
            if (weatherPanel == null) {
                // Remove the ImageBackgroundPanel if present
                if (backgroundPanel != null) {
                    layeredPane.remove(backgroundPanel);
                    backgroundPanel = null; // Dereference for garbage collection
                }

                // Initialize WeatherPanel
                weatherPanel = new WeatherPanel(latestLocation.getLat(), latestLocation.getLon(), latestLocation.getName(), latestLocation.getIndex(), this);

                // Set a preferred size for the WeatherPanel to enable scrolling
                weatherPanel.setPreferredSize(new Dimension(1920, 4500));

                // Create a JScrollPane for the WeatherPanel
                JScrollPane weatherScrollPane = new JScrollPane(weatherPanel);
                weatherScrollPane.setBounds(0, 130, 1920, 850);
                weatherScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                weatherScrollPane.getVerticalScrollBar().setUnitIncrement(20);

                // Add the scroll pane to the layeredPane
                layeredPane.add(weatherScrollPane, JLayeredPane.DEFAULT_LAYER);
            } else {
                // Update the WeatherPanel as usual
                weatherPanel.updateWeatherPanel(latestLocation.getLat(), latestLocation.getLon(), latestLocation.getName(), latestLocation.getIndex());
            }

            // Refresh the LocationNavBar buttons
            locationNavBar.refreshButtons();

            // Repaint and revalidate the layered pane to reflect changes
            layeredPane.revalidate();
            layeredPane.repaint();
        }
    }





}
