package proj.food.view.implementation.fx.start;

import proj.food.view.implementation.fx.start.components.StartPane;
import proj.food.view.interfaces.StartView;

public class StartViewFX implements StartView {

    private final StartPane pane;

    public StartViewFX() {
        this.pane = new StartPane();
    }

    public StartPane getPane() {
        return pane;
    }

    @Override
    public void show() {
        pane.setVisible(true);
    }

    @Override
    public void exit() {
        System.exit(0);
    }

}
