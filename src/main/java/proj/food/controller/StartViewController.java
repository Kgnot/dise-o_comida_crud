package proj.food.controller;

import proj.food.vista.interfaces.StartView;

public class StartViewController {

    private final StartView view;

    public StartViewController(StartView view) {
        this.view = view;
    }

    public void start() {
        view.show();
    }
}
