import Models.DailyData;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class HourlyHeader extends JPanel {
    private JLabel dateLabel = new JLabel();
    private JLabel tempLabel = new JLabel();
    private JLabel descLabel = new JLabel();

    public HourlyHeader(DailyData data) {
        setLayout(new GridLayout(2, 1,10,5));
        // Top row (Date and temperature)
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        titlePanel.setOpaque(false);
        dateLabel.setText(data.getTime());
        tempLabel.setIcon(loadIcon("/Assets/sunny_daily.png"));
        tempLabel.setText(data.getMaxTmp() + "° | " + data.getMinTmp() + "°");
        dateLabel.setFont(new Font("Default",Font.BOLD,22));
        tempLabel.setFont(new Font("Default",Font.BOLD,22));
        titlePanel.add(dateLabel);
        titlePanel.add(tempLabel);

        // Bottom row (Description)
        JPanel conditionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        conditionPanel.setBackground(Color.lightGray);
        conditionPanel.setOpaque(false);
        descLabel.setText(data.getCondition());
        descLabel.setFont(new Font("Default",Font.BOLD,18));
        conditionPanel.add(descLabel);

        add(titlePanel);
        add(conditionPanel);
        setBorder(BorderFactory.createEmptyBorder(15, 30, 0, 0)); // Top, Left, Bottom, Right padding
    }

    private ImageIcon loadIcon(String path) {
        try {
            return new ImageIcon(Objects.requireNonNull(WeatherPanel.class.getResource(path)));
        } catch (NullPointerException e) {
            System.err.println("Icon not found at path: " + path);
            return null; // Optionally, return a fallback icon
        }
    }
}
