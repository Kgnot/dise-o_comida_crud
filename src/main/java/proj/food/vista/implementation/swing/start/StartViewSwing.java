package proj.food.vista.implementation.swing.start;

import proj.food.controller.StartViewController;
import proj.food.vista.ViewType;
import proj.food.vista.implementation.swing.start.components.StartPanel;
import proj.food.vista.interfaces.StartView;
import proj.food.vista.mediatr.MediatorView;

public class StartViewSwing implements StartView {

    private final StartPanel panel;
    private MediatorView mediator;
    private StartViewController controller;

    public StartViewSwing() {
        this.panel = new StartPanel();
    }

    private StartViewController getController() {
        if (controller == null) {
            controller = new StartViewController(this);
        }
        return controller;
    }

    public StartPanel getPanel() {
        return panel;
    }

    @Override
    public void showMenu() {
        panel.setVisible(true);
    }

    @Override
    public void exit() {
        System.exit(0);
    }

    @Override
    public void goToFoodView() {
        mediator.changeView(ViewType.FOOD);
    }

    @Override
    public void goToCustomerView() {
        mediator.changeView(ViewType.CUSTOMER);
    }

    @Override
    public void setMediator(MediatorView mv) {
        this.mediator = mv;
    }
}