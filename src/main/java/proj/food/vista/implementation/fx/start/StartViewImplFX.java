package proj.food.vista.implementation.fx.start;

import proj.food.controller.StartViewController;
import proj.food.vista.ViewType;
import proj.food.vista.implementation.fx.start.components.StartPane;
import proj.food.vista.interfaces.StartView;
import proj.food.vista.mediatr.MediatorView;

public class StartViewImplFX implements StartView {

    private final StartPane pane;
    private StartViewController controller;
    private MediatorView mediator;

    public StartViewImplFX() {
        this.pane = new StartPane();
    }

    private StartViewController getController() {
        if (controller == null) {
            controller = new StartViewController(this);
        }
        return controller;
    }

    public StartPane getPane() {
        return pane;
    }

    @Override
    public void showMenu() {
        pane.setVisible(true);
    }

    @Override
    public void exit() {
        if (mediator != null) {
            mediator.exitApplication();
            return;
        }
        System.exit(0);
    }

    @Override
    public void goToFoodView() {
        if (mediator != null) {
            mediator.changeView(ViewType.FOOD);
        }
    }

    @Override
    public void goToCustomerView() {
        if (mediator != null) {
            mediator.changeView(ViewType.CUSTOMER);
        }
    }

    @Override
    public void setMediator(MediatorView mv) {
        this.mediator = mv;
        // Start pane acts as landing page; navigation remains in shell/sidebar.
        getController();
    }
}
