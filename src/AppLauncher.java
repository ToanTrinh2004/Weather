import javax.swing.*;

public class AppLauncher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SetUpFrame frame = new SetUpFrame();
            frame.setVisible(true);
        });
    }
}