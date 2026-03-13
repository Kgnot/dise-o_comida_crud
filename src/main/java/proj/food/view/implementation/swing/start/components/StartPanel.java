package proj.food.view.implementation.swing.start.components;

import javax.swing.*;
import java.awt.*;

public class StartPanel extends JPanel {

    public StartPanel() {
        buildUI();
    }

    private void buildUI() {

        setLayout(new BorderLayout());

        JLabel title = new JLabel("Plataforma de Gestión de Comida", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 22f));

        JLabel subtitle = new JLabel(
                "Sistema de gestión de clientes y alimentos",
                SwingConstants.CENTER
        );

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        center.add(Box.createVerticalGlue());
        center.add(title);
        center.add(Box.createRigidArea(new Dimension(0,10)));
        center.add(subtitle);
        center.add(Box.createVerticalGlue());

        add(center, BorderLayout.CENTER);
    }
}