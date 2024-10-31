import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WeatherPanel extends JPanel {
    private JLabel temperatureLabel;
    private JLabel weatherConditionLabel;
    private JLabel humidityLabel;
    private JLabel windSpeedLabel;
    private JButton refreshButton;

    public WeatherPanel(double latitude, double longitude) {
        setLayout(new GridLayout(5, 1, 10, 10)); // 5 rows, 1 column, spacing of 10
        setPreferredSize(new Dimension(300, 200)); // Panel size
        setBounds(0,300,500,500);
        // Initialize labels for weather information
        temperatureLabel = new JLabel("Temperature: ");
        weatherConditionLabel = new JLabel("Weather Condition: ");
        humidityLabel = new JLabel("Humidity: ");
        windSpeedLabel = new JLabel("Wind Speed: ");

        // Initialize refresh button
        refreshButton = new JButton("Refresh Data");

        // Add labels and button to the panel
        add(temperatureLabel);
        add(weatherConditionLabel);
        add(humidityLabel);
        add(windSpeedLabel);
        add(refreshButton);

        // Set action to fetch and display weather data on button click
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fetchAndDisplayWeatherData(latitude, longitude);
            }
        });

        // Initial load of weather data
        fetchAndDisplayWeatherData(latitude, longitude);
    }

    private void fetchAndDisplayWeatherData(double latitude, double longitude) {
        JSONObject weatherData = WeatherApi.getWeatherData(latitude, longitude);

        if (weatherData != null) {
            // Update labels with fetched weather data
            temperatureLabel.setText("Temperature: " + weatherData.get("temperature") + "°C");
            weatherConditionLabel.setText("Weather Condition: " + weatherData.get("weather_condition"));
            humidityLabel.setText("Humidity: " + weatherData.get("humidity") + "%");
            windSpeedLabel.setText("Wind Speed: " + weatherData.get("windspeed") + " km/h");
        } else {
            // Display error message if data couldn't be fetched
            temperatureLabel.setText("Temperature: Data not available");
            weatherConditionLabel.setText("Weather Condition: Data not available");
            humidityLabel.setText("Humidity: Data not available");
            windSpeedLabel.setText("Wind Speed: Data not available");
        }
    }
}
