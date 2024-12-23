import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class HourlyCoponent extends JPanel {
    private JLabel TimeLabel = new JLabel();
    private JLabel Condition = new JLabel();
    private JLabel TempLabel = new JLabel();
    private JLabel RainyLabel = new JLabel();
    private JLabel VisibleLabel = new JLabel();
    private JLabel WindSpeed = new JLabel();
    private Color  defaultBackground= new Color(255, 255, 255, 200); // Lighter when hovered

    public HourlyCoponent(String time, Number code, Number tmp, Number dewPoint, Number chance, Number visibility, Number windSpeed) {
        setLayout(new GridLayout(1, 6, 30, 10));
        Font labelFont = new Font("Arial", Font.BOLD, 15);
        // Configure labels
        // Add labels to panel
        add(TimeLabel);
        add(Condition);
        add(TempLabel);
        add(RainyLabel);
        add(VisibleLabel);
        add(WindSpeed);
        setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0)); // Top, Left, Bottom, Right padding
        setBackground(defaultBackground);

        // Add hover effect
//        addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseEntered(MouseEvent e) {
//                setBackground(hoverBackground); // Lighter background on hover
//                repaint();
//            }
//
//            @Override
//            public void mouseExited(MouseEvent e) {
//                setBackground(defaultBackground); // Reset to default background
//                repaint();
//            }
//        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Set semi-transparent background
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(getBackground());
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // Draw a line at the bottom
        g2d.setColor(new Color(200, 200, 200, 150)); // Light gray
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
    }
    public void updateComponent(String time, Number code, Number tmp, Number dewPoint, Number chance, Number visibility, Number windSpeed,boolean status) {
        // Update each label's text
        System.out.println(WeatherPanel.getWeatherImagePath(code.intValue(), status));
        TimeLabel.setText(time.substring(11, 16));
        Condition.setText(WeatherPanel.getWeatherDescription(code.intValue()));
        Condition.setIcon(
                WeatherPanel.resizeImageIcon(
                        new ImageIcon(Objects.requireNonNull(
                                WeatherPanel.class.getResource(WeatherPanel.getWeatherImagePath(code.intValue(), status))
                        )),
                        30, // Width
                        30  // Height
                )
        );
        TempLabel.setText(tmp + "°C");
        RainyLabel.setText(chance + "%");
        RainyLabel.setIcon(WeatherPanel.resizeImageIcon(new ImageIcon(Objects.requireNonNull(WeatherPanel.class.getResource("/Assets/rainyChance.png"))),20,20));
        VisibleLabel.setText((visibility.doubleValue() / 1000.0) + " km");
        VisibleLabel.setIcon(WeatherPanel.resizeImageIcon(new ImageIcon(Objects.requireNonNull(WeatherPanel.class.getResource("/Assets/eye.png"))),20,20));
        WindSpeed.setText(windSpeed + " km/h");
        WindSpeed.setIcon(WeatherPanel.resizeImageIcon(new ImageIcon(Objects.requireNonNull(WeatherPanel.class.getResource("/Assets/windy.png"))),20,20));
        // Refresh the panel to reflect changes
        revalidate();
        repaint();
    }

}
