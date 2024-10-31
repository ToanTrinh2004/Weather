import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class RoundedTextField extends JTextField {

    private final int cornerRadius;
    private final String placeholder;
    private boolean showingPlaceholder;

    public RoundedTextField(int radius, String placeholder) {
        this.cornerRadius = radius;
        this.placeholder = placeholder;
        this.showingPlaceholder = true; // Initially show the placeholder
        setText(placeholder); // Set placeholder text
        setForeground(Color.GRAY); // Set placeholder text color
        setOpaque(false);

        // Add FocusListener to handle placeholder logic
        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (showingPlaceholder) {
                    setText(""); // Remove placeholder when focused
                    setForeground(Color.BLACK); // Set regular text color
                    showingPlaceholder = false;
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getText().isEmpty()) {
                    setText(placeholder); // Restore placeholder when unfocused and no input
                    setForeground(Color.GRAY); // Set placeholder text color
                    showingPlaceholder = true;
                }
            }
        });

        // Add KeyListener to clear placeholder when the user starts typing
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (showingPlaceholder) {
                    setText(""); // Clear the placeholder text as soon as typing starts
                    setForeground(Color.BLACK); // Set regular text color
                    showingPlaceholder = false;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (getText().isEmpty() && !isFocusOwner()) {
                    // If no text and the field isn't focused, show the placeholder again
                    setText(placeholder);
                    setForeground(Color.GRAY);
                    showingPlaceholder = true;
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        // No border
    }

    @Override
    public Insets getInsets() {
        return new Insets(5, 15, 5, 15);
    }
}
