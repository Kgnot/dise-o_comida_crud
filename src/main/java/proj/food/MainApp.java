package proj.food;

import proj.food.config.app_context.ApplicationContext;
import proj.food.vista.ViewType;
import proj.food.vista.implementation.customer.CustomerViewFX;
import proj.food.vista.implementation.food.FoodViewFX;
import proj.food.vista.implementation.start.StartViewImplConsole;
import proj.food.vista.implementation.start.StartViewImplFX;
import proj.food.vista.mediatr.MediatorViewImpl;

public class MainApp {

    public static void main(String[] args) {
        ApplicationContext.getInstance();

        MediatorViewImpl mediator = new MediatorViewImpl();
        mediator.addView(ViewType.START, new StartViewImplConsole());
        mediator.addView(ViewType.FOOD, new FoodViewFX());
        mediator.addView(ViewType.CUSTOMER, new CustomerViewFX());
        mediator.changeView(ViewType.START);
    }

}
