import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class MonthlyForecast extends JFrame {
    private JPanel calendarPanel;
    private LocalDate currentDate;
    private final Map<String, Color> weatherColors;
    private final String[] weatherIcons = {"☀", "⛅", "☁", "🌧", "⛈", "❄"};

    public MonthlyForecast() {
        currentDate = LocalDate.now();
        weatherColors = initializeWeatherColors();
        
        setTitle("Weather Forecast");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        setBackground(new Color(18, 18, 18));
        
        add(createHeader(), BorderLayout.NORTH);
        add(createMainContent(), BorderLayout.CENTER);

        ((JPanel)getContentPane()).setBorder(
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );
        
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private Map<String, Color> initializeWeatherColors() {
        Map<String, Color> colors = new HashMap<>();
        colors.put("☀", new Color(255, 193, 7));  // Sunny
        colors.put("⛅", new Color(158, 158, 158));  // Partly Cloudy
        colors.put("☁", new Color(96, 125, 139));  // Cloudy
        colors.put("🌧", new Color(3, 169, 244));  // Rain
        colors.put("⛈", new Color(63, 81, 181));  // Storm
        colors.put("❄", new Color(176, 190, 197));  // Snow
        return colors;
    }
    
    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JPanel monthPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        monthPanel.setOpaque(false);
        
        JButton prevMonth = createStyledButton("←");
        JLabel monthLabel = new JLabel(
            currentDate.format(DateTimeFormatter.ofPattern("MMMM yyyy")),
            SwingConstants.CENTER
        );
        monthLabel.setForeground(Color.BLACK);
        monthLabel.setFont(new Font("Arial", Font.BOLD, 20));
        JButton nextMonth = createStyledButton("→");
        
        prevMonth.addActionListener(e -> {
            currentDate = currentDate.minusMonths(1);
            updateCalendar();
            monthLabel.setText(currentDate.format(
                DateTimeFormatter.ofPattern("MMMM yyyy"))
            );
        });
        
        nextMonth.addActionListener(e -> {
            currentDate = currentDate.plusMonths(1);
            updateCalendar();
            monthLabel.setText(currentDate.format(
                DateTimeFormatter.ofPattern("MMMM yyyy"))
            );
        });
        
        monthPanel.add(prevMonth);
        monthPanel.add(monthLabel);
        monthPanel.add(nextMonth);
        
        header.add(monthPanel, BorderLayout.CENTER);
        
        return header;
    }
    
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setForeground(Color.BLACK);
        button.setBackground(new Color(45, 45, 45));
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        button.setFocusPainted(false);
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(60, 60, 60));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(45, 45, 45));
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
        calendarPanel.setOpaque(false);
        
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
            calendarPanel.add(createDayCell(LocalDate.of(
                currentDate.getYear(),
                currentDate.getMonth(),
                i
            )));
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

        String weatherIcon = weatherIcons[(int)(Math.random() * weatherIcons.length)];
        JLabel iconLabel = new JLabel(weatherIcon, SwingConstants.CENTER);
        iconLabel.setFont(new Font("Arial", Font.PLAIN, 32));
        iconLabel.setForeground(weatherColors.get(weatherIcon));

        int highTemp = 60 + (int)(Math.random() * 30);
        int lowTemp = highTemp - 10 - (int)(Math.random() * 10);
        JLabel tempLabel = new JLabel(
            highTemp + "°/" + lowTemp + "°",
            SwingConstants.CENTER
        );
        tempLabel.setForeground(Color.BLACK);
        tempLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        
        cell.add(dateLabel, BorderLayout.NORTH);
        cell.add(iconLabel, BorderLayout.CENTER);
        cell.add(tempLabel, BorderLayout.SOUTH);

        if (date.equals(LocalDate.now())) {
            cell.setBackground(Color.GRAY);
        }
        
        return cell;
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(
                UIManager.getSystemLookAndFeelClassName()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(MonthlyForecast::new);
    }
}