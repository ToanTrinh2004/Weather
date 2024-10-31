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
    private ArrayList<Double> longitudes; // Store longitudes
    private ArrayList<Double> latitudes;  // Store latitudes

    public SearchField() {
        setLayout(null); // Allow absolute positioning
        setPreferredSize(new Dimension(250, 150)); // Adjust based on needed size

        searchInput = new RoundedTextField(15, "Search For Location"); // 15 is the corner radius
        searchInput.setPreferredSize(new Dimension(250, 40));
        searchInput.setBounds(0, 5, 250, 40); // Set position and size
        searchInput.setBackground(Color.white);
        searchInput.setBorder(null);

        add(searchInput);

        // Initialize suggestionList and ArrayLists
        listModel = new DefaultListModel<>();
        suggestionList = new JList<>(listModel);
        suggestionList.setVisible(false);
        suggestionList.setBorder(new EmptyBorder(5, 5, 5, 5));

        longitudes = new ArrayList<>();
        latitudes = new ArrayList<>();

        suggestionList.setBounds(0, 45, 250, 100);
        suggestionList.setPreferredSize(new Dimension(250, 100));
        suggestionList.setBackground(Color.WHITE);
        suggestionList.setSelectionBackground(new Color(220, 220, 220));

        add(suggestionList);

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
                        WeatherApi.getWeatherData(selectedLatitude,selectedLongitude);
                    }
                }
            }
        });
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
