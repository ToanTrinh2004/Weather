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

public class SearchField extends JPanel {
    private JList<String> suggestionList;
    private DefaultListModel<String> listModel;
    private RoundedTextField searchInput;
    public ArrayList<Double> longitudes; // Store longitudes
    public ArrayList<Double> latitudes;  // Store latitudes
    private final Home home;
    public SearchField(Home home) {
        this.home = home;
        setLayout(null); // Allow absolute positioning
        setPreferredSize(new Dimension(250, 150)); // Adjust based on needed size
        setOpaque(false); // Make the panel transparent

        // Create a padding panel
        JPanel paddingPanel = new JPanel();
        paddingPanel.setLayout(null);
        paddingPanel.setBounds(5, 5, 240, 140); // Padding of 5px
        paddingPanel.setOpaque(false); // Make the padding panel transparent

        searchInput = new RoundedTextField(15, "Search For Location"); // 15 is the corner radius
        searchInput.setPreferredSize(new Dimension(250, 40));
        searchInput.setBounds(0, 0, 240, 40); // Set position and size
        searchInput.setBackground(Color.white);
        searchInput.setBorder(null);

        paddingPanel.add(searchInput);

        // Initialize suggestionList and ArrayLists
        listModel = new DefaultListModel<>();
        suggestionList = new JList<>(listModel);
        suggestionList.setVisible(false);
        suggestionList.setBorder(new EmptyBorder(5, 5, 5, 5));

        longitudes = new ArrayList<>();
        latitudes = new ArrayList<>();

        suggestionList.setBounds(0, 45, 240, 100); // Adjusted to fit padding
        suggestionList.setPreferredSize(new Dimension(240, 100));
        suggestionList.setBackground(Color.WHITE);
        suggestionList.setSelectionBackground(new Color(220, 220, 220));

        paddingPanel.add(suggestionList);
        add(paddingPanel); // Add the padding panel to the main panel

        // Add DocumentListener to search field
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

        // Handle selection from the suggestion list
        suggestionList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedIndex = suggestionList.getSelectedIndex();
                    if (selectedIndex != -1) {
                        String selectedValue = suggestionList.getSelectedValue();
                        searchInput.setText(selectedValue); // Set selected value in search input
                        listModel.clear(); // Clear suggestions
                        suggestionList.setVisible(false); // Hide suggestion list

                        // Retrieve latitude and longitude using selectedIndex
                        double selectedLongitude = longitudes.get(selectedIndex);
                        double selectedLatitude = latitudes.get(selectedIndex);

                        // Pass latitude and longitude to getWeatherData
                        WeatherApi.getWeatherData(selectedLatitude, selectedLongitude);
                        WeatherData data = new WeatherData();
                        data.setLat(selectedLatitude);
                        data.setLon(selectedLongitude);
                        data.setName(selectedValue);
                        Home.locationList.add(data);
                        home.updateWeatherPanelFromLatest();

                    }
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Optional: You can add custom painting here if needed
    }

    private void updateSuggestions() {
        String query = searchInput.getText();
        listModel.clear();
        longitudes.clear(); // Clear old data
        latitudes.clear();

        if (query.length() > 0) {
            JSONArray locationData = WeatherApi.getLocationData(query); // Call your existing method
            if (locationData != null) {
                for (Object obj : locationData) {
                    JSONObject location = (JSONObject) obj;
                    String name = (String) location.get("name");
                    String country = (String) location.get("country");
                    double lat = (double) location.get("latitude");
                    double lon = (double) location.get("longitude");

                    // Add name and country to listModel
                    listModel.addElement(name + ", " + country);

                    // Store latitude and longitude in ArrayLists
                    latitudes.add(lat);
                    longitudes.add(lon);
                }
            }
        }

        // Show or hide the suggestion list based on whether there are suggestions
        suggestionList.setVisible(listModel.size() > 0);
        suggestionList.revalidate();
        suggestionList.repaint();
    }
}