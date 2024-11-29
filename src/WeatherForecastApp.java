import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class WeatherForecastApp extends JFrame {

    private JPanel mainPanel;
    private JPanel detailedForecastPanel;
    private String currentDisplayedDate = "";
    private final HashMap<String, List<Object[]>> weatherDataCache = new HashMap<>();

    public WeatherForecastApp() {
        setTitle("Weather Forecast");
        setSize(900, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mainPanel = new JPanel(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        JPanel headerPanel = createWeatherHeader();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        detailedForecastPanel = new JPanel();
        mainPanel.add(detailedForecastPanel, BorderLayout.CENTER);

        showTodayForecast();
    }

    private JPanel createWeatherHeader() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 100));
        this.setBackground(Color.decode("#f2f2f2"));

        JLabel headerLabel = new JLabel("Hourly Weather Forecast", JLabel.LEFT);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(headerLabel, BorderLayout.CENTER);

        JPanel searchPanel = createSearchPanel();
        headerPanel.add(searchPanel, BorderLayout.EAST);

        JPanel upcomingDaysPanel = createUpcomingDaysPanel();
        headerPanel.add(upcomingDaysPanel, BorderLayout.SOUTH);

        return headerPanel;
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        this.setBackground(Color.decode("#f2f2f2"));

        JTextField searchField = new JTextField(15);
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));

        JButton searchButton = new JButton("Search");
        searchButton.setFont(new Font("Arial", Font.PLAIN, 14));
        searchButton.setBackground(Color.WHITE);
        searchButton.setBorder(BorderFactory.createLineBorder(new Color(150, 200, 220), 1));
        searchButton.setFocusPainted(false);

        searchButton.addActionListener(e -> {
            String location = searchField.getText().trim();
            if (!location.isEmpty()) {
                fetchWeatherData(location);
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a location.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        return searchPanel;
    }

    private void fetchWeatherData(String location) {
        String apiKey = "38afdd2f6b82e97c369e3965fd562080"; // Replace with your API key
        String apiUrl = String.format("https://api.open-meteo.com/v1/forecast?", location, apiKey);

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            parseWeatherData(response.toString());
            JOptionPane.showMessageDialog(this, "Weather data for " + location + " loaded successfully.", "Location Updated", JOptionPane.INFORMATION_MESSAGE);
            showTodayForecast();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Cannot load weather data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void parseWeatherData(String jsonData) {
        // Parse JSON data and update weatherDataCache.
        // For now, placeholder implementation with mock data:
        weatherDataCache.clear();
        currentDisplayedDate = "";
        List<Object[]> mockData = generateMockHourlyData();
        String today = new SimpleDateFormat("EEEE, MMMM dd, yyyy").format(new Date());
        weatherDataCache.put(today, mockData);
    }

    private JPanel createUpcomingDaysPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        this.setBackground(Color.decode("#f2f2f2"));

        List<Object[]> upcomingDaysData = generateMockUpcomingDaysData();
        for (Object[] dayData : upcomingDaysData) {
            String date = (String) dayData[0];
            JButton dayButton = new JButton(date);
            styleDayButton(dayButton);
            dayButton.addActionListener(e -> showDayForecast(date));
            buttonPanel.add(dayButton);
        }

        return buttonPanel;
    }

    private void styleDayButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setBackground(Color.WHITE);
        button.setBorder(BorderFactory.createLineBorder(new Color(150, 200, 220), 1));
        button.setFocusPainted(false);
    }

    private void showTodayForecast() {
        String currentDate = new SimpleDateFormat("EEEE, MMMM dd, yyyy").format(new Date());
        showDayForecast(currentDate);
    }

    private void showDayForecast(String selectedDate) {
        if (selectedDate.equals(currentDisplayedDate)) return;

        currentDisplayedDate = selectedDate;
        detailedForecastPanel.removeAll();
        detailedForecastPanel.setLayout(new BorderLayout());

        JLabel dayHeader = new JLabel("Hourly Forecast for " + selectedDate, JLabel.CENTER);
        dayHeader.setFont(new Font("Arial", Font.BOLD, 24));
        detailedForecastPanel.add(dayHeader, BorderLayout.NORTH);

        List<Object[]> hourlyForecastData = weatherDataCache.computeIfAbsent(selectedDate, k -> generateMockHourlyData());
        JPanel hourlyForecastPanel = createDayForecastPanel(hourlyForecastData);

        JScrollPane scrollPane = new JScrollPane(hourlyForecastPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        detailedForecastPanel.add(scrollPane, BorderLayout.CENTER);

        detailedForecastPanel.revalidate();
        detailedForecastPanel.repaint();
    }

    private JPanel createDayForecastPanel(List<Object[]> hourlyData) {
        JPanel dayForecastPanel = new JPanel();
        dayForecastPanel.setLayout(new BoxLayout(dayForecastPanel, BoxLayout.Y_AXIS));
        dayForecastPanel.setBackground(new Color(230, 240, 255));

        for (Object[] hourlyEntry : hourlyData) {
            dayForecastPanel.add(createHourlyEntry(hourlyEntry));
        }

        return dayForecastPanel;
    }

    private JPanel createHourlyEntry(Object[] forecastData) {
        JPanel entryPanel = new JPanel(new BorderLayout(10, 10));
        entryPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1), BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        entryPanel.setBackground(new Color(255, 255, 240));
        entryPanel.setMaximumSize(new Dimension(850, 120));

        JPanel leftPanel = createLeftPanel(forecastData);
        entryPanel.add(leftPanel, BorderLayout.WEST);

        JPanel detailsPanel = createDetailsPanel(forecastData);
        entryPanel.add(detailsPanel, BorderLayout.CENTER);

        return entryPanel;
    }

    private JPanel createLeftPanel(Object[] forecastData) {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.X_AXIS));
        leftPanel.setOpaque(false);

        String time = (String) forecastData[0];
        int temperature = (int) forecastData[3];
        JLabel timeLabel = new JLabel(String.format("%s - %d°C", time, temperature));
        timeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        leftPanel.add(timeLabel);

        return leftPanel;
    }

    private JPanel createDetailsPanel(Object[] forecastData) {
        JPanel detailsPanel = new JPanel(new GridLayout(2, 5));
        detailsPanel.setOpaque(false);

        detailsPanel.add(new JLabel("Condition: " + forecastData[2]));  // Forecast condition (e.g., Sunny)
        detailsPanel.add(new JLabel("Temp: " + forecastData[3] + "°C")); // Temperature
        detailsPanel.add(new JLabel("Feels Like: " + forecastData[4] + "°C")); // Feels Like
        detailsPanel.add(new JLabel("Wind: " + forecastData[5] + " mph")); // Wind speed
        detailsPanel.add(new JLabel("Wind Gust: " + forecastData[6] + " mph")); // Wind gust
        detailsPanel.add(new JLabel("Humidity: " + forecastData[7] + "%")); // Humidity
        detailsPanel.add(new JLabel("Dew Point: " + forecastData[8] + "°C")); // Dew point
        detailsPanel.add(new JLabel("UV Index: " + forecastData[9])); // UV index
        detailsPanel.add(new JLabel("Cloud Cover: " + forecastData[10] + "%")); // Cloud cover
        detailsPanel.add(new JLabel("Pressure: " + forecastData[11] + " in")); // Pressure

        return detailsPanel;
    }

    private List<Object[]> generateMockHourlyData() {
        List<Object[]> hourlyData = new ArrayList<>();

        // Predefined weather conditions based on time of day
        String[] dayConditions = {"Sunny", "Partly Sunny",};
        String[] nightConditions = {"Mostly clear", "Mostly cloudy", "Clear", "Nighttime showers"};

        // Time range for day and night
        for (int hour = 0; hour < 24; hour++) {
            String time = String.format("%02d:00", hour);
            String condition;
            int temperature;
            int feelsLike;

            if (hour >= 6 && hour < 18) { // Daytime (6:00 AM - 6:00 PM)
                // Assign realistic conditions for daytime
                condition = getDaytimeCondition(hour);
                temperature = getTemperatureForDayCondition(condition);
                feelsLike = getFeelsLikeForTemperature(temperature);
            } else { // Nighttime (6:00 PM - 6:00 AM)
                // Assign realistic conditions for nighttime
                condition = getNighttimeCondition(hour);
                temperature = getTemperatureForNightCondition(condition);
                feelsLike = getFeelsLikeForTemperature(temperature);
            }

            // Add hourly data for the given hour
            hourlyData.add(new Object[]{
                    time,  // Time (e.g., "06:00")
                    "",    // Placeholder for icon or additional data
                    condition,  // Condition (e.g., Sunny, Mostly clear, etc.)
                    temperature,  // Temperature
                    feelsLike,  // Feels Like temperature
                    5 + hour % 10,   // Wind Speed (random but consistent for each hour)
                    10 + hour % 15,  // Wind Gust (random for variation)
                    50 + hour % 50,  // Humidity (more variation)
                    10 + hour % 6,   // Dew Point
                    hour % 7,         // UV Index
                    50 + hour % 50,   // Cloud Cover
                    29 + (hour % 10)  // Pressure
            });
        }

        return hourlyData;
    }

    private String getDaytimeCondition(int hour) {
        if (hour >= 6 && hour < 8) {
            return "Mostly Sunny"; // Morning
        } else if (hour >= 9 && hour < 17) {
            return "Sunny"; // Midday
        } else {
            return "Partly Cloudy"; // Default for other daytime hours
        }
    }

    private String getNighttimeCondition(int hour) {
        if (hour >= 18 && hour < 21) {
            return "Mostly clear"; // Early evening
        } else if (hour >= 21 && hour < 24) {
            return "Mostly cloudy"; // Late night
        } else {
            return "Clear"; // Default for other nighttime hours
        }
    }


    private int getTemperatureForDayCondition(String condition) {
        switch (condition) {
            case "Sunny":
                return 25 + (int)(Math.random() * 10); // Daytime sunny 25°C to 35°C
            case "Partly cloudy":
                return 20 + (int)(Math.random() * 10); // Partly cloudy 20°C to 30°C
            case "Clear":
                return 18 + (int)(Math.random() * 7); // Clear 18°C to 25°C
            case "Light rain showers":
                return 15 + (int)(Math.random() * 7); // Light rain 15°C to 22°C
            default:
                return 20; // Default temperature
        }
    }

    private int getTemperatureForNightCondition(String condition) {
        switch (condition) {
            case "Mostly clear":
                return 15 + (int)(Math.random() * 7); // Nighttime clear 15°C to 22°C
            case "Mostly cloudy":
                return 12 + (int)(Math.random() * 8); // Cloudy 12°C to 20°C
            case "Clear":
                return 10 + (int)(Math.random() * 5); // Clear 10°C to 15°C
            case "Nighttime showers":
                return 10 + (int)(Math.random() * 5); // Rainy 10°C to 15°C
            default:
                return 12; // Default temperature
        }
    }

    private int getFeelsLikeForTemperature(int temperature) {
        // Adjust feels like based on the actual temperature
        if (temperature > 30) {
            return temperature - 3; // High temperature feels slightly lower
        } else if (temperature < 20) {
            return temperature + 2; // Cooler temperature feels warmer
        } else {
            return temperature; // Moderate temperature feels close to actual
        }
    }



    private List<Object[]> generateMockUpcomingDaysData() {
        List<Object[]> upcomingDays = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMMM dd, yyyy");

        for (int i = 0; i < 5; i++) {
            upcomingDays.add(new Object[]{sdf.format(calendar.getTime())});
            calendar.add(Calendar.DATE, 1);
        }

        return upcomingDays;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WeatherForecastApp app = new WeatherForecastApp();
            app.setVisible(true);
        });
    }
}