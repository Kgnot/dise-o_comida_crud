package proj.food.vista.implementation.swing.shared;

import javax.swing.*;
import java.awt.*;

public class StatusBar extends JPanel {

    private final JLabel label;

    public StatusBar() {
        super(new BorderLayout());
        this.label = new JLabel(" ");
        this.label.setBorder(BorderFactory.createEmptyBorder(0, 12, 8, 12));
        add(label, BorderLayout.WEST);
    }

    public void setText(String message) {
        label.setText(message);
    }

    public void clear() {
        label.setText(" ");
    }
}
