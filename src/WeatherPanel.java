import org.json.simple.JSONObject;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WeatherPanel extends JPanel {
    private JLabel nameLabel;
    private JLabel temperatureLabel;
    private JLabel weatherConditionLabel;
    private JLabel humidityLabel;
    private JLabel windSpeedLabel;
    private JLabel timeLabel;
    private JLabel statusLabel;
    private JLabel pressureLabel;
    private JButton refreshButton;

    // Constructor for the WeatherPanel
    public WeatherPanel(double latitude, double longitude, String name) {
        setLayout(new GridLayout(8, 2, 10, 1)); // 8 rows, 1 column, spacing of 10
        setBackground(Color.cyan);
        // Initialize labels for weather information
        nameLabel = new JLabel("Country: " + name);
        temperatureLabel = new JLabel("Temperature: ");
        weatherConditionLabel = new JLabel("Weather Condition: ");
        humidityLabel = new JLabel("Humidity: ");
        windSpeedLabel = new JLabel("Wind Speed: ");
        timeLabel = new JLabel("Time: ");
        statusLabel = new JLabel("Day/Night: ");
        pressureLabel = new JLabel("Pressure: ");

        // Initialize refresh button
        refreshButton = new JButton("Refresh Data");

        // Add labels and button to the panel
        add(nameLabel);
        add(timeLabel);
        add(statusLabel);
        add(temperatureLabel);
        add(weatherConditionLabel);
        add(humidityLabel);
        add(windSpeedLabel);
        add(pressureLabel);
        add(refreshButton);

        // Set action to fetch and display weather data on button click
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fetchAndDisplayWeatherData(latitude, longitude, name);
            }
        });

        // Initial load of weather data
        fetchAndDisplayWeatherData(latitude, longitude, name);
    }

    // Method to fetch and display weather data
    private void fetchAndDisplayWeatherData(double latitude, double longitude, String name) {
        JSONObject weatherData = WeatherApi.getWeatherData(latitude, longitude);

        if (weatherData != null) {
            // Update labels with fetched weather data
            nameLabel.setText("Country: " + name);
            timeLabel.setText("Time: " + weatherData.get("time"));
            statusLabel.setText("Day/Night: " + ((Boolean) weatherData.get("status") ? "Day" : "Night"));
            temperatureLabel.setText("Temperature: " + weatherData.get("temperature") + "°C");
            weatherConditionLabel.setText("Weather Condition: " + weatherData.get("weather_condition"));
            humidityLabel.setText("Humidity: " + weatherData.get("humidity") + "%");
            windSpeedLabel.setText("Wind Speed: " + weatherData.get("windspeed") + " km/h");
            pressureLabel.setText("Pressure: " + weatherData.get("pressure") + " hPa");
        } else {
            // Display error message if data couldn't be fetched
            timeLabel.setText("Time: Data not available");
            statusLabel.setText("Day/Night: Data not available");
            temperatureLabel.setText("Temperature: Data not available");
            weatherConditionLabel.setText("Weather Condition: Data not available");
            humidityLabel.setText("Humidity: Data not available");
            windSpeedLabel.setText("Wind Speed: Data not available");
            pressureLabel.setText("Pressure: Data not available");
        }
    }

    // Method to update the WeatherPanel with new location data
    public void updateWeatherPanel(double newLatitude, double newLongitude, String newName) {
        nameLabel.setText("Country: " + newName);
        fetchAndDisplayWeatherData(newLatitude, newLongitude, newName);
    }
}
