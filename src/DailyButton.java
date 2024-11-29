import Models.DailyData;
import Models.HourlyData;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.Objects;

public class DailyButton extends JButton {
    private static DailyButton currentlyClickedButton = null; // Static reference to track the clicked button

    private CustomLabel Days = new CustomLabel(15, 20, 10, 100, 15, 1, "");
    private CustomLabel MaxTempurature = new CustomLabel(15, 85, 35, 50, 15, 1, "");
    private CustomLabel MinTempurature = new CustomLabel(15, 85, 55, 50, 15, 1, "");
    private CustomLabel WeatherCondition = new CustomLabel(15, 150, 35, 120, 18, 1, "");
    private CustomLabel WeatherRainyChance = new CustomLabel(15, 150, 55, 50, 15, 1, "");
    private CustomLabel WeatherIcon = new CustomLabel(45, 20, 35, 45, 45, 1, "");

    private int cornerRadius = 20;
    private Dimension normalSize = new Dimension(140, 90);
    private Dimension clickedSize = new Dimension(280, 90);
    public HourlyPanel hourlyPanel;
    private DailyData dailyData;
    private HourlyData hourlyData;

    public DailyButton(HourlyPanel hourlyPanel) {
        this.hourlyPanel = hourlyPanel;
        setLayout(null);
        setBackground(Color.WHITE);
        setPreferredSize(normalSize);
        setContentAreaFilled(false);
        addActionListener(e -> handleClick());
    }

    public void fetchData(DailyData data) {
        MaxTempurature.setText(data.getMaxTmp() + "\u00B0C");
        MinTempurature.setText(data.getMinTmp() + "\u00B0C");
        Days.setText(data.getTime());
        WeatherRainyChance.setText("" + data.getChance()+"%");
        WeatherIcon.setIcon(
                WeatherPanel.resizeImageIcon(
                        new ImageIcon(Objects.requireNonNull(
                                WeatherPanel.class.getResource(
                                        WeatherPanel.getWeatherImagePath(data.getCondition(), data.isStatus())
                                )
                        )),
                        45,
                        45
                )
        );

        WeatherCondition.setText("" + WeatherPanel.getWeatherDescription(data.getCondition()));
        add(MaxTempurature);
        add(MinTempurature);
        add(Days);
        add(WeatherIcon);
        add(WeatherRainyChance);
        add(WeatherCondition);
    }
    public void setData(DailyData dailyData, HourlyData hourlyData) {
        this.dailyData = dailyData;
        this.hourlyData = hourlyData;
        fetchData(dailyData);
    }
    public void handleClick() {
        // Reset previously clicked button
        if (currentlyClickedButton != null && currentlyClickedButton != this) {
            currentlyClickedButton.setPreferredSize(normalSize);
            currentlyClickedButton.revalidate();
            currentlyClickedButton.repaint();
        }
        setPreferredSize(clickedSize);
        revalidate();
        repaint();
        currentlyClickedButton = this;
        System.out.println("Clicked");
        hourlyPanel.fetchData(hourlyData, dailyData);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius));
        g2.dispose();
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(Color.DARK_GRAY);
        g2.setStroke(new BasicStroke(2));
        g2.draw(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius));
        g2.dispose();
    }
}
