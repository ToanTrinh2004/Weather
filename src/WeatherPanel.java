import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;

public class WeatherPanel extends JPanel {
    private boolean status = true;
    private final CustomeLabel nameLabel;
    private final CustomeLabel temperatureLabel;
    private final CustomeLabel weatherConditionLabel;
    private final CustomeLabel humidityLabel;
    private final CustomeLabel windSpeedLabel;
    private final CustomeLabel timeLabel;
    private final CustomeLabel statusLabel;
    private final CustomeLabel pressureLabel;
    private final CustomeLabel currentLabel;
    private final JLabel weatherIcon;
    private final RoundedPanel currentWeather;
    private final JPanel weatherDetail;
    private final CustomeLabel dewpointLabel;
    private final CustomeLabel visibilityLabel;
    private final CustomeLabel uvLabel;
    private final CustomeLabel rainLabel;
    private final CustomeLabel feelslikeLabel;
    private ArrayList<String> dailyDays = new ArrayList<>();
    private ArrayList<Integer> dailyMaxTmp = new ArrayList<>();
    private ArrayList<Integer> dailyMinTmp = new ArrayList<>();
    private ArrayList<String> dailyCondition = new ArrayList<>();
    private ArrayList<Double> dailyRainyChance = new ArrayList<>();
    private ArrayList<DailyButton> dailyButtons = new ArrayList<>();





    // Constructor for the WeatherPanel
    public WeatherPanel(double latitude, double longitude, String name) {
        setLayout(null); // 8 rows, 1 column, spacing of 10
        setBackground(Color.cyan);
        // Initialize labels for weather information
        nameLabel = new CustomeLabel(15, 10, 10, 200, 20, Font.PLAIN, "Country: " + name);
        temperatureLabel = new CustomeLabel(40, 100, 72, 200, 40, Font.BOLD, "Temperature: ");
        weatherConditionLabel = new CustomeLabel(20, 100, 120, 300, 25, Font.PLAIN, "Weather Condition: ");
        weatherIcon = new JLabel();
        weatherIcon.setBounds(10, 65, 80, 80);
        timeLabel = new CustomeLabel(12, 10, 30, 200, 12, Font.PLAIN, "");
        statusLabel = new CustomeLabel(15, 100, 240, 200, 20, Font.PLAIN, "Day/Night: ");
        // detail Panel component
        pressureLabel = new CustomeLabel(11, 100, 280, 200, 30, Font.BOLD, "Pressure: ");
        humidityLabel = new CustomeLabel(11, 100, 160, 200, 30, Font.BOLD, "Humidity: ");
        windSpeedLabel = new CustomeLabel(11, 100, 200, 200, 30, Font.BOLD, "Wind Speed: ");
        dewpointLabel = new CustomeLabel(11, 100, 240, 200, 30, Font.BOLD, "Dew Point: ");
        visibilityLabel = new CustomeLabel(11, 100, 280, 200, 30, Font.BOLD, "Visibility: ");
        uvLabel = new CustomeLabel(11, 100, 320, 200, 30, Font.BOLD, "UV Index: ");
        rainLabel = new CustomeLabel(11, 100, 360, 200, 30, Font.BOLD, "Rain: ");
        feelslikeLabel = new CustomeLabel(11, 100, 400, 200, 30, Font.BOLD, "Feels Like: ");




        // Set up current weather panel
        currentWeather = new RoundedPanel(20, Color.white);
        currentWeather.setBounds(150, 70, 300, 300);
        currentWeather.setLayout(null);
        currentWeather.setBorder(new EmptyBorder(20, 20, 20, 20));
        // Set up for detailWeather
        weatherDetail  = new JPanel();

        weatherDetail.setBackground(Color.white);
        weatherDetail.setBounds(10,180,280,100);
        weatherDetail.setLayout(new GridLayout(2, 4, 10, 5));
        // Title label
        currentLabel = new CustomeLabel(15, 10, 10, 200, 20, Font.BOLD, "Current weather");

        // Add labels and icon to the current weather panel
        currentWeather.add(weatherDetail);
        currentWeather.add(currentLabel);
        currentWeather.add(timeLabel);
        currentWeather.add(weatherIcon);
        currentWeather.add(temperatureLabel);
        currentWeather.add(weatherConditionLabel);
        // add coponent for detail weather
        weatherDetail.add(humidityLabel);
        weatherDetail.add(windSpeedLabel);
        weatherDetail.add(pressureLabel);
        weatherDetail.add(dewpointLabel);
        weatherDetail.add(visibilityLabel);
        weatherDetail.add(feelslikeLabel);
        weatherDetail.add(rainLabel);
        weatherDetail.add(uvLabel);



        // Add the current weather panel to WeatherPanel
        add(currentWeather);
        // Initial Daily array list
        dailyDays = new ArrayList<>();
        dailyMaxTmp = new ArrayList<>();
        dailyMinTmp = new ArrayList<>();
        dailyCondition = new ArrayList<>();
        dailyRainyChance = new ArrayList<>();
        dailyButtons = new ArrayList<>();
        DailyPanel dailypanel = new DailyPanel();
        dailypanel.setBounds(150,400,970,100);
        dailypanel.setOpaque(false);
        add(dailypanel);
        try {
            for (int i = 0; i < 5; i++) {
                DailyButton b = new DailyButton();
                dailyButtons.add(b);
                dailypanel.add(b);
            }

        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bounds: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
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
            timeLabel.setText(time);  // set current time
            boolean status = ((boolean) weatherData.get("status"));
            setPanelBackground(status);
            temperatureLabel.setText(String.valueOf(weatherData.get("temperature"))); // set current temperature
            int weatherCode = (int) weatherData.get("weather_condition"); // get condition base on weather code
            weatherConditionLabel.setText(getWeatherDescription(weatherCode)); // set weather condition
            weatherIcon.setIcon(new ImageIcon(Objects.requireNonNull(WeatherPanel.class.getResource("/Assets/cloudy.png")))); // set icon for current panel

            humidityLabel.twoRowDisplay("Humidity",weatherData.get("humidity"),"%"); // set current humid value
            windSpeedLabel.twoRowDisplay("Wind speed",weatherData.get("windspeed"),"km/h"); // set current wind speed
            pressureLabel.twoRowDisplay("Pressure",weatherData.get("pressure"),"hPa"); // set current pressure
            dewpointLabel.twoRowDisplay("Dew Point", weatherData.get("dewpoint"), "°C");
            visibilityLabel.twoRowDisplay("Visibility", weatherData.get("visibility"), "km");
            feelslikeLabel.twoRowDisplay("Feels Like", weatherData.get("feelslike"), "°C");
            rainLabel.twoRowDisplay("Rainy Sum", weatherData.get("rainyChance"), "mm");
            uvLabel.twoRowDisplay("UV Index", weatherData.get("uv"), "");
            // daily
            JSONArray dailyTimeArray = (JSONArray) weatherData.get("DailyTime");
            JSONArray dailyMaxTmpArray = (JSONArray) weatherData.get("DailyMaxTemperature");// Assuming it's a JSONArray
            JSONArray dailyMinTmpArray = (JSONArray) weatherData.get("DailyMinTemperature");
            JSONArray dailyWeatherCode = (JSONArray) weatherData.get("DailyWeatherCode");
            JSONArray dailyRainyArray = (JSONArray) weatherData.get("DailyRainyChance");
            for (int i = 0; i < 5; i++) {
                String days = dailyTimeArray.get(i).toString();
                int maxtmp = (int) Math.round((double) dailyMaxTmpArray.get(i));
                int mintmp = (int) Math.round((double) dailyMinTmpArray.get(i));
                int code = 1;
                double chance = (double) dailyRainyArray.get(i);
                dailyButtons.get(i).fetchData(maxtmp,mintmp,days,chance,getWeatherDescription(code));
            }
            dailyButtons.get(0).handleClick();


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
    public void setPanelBackground(boolean status){
        if(status){
            setBackground(Color.decode("#C7FDFF"));
        }else {
            setBackground(Color.decode("#2E06A1"));
        }

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
