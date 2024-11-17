import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.Objects;

public class DailyButton extends JButton {
    private static DailyButton currentlyClickedButton = null; // Static reference to track the clicked button

    private CustomLabel Days = new CustomLabel(15, 20, 10, 100, 15, 1, "");
    private CustomLabel MaxTempurature = new CustomLabel(15, 85, 35, 50, 15, 1, "");
    private CustomLabel MinTempurature = new CustomLabel(15, 85, 55, 50, 15, 1, "");
    private CustomLabel WeatherCondition = new CustomLabel(15, 180, 35, 60, 15, 1, "");
    private CustomLabel WeatherRainyChance = new CustomLabel(15, 180, 55, 50, 15, 1, "");
    private CustomLabel WeatherIcon = new CustomLabel(45, 20, 35, 45, 45, 1, "");

    private int cornerRadius = 20; // Radius for the corners
    private Dimension normalSize = new Dimension(140, 90); // Normal size
    private Dimension clickedSize = new Dimension(260, 90); // Clicked size

    public DailyButton() {
        setLayout(null);
        setBackground(Color.WHITE);
        setPreferredSize(normalSize);
        setContentAreaFilled(false); // Allow custom painting of background

        // Add ActionListener for the button
        addActionListener(e -> handleClick());
    }

    public void fetchData(int MaxTmp, int MinTmp, String Time, double chance, String Condition) {
        MaxTempurature.setText(MaxTmp + "\u00B0C");
        MinTempurature.setText(MinTmp + "\u00B0C");
        Days.setText(Time);
        WeatherRainyChance.setText("" + chance+"%");
        WeatherIcon.setIcon(new ImageIcon(Objects.requireNonNull(WeatherPanel.class.getResource("/Assets/sunny_daily.png"))));
        WeatherCondition.setText("" + Condition);
        add(MaxTempurature);
        add(MinTempurature);
        add(Days);
        add(WeatherIcon);
        add(WeatherRainyChance);
        add(WeatherCondition);
    }

    public void handleClick() {
        // Reset the previously clicked button
        if (currentlyClickedButton != null && currentlyClickedButton != this) {
            currentlyClickedButton.setPreferredSize(normalSize);
            currentlyClickedButton.revalidate();
            currentlyClickedButton.repaint();
        }

        // Expand this button
        setPreferredSize(clickedSize);
        revalidate();
        repaint();

        // Update the currently clicked button reference
        currentlyClickedButton = this;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        // Enable antialiasing for smoother edges
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Set the background color and draw a rounded rectangle
        g2.setColor(getBackground());
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius));

        // Dispose the graphics context
        g2.dispose();

        super.paintComponent(g); // Paint the component’s content
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        // Set border color and draw rounded rectangle outline
        g2.setColor(Color.DARK_GRAY);
        g2.setStroke(new BasicStroke(2));
        g2.draw(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius));

        g2.dispose();
    }
}
