import javax.swing.*;
import java.awt.*;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class MonthlyForecast extends JFrame {
    private JPanel calendarPanel;
    private LocalDate currentDate;
    private JButton selectedMonthButton;
    private List<WeatherData> weatherDataList;

    public MonthlyForecast() {
        currentDate = LocalDate.now();
        weatherDataList = loadWeatherData();

        setTitle("Weather Forecast");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        add(createHeader(), BorderLayout.NORTH);
        add(createMainContent(), BorderLayout.CENTER);

        ((JPanel) getContentPane()).setBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private List<WeatherData> loadWeatherData() {
        List<WeatherData> dataList = new ArrayList<>();
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("Weather/src/MonthlyForecastData.json"));
            JSONArray jsonArray = (JSONArray) obj;

            for (Object item : jsonArray) {
                JSONObject jsonObject = (JSONObject) item;
                dataList.add(new WeatherData(
                        LocalDate.parse((String) jsonObject.get("date")),
                        (String) jsonObject.get("weather"),
                        ((Long) jsonObject.get("highTemp")).intValue(),
                        ((Long) jsonObject.get("lowTemp")).intValue()
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error loading weather data",
                    "Data Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        return dataList;
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JLabel yearLabel = new JLabel(
                String.valueOf(currentDate.getYear()),
                SwingConstants.CENTER
        );
        yearLabel.setForeground(Color.BLACK);
        yearLabel.setFont(new Font("Arial", Font.BOLD, 20));

        JPanel monthPanel = new JPanel(new GridLayout(1, 6, 5, 5));
        monthPanel.setOpaque(false);

        String[] months = {
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        };

        int currentMonth = currentDate.getMonthValue();
        for (int i = 0; i < 12; i++) {
            int monthIndex = (currentMonth - 1 + i) % 12 + 1;
            String monthName = months[monthIndex - 1];
            JButton monthButton = createStyledButton(monthName);
            int finalMonthIndex = monthIndex;
            monthButton.addActionListener(e -> {
                if (selectedMonthButton != null) {
                    selectedMonthButton.setBackground(new Color(250, 250, 250));
                }
                selectedMonthButton = monthButton;
                currentDate = currentDate.withMonth(finalMonthIndex);
                updateCalendar();

                selectedMonthButton.setBackground(new Color(210, 210, 210));
            });
            monthPanel.add(monthButton);

            if (monthIndex == currentMonth) {
                selectedMonthButton = monthButton;
                monthButton.setBackground(new Color(210, 210, 210));
            }
        }

        header.add(yearLabel, BorderLayout.NORTH);
        header.add(monthPanel, BorderLayout.CENTER);

        return header;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setForeground(Color.BLACK);
        button.setBackground(new Color(250, 250, 250));
        button.setFont(new Font("Arial", Font.BOLD, 14));

        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(true);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(230, 230, 230));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (button != selectedMonthButton) {
                    button.setBackground(new Color(250, 250, 250));
                }
            }
        });

        return button;
    }

    private JPanel createMainContent() {
        JPanel mainPanel = new JPanel(new BorderLayout(0, 20));
        mainPanel.setOpaque(false);

        JPanel weekDays = new JPanel(new GridLayout(1, 7));
        weekDays.setOpaque(false);
        String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (String day : days) {
            JLabel label = new JLabel(day, SwingConstants.CENTER);
            label.setForeground(Color.BLACK);
            label.setFont(new Font("Arial", Font.BOLD, 14));
            weekDays.add(label);
        }

        calendarPanel = new JPanel(new GridLayout(0, 7, 10, 10));
        mainPanel.add(weekDays, BorderLayout.NORTH);
        mainPanel.add(calendarPanel, BorderLayout.CENTER);

        updateCalendar();
        return mainPanel;
    }

    private void updateCalendar() {
        calendarPanel.removeAll();

        YearMonth yearMonth = YearMonth.from(currentDate);
        LocalDate firstOfMonth = yearMonth.atDay(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue() % 7;

        for (int i = 0; i < dayOfWeek; i++) {
            calendarPanel.add(createEmptyCell());
        }

        for (int i = 1; i <= yearMonth.lengthOfMonth(); i++) {
            LocalDate date = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), i);
            calendarPanel.add(createDayCell(date));
        }

        revalidate();
        repaint();
    }

    private JPanel createEmptyCell() {
        JPanel cell = new JPanel();
        cell.setOpaque(false);
        return cell;
    }

    private JPanel createDayCell(LocalDate date) {
        JPanel cell = new JPanel(new BorderLayout(5, 5));
        cell.setBackground(Color.WHITE);
        cell.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel dateLabel = new JLabel(String.valueOf(date.getDayOfMonth()));
        dateLabel.setForeground(Color.BLACK);
        dateLabel.setFont(new Font("Arial", Font.BOLD, 16));

        WeatherData dayData = weatherDataList.stream()
                .filter(wd -> wd.date.equals(date))
                .findFirst()
                .orElse(new WeatherData(date, "clear-day.png", 60, 45));

        String iconPath = "Weather/src/Assets/" + dayData.weather; // Đường dẫn thư mục biểu tượng
        ImageIcon weatherIcon = new ImageIcon(iconPath);

        JLabel iconLabel = new JLabel();
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Điều chỉnh kích thước icon theo kích thước cell
        cell.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                int size = Math.min(cell.getWidth(), cell.getHeight()) / 3; // Tỉ lệ icon (1/3 cell)
                Image img = weatherIcon.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
                iconLabel.setIcon(new ImageIcon(img));
            }
        });

        JLabel tempLabel = new JLabel(
                dayData.highTemp + "°/" + dayData.lowTemp + "°",
                SwingConstants.CENTER
        );
        tempLabel.setForeground(Color.BLACK);
        tempLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        cell.add(dateLabel, BorderLayout.NORTH);
        cell.add(iconLabel, BorderLayout.CENTER);
        cell.add(tempLabel, BorderLayout.SOUTH);

        if (date.equals(LocalDate.now())) {
            cell.setBackground(new Color(210, 210, 210));
        }

        return cell;
    }

    public static void main(String[] args) {
        new MonthlyForecast();
    }

    static class WeatherData {
        LocalDate date;
        String weather;
        int highTemp;
        int lowTemp;

        WeatherData(LocalDate date, String weather, int highTemp, int lowTemp) {
            this.date = date;
            this.weather = weather;
            this.highTemp = highTemp;
            this.lowTemp = lowTemp;
        }
    }
}
