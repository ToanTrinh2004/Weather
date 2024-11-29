import Models.DailyData;
import Models.HourlyData;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class HourlyPanel extends JPanel {
    private JPanel hourlyComponentContainer = new JPanel();
    private ArrayList<HourlyCoponent> hourlyComponents = new ArrayList<>(); // Store hourly components
    private  HourlyHeader header = new HourlyHeader();
    public HourlyPanel() {
        setLayout(new BorderLayout()); // Use BorderLayout to center the container
        setPreferredSize(new Dimension(1000, 800)); // Adjust initial size if needed
        // Configure hourlyComponentContainer
        hourlyComponentContainer.setLayout(new BoxLayout(hourlyComponentContainer, BoxLayout.Y_AXIS));
        hourlyComponentContainer.setBorder(new EmptyBorder(20, 30, 20, 30)); // Add padding
        hourlyComponentContainer.setAlignmentX(Component.CENTER_ALIGNMENT);
        hourlyComponentContainer.setOpaque(false);
        // Add hourlyComponentContainer directly to the panel
        add(hourlyComponentContainer, BorderLayout.CENTER);
        header.setBackground(Color.cyan);
        header.setOpaque(true);
        header.setSize(1000,150);
        hourlyComponentContainer.add(header);
        // Initialize empty HourlyCoponent components
        for (int i = 0; i < 24; i++) {
            HourlyCoponent m = new HourlyCoponent("", 0, 0.0, 0.0, 0.0, 0.0, 0.0);
            m.setPreferredSize(new Dimension(940, 80)); // Match width minus padding
            m.setAlignmentX(Component.CENTER_ALIGNMENT);
            hourlyComponents.add(m); // Store the component in the list
            hourlyComponentContainer.add(m); // Add it to the container
        }

        setBackground(Color.cyan);
        setVisible(true);
    }

    // Function to update the existing components with new data
    public void fetchData(HourlyData data , DailyData daily) {
        // Update each HourlyCoponent with new data
        for (int i = 0; i < hourlyComponents.size(); i++) {
            HourlyCoponent m = hourlyComponents.get(i);
            // Update the text and values for the component
            m.updateComponent(
                    data.getHourlyTime().get(i),
                    data.getHourlyWeatherCode().get(i),
                    data.getHourlyTemperature().get(i),
                    data.getHourlyDewPoint().get(i),
                    data.getHourlyRainyChance().get(i),
                    data.getHourlyVisibility().get(i),
                    data.getHourlyWindspeed().get(i),
                    data.isStatus()
            );
        }
        header.update(daily);
        System.out.println("already repain ///");

        // Refresh the panel to ensure the updated values are displayed
        revalidate();
        repaint();
    }
}
