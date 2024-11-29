import Models.DailyData;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class HourlyHeader extends JPanel {
    private final JLabel dateLabel = new JLabel();
    private final JLabel tempLabel = new JLabel();
    private final JLabel descLabel = new JLabel();

    public HourlyHeader() {
        setLayout(new GridLayout(2, 1, 10, 5));

        // Top row (Date and temperature)
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        titlePanel.setOpaque(false);
        dateLabel.setFont(new Font("Default", Font.BOLD, 22));
        tempLabel.setFont(new Font("Default", Font.BOLD, 22));
        titlePanel.add(dateLabel);
        titlePanel.add(tempLabel);

        // Bottom row (Description)
        JPanel conditionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        conditionPanel.setOpaque(false);
        descLabel.setFont(new Font("Default", Font.BOLD, 18));
        conditionPanel.add(descLabel);

        add(titlePanel);
        add(conditionPanel);
        setBorder(BorderFactory.createEmptyBorder(15, 30, 0, 0)); // Top, Left, Bottom, Right padding
    }

    /**
     * Updates the content of the panel based on the given data.
     *
     * @param data The DailyData object containing the weather data.
     */
    public void update(DailyData data) {
        if (data == null) return;

        // Update the labels with new data
        dateLabel.setText(data.getTime());
        tempLabel.setText(data.getMaxTmp() + "° | " + data.getMinTmp() + "°");
        tempLabel.setIcon(
                WeatherPanel.resizeImageIcon(
                        loadIcon(WeatherPanel.getWeatherImagePath(data.getCondition(), data.isStatus())),
                        40, 40
                )
        );

        descLabel.setText(WeatherPanel.getWeatherDescription(data.getCondition()));
        // Repaint the panel to reflect the changes
        revalidate();
        repaint();
    }

    private ImageIcon loadIcon(String path) {
        try {
            return new ImageIcon(Objects.requireNonNull(WeatherPanel.class.getResource(path)));
        } catch (NullPointerException e) {
            System.err.println("Icon not found at path: " + path);
            return null;
        }
    }
}
