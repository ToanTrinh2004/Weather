import javax.swing.*;
import java.util.Objects;

public class Home extends JFrame {
    public Home() {
        // Set up the frame
        setTitle("Weather Forecast");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon(Objects.requireNonNull(Home.class.getResource("/Assets/Logo.png"))).getImage());
        setLayout(null);

        // Create a layered pane
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 1920, 1080);
        add(layeredPane);

        // Add Header to the layered pane
        Home_Header header = new Home_Header();
        header.setBounds(0, 0, 1920, 50); // Adjust the height as needed
        layeredPane.add(header, JLayeredPane.DEFAULT_LAYER);

        // Add SearchField on top of the Header
        SearchField searchField = new SearchField();
        searchField.setBounds(1000, 0, 250, 400); // Adjust position to lay on Header
        layeredPane.add(searchField, JLayeredPane.PALETTE_LAYER);
//        LocationNavBar locationNavBar = new LocationNavBar();
//        layeredPane.add(locationNavBar,JLayeredPane.DEFAULT_LAYER);
        WeatherPanel weatherPanel = new WeatherPanel(35.6895,139.69171);
        layeredPane.add(weatherPanel,layeredPane.DEFAULT_LAYER);
        setVisible(true);
    }
}
