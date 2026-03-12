package proj.food;

import proj.food.config.database.factory.ConnectFactory;
import proj.food.config.database.factory.LocalDatabaseConnectionFactory;
import proj.food.vista.ViewType;
import proj.food.vista.implementation.customer.CustomerViewConsole;
import proj.food.vista.implementation.customer.CustomerViewFX;
import proj.food.vista.implementation.customer.CustomerViewSwing;
import proj.food.vista.implementation.food.FoodViewConsole;
import proj.food.vista.implementation.start.StartViewImplConsole;
import proj.food.vista.implementation.start.StartViewImplFX;
import proj.food.vista.implementation.start.StartViewImplSwing;
import proj.food.vista.mediatr.MediatorViewImpl;

public class MainApp {

    public static void main(String[] args) {
        ConnectFactory connectFactory = new LocalDatabaseConnectionFactory();
        var connectInstance = connectFactory.getConnectInstance();

        MediatorViewImpl mediator = new MediatorViewImpl();
        // vistas:
        mediator.addView(ViewType.START, new StartViewImplFX());
        mediator.addView(ViewType.FOOD, new FoodViewConsole());
        mediator.addView(ViewType.CUSTOMER, new CustomerViewFX());
        //
        mediator.changeView(ViewType.START);
    }

}




