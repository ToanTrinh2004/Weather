import org.json.simple.JSONObject;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class WeatherPanel extends JPanel {
    private JLabel nameLabel;
    private JLabel temperatureLabel;
    private JLabel weatherConditionLabel;
    private JLabel humidityLabel;
    private JLabel windSpeedLabel;
    private JLabel timeLabel;
    private JLabel statusLabel;
    private JLabel pressureLabel;
    private JLabel currentLabel;
    private JLabel weatherIcon;
    private RoundedPanel currentWeather;



    // Constructor for the WeatherPanel
    public WeatherPanel(double latitude, double longitude, String name) {
        setLayout(null); // 8 rows, 1 column, spacing of 10
        setBackground(Color.cyan);
        // Initialize labels for weather information
        nameLabel = new JLabel("Country: " + name);
        temperatureLabel = new JLabel("Temperature: ");
        weatherConditionLabel = new JLabel("Weather Condition: ");
        humidityLabel = new JLabel("Humidity: ");
        windSpeedLabel = new JLabel("Wind Speed: ");
        timeLabel = new JLabel("");
        statusLabel = new JLabel("Day/Night: ");
        pressureLabel = new JLabel("Pressure: ");
        currentLabel = new JLabel("Current weather");
        weatherIcon = new JLabel();

        // current panel
        currentWeather  = new RoundedPanel(20,Color.white);
        currentWeather.setBackground(Color.white);
        currentWeather.setBounds(100,70,300,300);//set position for panel
        currentWeather.setLayout(null);// set layout null
        currentWeather.setBorder(new EmptyBorder(20, 20, 20, 20)); // set padding 20px
        // Add labels and button to the panel
        currentWeather.add(currentLabel);
        currentLabel.setFont(new Font("Default", Font.BOLD, 15));
        currentLabel.setBounds(10,10,200,20);
        currentWeather.add(timeLabel);
        timeLabel.setFont(new Font("Default", Font.PLAIN, 12));
        timeLabel.setForeground(Color.GRAY);
        timeLabel.setBounds(10,30,200,12);
        weatherIcon.setBounds(10,65,80,80);
        temperatureLabel.setBounds(100,72,200,40);
        temperatureLabel.setFont(new Font("Default",Font.BOLD,40));
        weatherConditionLabel.setBounds(100,120,300,20);
        weatherConditionLabel.setFont(new Font("Default",Font.PLAIN,20));
        currentWeather.add(weatherIcon);
        currentWeather.add(statusLabel);
        currentWeather.add(temperatureLabel);
        currentWeather.add(weatherConditionLabel);
        currentWeather.add(humidityLabel);
        currentWeather.add(windSpeedLabel);
        currentWeather.add(pressureLabel);
        add(currentWeather);

        // Set action to fetch and display weather data on button click


        // Initial load of weather data
        fetchAndDisplayWeatherData(latitude, longitude, name);
    }

    // Method to fetch and display weather data
    private void fetchAndDisplayWeatherData(double latitude, double longitude, String name) {
        JSONObject weatherData = WeatherApi.getWeatherData(latitude, longitude);

        if (weatherData != null) {
            // Update labels with fetched weather data
            nameLabel.setText("Country: " + name);
            String fullTime = (String) weatherData.get("time"); // Get the time string from the JSON
            String time = fullTime.substring(11); // Extract the time part
            timeLabel.setText("" + time); // Set the label text
            statusLabel.setText("Day/Night: " + ((Boolean) weatherData.get("status") ? "Day" : "Night"));
            temperatureLabel.setText("" + weatherData.get("temperature"));
            int weatherCode = (int) weatherData.get("weather_condition");
            weatherConditionLabel.setText(getWeatherDescription(weatherCode));
            weatherIcon.setIcon(new ImageIcon(Objects.requireNonNull(WeatherPanel.class.getResource("/Assets/cloudy.png"))));

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
    public static String getWeatherDescription(int code) {
        switch (code) {
            case 0:
                return "Clear sky";
            case 1:
                return "Mainly clear";
            case 2:
                return "Partly cloudy";
            case 3:
                return "Overcast";
            case 45:
                return "Fog";
            case 48:
                return "Depositing fog";
            case 51:
                return "Light intensity";
            case 53:
                return "Moderate intensity";
            case 55:
                return "Dense intensity";
            case 56:
                return "Freezing drizzle: Light intensity";
            case 57:
                return "Freezing drizzle: Dense intensity";
            case 61:
                return "Rain: Slight intensity";
            case 63:
                return "Rain: Moderate intensity";
            case 65:
                return "Rain: Heavy intensity";
            case 66:
                return "Freezing rain: Light intensity";
            case 67:
                return "Freezing rain: Heavy intensity";
            case 71:
                return "Snow fall: Slight intensity";
            case 73:
                return "Snow fall: Moderate intensity";
            case 75:
                return "Snow fall: Heavy intensity";
            case 77:
                return "Snow grains";
            case 80:
                return "Rain showers: Slight intensity";
            case 81:
                return "Rain showers: Moderate intensity";
            case 82:
                return "Rain showers: Violent intensity";
            case 85:
                return "Snow showers: Slight intensity";
            case 86:
                return "Snow showers: Heavy intensity";
            case 95:
                return "Thunderstorm: Slight or moderate";
            case 96:
                return "Thunderstorm with slight hail";
            case 99:
                return "Thunderstorm with heavy hail";
            default:
                return "Unknown weather condition"; // Default case for unknown codes
        }
    }
}
