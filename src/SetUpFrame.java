import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Objects;

public class SetUpFrame extends JFrame {
    private JList<String> suggestionList;
    private DefaultListModel<String> listModel;
    private JTextField searchInput;
    private ArrayList<Double> longitudes; // Store longitudes
    private ArrayList<Double> latitudes;  // Store latitudes
    private JButton confirmButton;

    public SetUpFrame() {
        // Frame setup
        setTitle("Weather Forecast");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon(Objects.requireNonNull(Home.class.getResource("/Assets/Logo.png"))).getImage());
        setLocationRelativeTo(null); // Center the frame on the screen
        setLayout(new BorderLayout(10, 10)); // Use BorderLayout with padding

        // Main content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout(10, 10));
        contentPanel.setBorder(new EmptyBorder(15, 15, 15, 15)); // Add padding around the panel
        add(contentPanel, BorderLayout.CENTER);

        // Title label
        JLabel titleLabel = new JLabel("Search for a Location");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(new EmptyBorder(10, 0, 15, 0));
        contentPanel.add(titleLabel, BorderLayout.NORTH);

        // Input and suggestions panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout(10, 10));

        // Search input
        searchInput = new JTextField("Search For Location");
        searchInput.setFont(new Font("Arial", Font.PLAIN, 16));
        searchInput.setForeground(Color.GRAY);
        searchInput.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        inputPanel.add(searchInput, BorderLayout.NORTH);

        // Add focus listener to clear placeholder text
        searchInput.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (searchInput.getText().equals("Search For Location")) {
                    searchInput.setText("");
                    searchInput.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (searchInput.getText().isEmpty()) {
                    searchInput.setText("Search For Location");
                    searchInput.setForeground(Color.GRAY);
                }
            }
        });

        // Suggestion list
        listModel = new DefaultListModel<>();
        suggestionList = new JList<>(listModel);
        suggestionList.setFont(new Font("Arial", Font.PLAIN, 14));
        suggestionList.setVisible(false);
        suggestionList.setBorder(new EmptyBorder(5, 5, 5, 5));
        suggestionList.setSelectionBackground(new Color(200, 220, 240));
        suggestionList.setFixedCellHeight(25);
        suggestionList.setFixedCellWidth(450);


        // Scroll pane for suggestion list
        JScrollPane scrollPane = new JScrollPane(suggestionList);
        scrollPane.setVisible(false); // Initially hidden
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        inputPanel.add(scrollPane, BorderLayout.CENTER);
        contentPanel.add(inputPanel, BorderLayout.CENTER);


        // Latitude and Longitude lists
        longitudes = new ArrayList<>();
        latitudes = new ArrayList<>();

        // Add DocumentListener to searchInput to update suggestions
        searchInput.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateSuggestions();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateSuggestions();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateSuggestions();
            }
        });

        // Add mouse listener to suggestion list for double-click selection
        suggestionList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedIndex = suggestionList.getSelectedIndex();
                    if (selectedIndex != -1) {
                        // Set the selected value in the search input
                        String selectedValue = suggestionList.getSelectedValue();
                        searchInput.setText(selectedValue);

                        // Hide suggestion list
                        suggestionList.setVisible(false);
                        scrollPane.setVisible(false);
                    }
                }
            }
        });

        // Confirm button setup
        confirmButton = new JButton("Confirm");
        confirmButton.setFont(new Font("Arial", Font.BOLD, 16));
        confirmButton.setBackground(new Color(70, 130, 180));
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setFocusPainted(false);
        confirmButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        confirmButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        confirmButton.addActionListener(e -> {
            int selectedIndex = suggestionList.getSelectedIndex();
            if (selectedIndex != -1) {
                // Get selected name, latitude, and longitude
                String selectedName = suggestionList.getSelectedValue();
                double selectedLatitude = latitudes.get(selectedIndex);
                double selectedLongitude = longitudes.get(selectedIndex);

                // Print the selected values to the console
                setVisible(false);
                new Home();

            } else {
                // Handle the case where no location is selected
                System.out.println("No location selected.");
                JOptionPane.showMessageDialog(this,
                        "No location selected. Please choose a location from the list.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        contentPanel.add(confirmButton, BorderLayout.SOUTH);
    }

    private void updateSuggestions() {
        String query = searchInput.getText().trim();
        listModel.clear();
        longitudes.clear(); // Clear previous suggestions
        latitudes.clear();

        if (!query.isEmpty() && !query.equals("Search For Location")) {
            JSONArray locationData = WeatherApi.getLocationData(query); // Call your existing method
            if (locationData != null) {
                for (Object obj : locationData) {
                    JSONObject location = (JSONObject) obj;
                    String name = (String) location.get("name");
                    String country = (String) location.get("country");
                    double lat = (double) location.get("latitude");
                    double lon = (double) location.get("longitude");

                    // Add name and country to the list model
                    listModel.addElement(name + ", " + country);

                    // Store latitude and longitude in lists
                    latitudes.add(lat);
                    longitudes.add(lon);
                }
            }
        }

        // Show or hide the suggestion list based on whether there are suggestions
        boolean hasSuggestions = listModel.size() > 0;
        suggestionList.setVisible(hasSuggestions);
        JScrollPane scrollPane = (JScrollPane) suggestionList.getParent().getParent();
        scrollPane.setVisible(hasSuggestions);

        // Trigger UI updates
        suggestionList.revalidate();
        suggestionList.repaint();
        scrollPane.revalidate();
        scrollPane.repaint();

        // Trigger a layout update for the frame
        SwingUtilities.invokeLater(() -> {
            revalidate();
            repaint();
        });
    }



}