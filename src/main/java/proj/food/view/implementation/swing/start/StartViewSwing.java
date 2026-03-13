package proj.food.view.implementation.swing.start;

import proj.food.view.implementation.swing.start.components.StartPanel;
import proj.food.view.interfaces.StartView;

public class StartViewSwing implements StartView {

    private final StartPanel panel;

    public StartViewSwing() {
        this.panel = new StartPanel();
    }

    public StartPanel getPanel() {
        return panel;
    }

    @Override
    public void show() {
        panel.setVisible(true);
    }

    @Override
    public void exit() {
        System.exit(0);
    }

}