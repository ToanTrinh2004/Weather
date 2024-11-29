import Models.DailyData;
import Models.HourlyData;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class WeatherPanel extends JPanel {
    private boolean status = true;
    private final CustomLabel nameLabel;
    private final CustomLabel temperatureLabel;
    private final CustomLabel weatherConditionLabel;
    private final CustomLabel humidityLabel;
    private final CustomLabel windSpeedLabel;
    private final CustomLabel timeLabel;
    private final CustomLabel statusLabel;
    private final CustomLabel pressureLabel;
    private final CustomLabel currentLabel;
    private final JLabel weatherIcon;
    private final RoundedPanel currentWeather;
    private final JPanel weatherDetail;
    private final CustomLabel dewpointLabel;
    private final CustomLabel visibilityLabel;
    private final CustomLabel uvLabel;
    private final CustomLabel rainLabel;
    private final CustomLabel feelslikeLabel;
    private ArrayList<String> dailyDays = new ArrayList<>();
    private ArrayList<Integer> dailyMaxTmp = new ArrayList<>();
    private ArrayList<Integer> dailyMinTmp = new ArrayList<>();
    private ArrayList<String> dailyCondition = new ArrayList<>();
    private ArrayList<Double> dailyRainyChance = new ArrayList<>();
    private ArrayList<DailyData> dailyDataList = new ArrayList<>();
    private ArrayList<HourlyData> hourlyDataList = new ArrayList<>();
    private ArrayList<DailyButton> dailyButtons = new ArrayList<>();
    private LocationPanel LocationTitle;
    private Home home;
    private MonthlyForecast monthlyForecast;
    private HourlyPanel hourlyPanel;
    private WeatherMap m;
    private WeatherMap cl;





    // Constructor for the WeatherPanel
    public WeatherPanel(double latitude, double longitude, String name,int index,Home home) {
        setLayout(null); // 8 rows, 1 column, spacing of 10
        setBackground(Color.decode("#D6E7FA"));
        // Location title
        LocationTitle  = new LocationPanel();
        LocationTitle.setBounds(150 , 20,360,50);
        add(LocationTitle);
        // Initialize labels for weather information
        nameLabel = new CustomLabel(15, 10, 10, 200, 20, Font.PLAIN, "Country: " + name);
        temperatureLabel = new CustomLabel(40, 100, 72, 200, 40, Font.BOLD, "Temperature: ");
        weatherConditionLabel = new CustomLabel(20, 100, 120, 300, 25, Font.PLAIN, "Weather Condition: ");
        weatherIcon = new JLabel();
        weatherIcon.setBounds(10, 65, 80, 80);
        timeLabel = new CustomLabel(12, 10, 30, 200, 12, Font.PLAIN, "");
        statusLabel = new CustomLabel(15, 100, 240, 200, 20, Font.PLAIN, "Day/Night: ");
        // detail Panel component
        pressureLabel = new CustomLabel(11, 100, 280, 200, 30, Font.BOLD, "Pressure: ");
        humidityLabel = new CustomLabel(11, 100, 160, 200, 30, Font.BOLD, "Humidity: ");
        windSpeedLabel = new CustomLabel(11, 100, 200, 200, 30, Font.BOLD, "Wind Speed: ");
        dewpointLabel = new CustomLabel(11, 100, 240, 200, 30, Font.BOLD, "Dew Point: ");
        visibilityLabel = new CustomLabel(11, 100, 280, 200, 30, Font.BOLD, "Visibility: ");
        uvLabel = new CustomLabel(11, 100, 320, 200, 30, Font.BOLD, "UV Index: ");
        rainLabel = new CustomLabel(11, 100, 360, 200, 30, Font.BOLD, "Rain: ");
        feelslikeLabel = new CustomLabel(11, 100, 400, 200, 30, Font.BOLD, "Feels Like: ");
        // Set up current weather panel
        currentWeather = new RoundedPanel(20, Color.white);
        currentWeather.setBounds(150, 70, 300, 300);
        currentWeather.setLayout(null);
        currentWeather.setBorder(new EmptyBorder(20, 20, 20, 20));
        // Set up for detailWeather
        weatherDetail  = new JPanel();

        weatherDetail.setBackground(Color.white);
        weatherDetail.setBounds(15,180,280,100);
        weatherDetail.setLayout(new GridLayout(2, 4, 10, 5));
        // Title label
        currentLabel = new CustomLabel(15, 10, 10, 200, 20, Font.BOLD, "Current weather");

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
        dailypanel.setBounds(130,400,1100,100);
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
        m = new WeatherMap(longitude,latitude,"TA2");
        cl = new WeatherMap(longitude,latitude,"CL");
        add(m);
        add(cl);
        cl.setBounds(790,70,300,300);
        m.setBounds(470,70,300,300);
//        monthlyForecast = new MonthlyForecast();
//        monthlyForecast.setBounds(140,500,1000,1000);
//        add(monthlyForecast);
        hourlyPanel = new HourlyPanel();
        hourlyPanel.setBounds(125,530,990,1900);
        hourlyPanel.setOpaque(false);
        add(hourlyPanel);

        fetchAndDisplayWeatherData(latitude, longitude, name,index);
        
        monthlyForecast = new MonthlyForecast();
        monthlyForecast.setBounds(150, 2450, 935, 950);
        add(monthlyForecast);

    }


    // Method to fetch and display weather data
    private void fetchAndDisplayWeatherData(double latitude, double longitude, String name,int index) {
        JSONObject weatherData = WeatherApi.getWeatherData(latitude, longitude);

        if (weatherData != null) {
            // Update labels with fetched weather data
            nameLabel.setText("Country: " + name);
            String fullTime = (String) weatherData.get("time"); // Get the time string from the JSON
            String time = fullTime.substring(11); // Extract the time part
            timeLabel.setText(time);  // set current time
            boolean status = ((boolean) weatherData.get("status"));
            setPanelBackground(status);
            LocationTitle.setNameForLocation(name,status,index);
            temperatureLabel.setText(String.valueOf(weatherData.get("temperature"))+"\u00B0C"); // set current temperature
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
            JSONArray dailyMaxTmpArray = (JSONArray) weatherData.get("DailyMaxTemperature");
            JSONArray dailyMinTmpArray = (JSONArray) weatherData.get("DailyMinTemperature");
            JSONArray dailyWeatherCode = (JSONArray) weatherData.get("DailyWeatherCode");
            JSONArray dailyRainyArray = (JSONArray) weatherData.get("DailyRainyChance");
            for (int i = 0; i < 5; i++) {
                String days = formatDate(dailyTimeArray.get(i).toString()); //
                int maxtmp = (int) Math.round((double) dailyMaxTmpArray.get(i));
                int mintmp = (int) Math.round((double) dailyMinTmpArray.get(i));
                int code = ((Number) dailyWeatherCode.get(i)).intValue();
                double chance = (double) dailyRainyArray.get(i);
                DailyData data = new DailyData(maxtmp, mintmp, days, chance, getWeatherDescription(code));
                dailyDataList.add(data);
                dailyButtons.get(i).fetchData(data);
            }
            dailyButtons.get(0).handleClick();

            // Hourly
            JSONArray hourlyTimeArray = (JSONArray) weatherData.get("HourlyTime");
            JSONArray hourlyTemperatureArray = (JSONArray) weatherData.get("HourlyTemperature");
            JSONArray hourlyDewPointArray = (JSONArray) weatherData.get("HourlyDewPoint");
            JSONArray hourlyRainyChanceArray = (JSONArray) weatherData.get("HourlyRainyChance");
            JSONArray hourlyWeatherCodeArray = (JSONArray) weatherData.get("HourlyWeatherCode");
            JSONArray hourlyVisibilityArray = (JSONArray) weatherData.get("HourlyVisibility");
            JSONArray hourlyWindspeedArray = (JSONArray) weatherData.get("HourlyWindspeed");

            ArrayList<HourlyData> hourlyDataList = new ArrayList<>();

            // Define batch size
            int batchSize = 24;

            // Create hourly data in batches
            for (int i = 0; i < 5; i++) { // Assuming you want 5 batches
                int start = i * batchSize;
                int end = Math.min(start + batchSize, hourlyTimeArray.size()); // Ensure no out-of-bounds

                HourlyData h = new HourlyData();

                // Extract sublists for each property
                h.setHourlyTime(getSubList(hourlyTimeArray, start, end));
                h.setHourlyTemperature(getSubList(hourlyTemperatureArray, start, end));
                h.setHourlyDewPoint(getSubList(hourlyDewPointArray, start, end));
                h.setHourlyRainyChance(getSubList(hourlyRainyChanceArray, start, end));
                h.setHourlyWeatherCode(getSubList(hourlyWeatherCodeArray, start, end));
                h.setHourlyVisibility(getSubList(hourlyVisibilityArray, start, end));
                h.setHourlyWindspeed(getSubList(hourlyWindspeedArray, start, end));

                hourlyDataList.add(h);}
            hourlyPanel.fetchData(hourlyDataList.get(0));
            // update new map
            m.fetchMap(longitude,latitude);
            cl.fetchMap(longitude,latitude);



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
    private <T> ArrayList<T> getSubList(JSONArray array, int start, int end) {
        ArrayList<T> subList = new ArrayList<>();
        for (int j = start; j < end; j++) {
            Object value = array.get(j); // Get the raw value
            if (value instanceof Number) {
                subList.add((T) value); // Cast numbers safely
            } else if (value instanceof String) {
                subList.add((T) value); // Cast strings safely
            } else {
                // Handle unexpected data types if needed
                throw new IllegalArgumentException("Unsupported data type in JSONArray: " + value.getClass());
            }
        }
        return subList;
    }

    // Method to update the WeatherPanel with new location data
    public void updateWeatherPanel(double newLatitude, double newLongitude, String newName, int index) {
        nameLabel.setText("Country: " + newName);
        fetchAndDisplayWeatherData(newLatitude, newLongitude, newName,index);
    }
    public void setPanelBackground(boolean status){
        if(status){
            setBackground(Color.decode("#e0eefd"));
        }else {
            setBackground(Color.decode("#2b4b7f"));
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
                return "Freezing drizzle";
            case 57:
                return "Freezing drizzle";
            case 61:
                return "Rain Slight";
            case 63:
                return "Rain Moderate ";
            case 65:
                return "Rain intensity";
            case 66:
                return "Freezing rain";
            case 67:
                return "Freezing rain";
            case 71:
                return "Snow fall";
            case 73:
                return "Snow fall";
            case 75:
                return "Snow fall";
            case 77:
                return "Snow grains";
            case 80:
                return "Rain showers";
            case 81:
                return "Rain showers";
            case 82:
                return "Rain showers";
            case 85:
                return "Snow showers";
            case 86:
                return "Snow showers:";
            case 95:
                return "Thunderstorm";
            case 96:
                return "Thunderstorm";
            case 99:
                return "Thunderstorm";
            default:
                return "Unknown weather condition"; // Default case for unknown codes
        }
    }
    public static String formatDate(String inputDate) {
        // Parse the input date
        LocalDate date = LocalDate.parse(inputDate);

        // Get today's date
        LocalDate today = LocalDate.now();

        // Check if the input date is today
        if (date.equals(today)) {
            return "Today";
        } else {
            // Format as "DayOfWeek dd/MM"
            return date.getDayOfWeek()
                    .getDisplayName(TextStyle.SHORT, Locale.ENGLISH) // Get short day name (e.g., Mon, Tue)
                    + " " + date.format(DateTimeFormatter.ofPattern("dd/MM"));
        }
    }
}
