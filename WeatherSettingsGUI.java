import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WeatherSettingsGUI extends JFrame {

    private JComboBox<String> languageComboBox;
    private JComboBox<String> modeComboBox;
    private JCheckBox severeWeatherCheckBox;
    private JCheckBox dailyWeatherCheckBox;
    private JRadioButton celsiusRadioButton;
    private JRadioButton fahrenheitRadioButton;
    private JLabel languageLabel, modeLabel, temperatureLabel;
    private JButton saveButton;

    // Các văn bản cho tiếng Việt và tiếng Anh
    private final String[][] languageTexts = {
            {"Ngôn ngữ", "Chế độ giao diện", "Nhận thông báo thời tiết xấu", "Nhận thông báo thời tiết hằng ngày", "Chọn đơn vị nhiệt độ", "Lưu cài đặt"},
            {"Language", "Mode", "Receive severe weather alerts", "Receive daily weather alerts", "Select temperature unit", "Save settings"}
    };

    private final String[][] modeOptions = {
            {"Trắng", "Đen", "Mặc định"},  // Cho tiếng Việt
            {"Light", "Dark", "Default"}   // Cho tiếng Anh
    };

    private int currentLanguage = 0; // 0: Tiếng Việt, 1: English

    public WeatherSettingsGUI() {
        setTitle("Weather App Settings");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Đặt khung hiển thị toàn màn hình
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Panel chính để chứa các thành phần
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); // Căn từ trên xuống dưới

        // Font chữ lớn hơn
        Font largeFont = new Font("Arial", Font.PLAIN, 24);

        // 1. Tùy chọn ngôn ngữ
        JPanel languagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Căn trái
        languagePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Tạo khoảng cách thay vì border
        languageLabel = new JLabel();
        languageLabel.setFont(largeFont);  // Đặt font chữ lớn cho nhãn
        languageComboBox = new JComboBox<>(new String[]{"Tiếng Việt", "English"});
        languageComboBox.setFont(largeFont); // Đặt font chữ lớn cho combo box
        languageComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateLanguage();
            }
        });
        languagePanel.add(languageLabel);
        languagePanel.add(languageComboBox);
        mainPanel.add(languagePanel);

        // 2. Chọn mode cho ứng dụng
        JPanel modePanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Căn trái
        modePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Tạo khoảng cách thay vì border
        modeLabel = new JLabel();
        modeLabel.setFont(largeFont);  // Đặt font chữ lớn cho nhãn
        modeComboBox = new JComboBox<>(modeOptions[currentLanguage]); // Hiển thị tùy chọn mode tương ứng với ngôn ngữ
        modeComboBox.setFont(largeFont); // Đặt font chữ lớn cho combo box
        modeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateMode(); // Thay đổi chế độ hiển thị khi chọn mode
            }
        });
        modePanel.add(modeLabel);
        modePanel.add(modeComboBox);
        mainPanel.add(modePanel);

        // 3. Chọn xem có nhận thông báo khi nào có thời tiết xấu
        JPanel severeWeatherPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Căn trái
        severeWeatherPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Tạo khoảng cách thay vì border
        severeWeatherCheckBox = new JCheckBox();
        severeWeatherCheckBox.setFont(largeFont); // Đặt font chữ lớn cho checkbox
        severeWeatherPanel.add(severeWeatherCheckBox);
        mainPanel.add(severeWeatherPanel);

        // 4. Chọn xem có nhận thông báo thời tiết hằng ngày
        JPanel dailyWeatherPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Căn trái
        dailyWeatherPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Tạo khoảng cách thay vì border
        dailyWeatherCheckBox = new JCheckBox();
        dailyWeatherCheckBox.setFont(largeFont); // Đặt font chữ lớn cho checkbox
        dailyWeatherPanel.add(dailyWeatherCheckBox);
        mainPanel.add(dailyWeatherPanel);

        // 5. Chọn chế độ nhiệt độ hiển thị
        JPanel temperaturePanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Căn trái
        temperaturePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Tạo khoảng cách thay vì border
        temperatureLabel = new JLabel();
        temperatureLabel.setFont(largeFont);  // Đặt font chữ lớn cho nhãn
        celsiusRadioButton = new JRadioButton("Celsius", true);
        celsiusRadioButton.setFont(largeFont);  // Đặt font chữ lớn cho radio button
        fahrenheitRadioButton = new JRadioButton("Fahrenheit");
        fahrenheitRadioButton.setFont(largeFont);  // Đặt font chữ lớn cho radio button
        ButtonGroup tempGroup = new ButtonGroup();
        tempGroup.add(celsiusRadioButton);
        tempGroup.add(fahrenheitRadioButton);
        temperaturePanel.add(temperatureLabel);
        temperaturePanel.add(celsiusRadioButton);
        temperaturePanel.add(fahrenheitRadioButton);
        mainPanel.add(temperaturePanel);

        // Nút lưu cài đặt
        JPanel savePanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Căn trái
        savePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Tạo khoảng cách thay vì border
        saveButton = new JButton();
        saveButton.setFont(largeFont);  // Đặt font chữ lớn cho nút lưu
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveSettings();
            }
        });
        savePanel.add(saveButton);
        mainPanel.add(savePanel);

        add(mainPanel);
        updateLanguage(); // Thiết lập ngôn ngữ ban đầu (Tiếng Việt)
    }

    // Phương thức để thay đổi ngôn ngữ của giao diện
    private void updateLanguage() {
        currentLanguage = languageComboBox.getSelectedIndex(); // 0: Tiếng Việt, 1: English
        languageLabel.setText(languageTexts[currentLanguage][0]);
        modeLabel.setText(languageTexts[currentLanguage][1]);
        severeWeatherCheckBox.setText(languageTexts[currentLanguage][2]);
        dailyWeatherCheckBox.setText(languageTexts[currentLanguage][3]);
        temperatureLabel.setText(languageTexts[currentLanguage][4]);
        saveButton.setText(languageTexts[currentLanguage][5]);

        // Cập nhật các lựa chọn cho comboBox mode
        modeComboBox.setModel(new DefaultComboBoxModel<>(modeOptions[currentLanguage]));
    }

    // Phương thức thay đổi giao diện theo chế độ được chọn
    private void updateMode() {
        String selectedMode = (String) modeComboBox.getSelectedItem();
        if (currentLanguage == 0) { // Tiếng Việt
            if (selectedMode.equals("Trắng")) {
                getContentPane().setBackground(Color.WHITE);
                setTextColor(Color.BLACK);
            } else if (selectedMode.equals("Đen") || selectedMode.equals("Mặc định")) {
                getContentPane().setBackground(Color.BLACK);
                setTextColor(Color.WHITE);
            }
        } else { // Tiếng Anh
            if (selectedMode.equals("Light")) {
                getContentPane().setBackground(Color.WHITE);
                setTextColor(Color.BLACK);
            } else if (selectedMode.equals("Dark") || selectedMode.equals("Default")) {
                getContentPane().setBackground(Color.BLACK);
                setTextColor(Color.WHITE);
            }
        }
    }

    // Phương thức để thay đổi màu chữ của tất cả các thành phần
    private void setTextColor(Color color) {
        languageLabel.setForeground(color);
        modeLabel.setForeground(color);
        severeWeatherCheckBox.setForeground(color);
        dailyWeatherCheckBox.setForeground(color);
        temperatureLabel.setForeground(color);
        celsiusRadioButton.setForeground(color);
        fahrenheitRadioButton.setForeground(color);
        saveButton.setForeground(color);
    }

    private void saveSettings() {
        String language = (String) languageComboBox.getSelectedItem();
        String mode = (String) modeComboBox.getSelectedItem();
        boolean severeWeatherAlert = severeWeatherCheckBox.isSelected();
        boolean dailyWeatherAlert = dailyWeatherCheckBox.isSelected();
        String temperatureUnit = celsiusRadioButton.isSelected() ? "Celsius" : "Fahrenheit";

        // Hiển thị cài đặt đã lưu
        JOptionPane.showMessageDialog(this,
                (currentLanguage == 0 ? "Cài đặt đã lưu:" : "Settings saved:") + "\n" +
                        (currentLanguage == 0 ? "Ngôn ngữ: " : "Language: ") + language + "\n" +
                        (currentLanguage == 0 ? "Chế độ giao diện: " : "Mode: ") + mode + "\n" +
                        (currentLanguage == 0 ? "Nhận thông báo thời tiết xấu: " : "Receive severe weather alerts: ") + (severeWeatherAlert ? (currentLanguage == 0 ? "Có" : "Yes") : (currentLanguage == 0 ? "Không" : "No")) + "\n" +
                        (currentLanguage == 0 ? "Nhận thông báo thời tiết hằng ngày: " : "Receive daily weather alerts: ") + (dailyWeatherAlert ? (currentLanguage == 0 ? "Có" : "Yes") : (currentLanguage == 0 ? "Không" : "No")) + "\n" +
                        (currentLanguage == 0 ? "Đơn vị nhiệt độ: " : "Temperature unit: ") + temperatureUnit);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new WeatherSettingsGUI().setVisible(true);
            }
        });
    }
}
