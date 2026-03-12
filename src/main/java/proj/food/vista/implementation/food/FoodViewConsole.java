package proj.food.vista.implementation.food;

import proj.food.vista.interfaces.FoodView;
import proj.food.vista.mediatr.MediatorView;

public class FoodViewConsole implements FoodView {

    private MediatorView mediator;

    @Override
    public void showMenu() {

    }

    @Override
    public void exit() {

    }

    @Override
    public void setMediator(MediatorView mediator) {
        this.mediator = mediator;
    }
}
