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
        setVisible(true);
    }
}
