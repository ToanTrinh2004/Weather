import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;
import java.util.Scanner;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Home_Header extends JPanel {

    JButton refresh = new JButton();
    JButton pin = new JButton();
    JButton favorites = new JButton();
    JButton search = new JButton();

    // Add a JList for displaying suggestions


    public Home_Header() {
        // Set bounds for the panel itself
        setBounds(0, 0, 1920, 50); // Full width
        setBackground(Color.decode("#f2f2f2"));
        setLayout(null);

        // Header text
        Label headerText = new Label("Forecast");
        headerText.setFont(new Font("Arial", Font.BOLD, 20)); // Set font and size
        headerText.setBounds(20, 10, 100, 30);
        add(headerText);

        // Refresh button
        setButtonStyle(refresh, "/Assets/refresh.png", 600);
        add(refresh);

        // Pin button
        setButtonStyle(pin, "/Assets/pin.png", 700);
        add(pin);

        // Favorites button
        setButtonStyle(favorites, "/Assets/star.png", 800);
        add(favorites);

        // Search button
        setButtonStyle(search, "/Assets/search_fs.png", 1150);
        addCustomHoverEffectToSearchButton(search);
        add(search);
        search.setBounds(1170, 5, 60, 40);

        // Search textfield


        // Initialize the suggestion list
    }
    // Method to style the buttons (general for all buttons)
    private void setButtonStyle(JButton button, String iconPath, int xPosition) {
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(Home_Header.class.getResource(iconPath)));
        Image img = icon.getImage();
        button.setIcon(new ImageIcon(img));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setBackground(Color.decode("#f2f2f2"));
        button.setBounds(xPosition, 0, 90, 50); // Set position and size

        // Add hover effect (default hover effect for all buttons)
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(Color.LIGHT_GRAY); // Change background color on hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(Color.decode("#f2f2f2")); // Reset background when not hovering
            }
        });
    }

    // Method to add a custom hover effect specifically for the search button
    private void addCustomHoverEffectToSearchButton(JButton searchButton) {
        searchButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                searchButton.setBackground(Color.BLUE); // Change background color to blue on hover
                ImageIcon hoverIcon = new ImageIcon(Objects.requireNonNull(Home_Header.class.getResource("/Assets/search_bs.png")));
                searchButton.setIcon(new ImageIcon(hoverIcon.getImage())); // Change image to Logo.png
            }

            @Override
            public void mouseExited(MouseEvent e) {
                searchButton.setBackground(Color.decode("#f2f2f2")); // Reset background when not hovering
                ImageIcon defaultIcon = new ImageIcon(Objects.requireNonNull(Home_Header.class.getResource("/Assets/search_fs.png")));
                searchButton.setIcon(new ImageIcon(defaultIcon.getImage())); // Reset image to original search icon
            }
        });
    }



}