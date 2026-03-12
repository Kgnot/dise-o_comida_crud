package proj.food;

import com.formdev.flatlaf.intellijthemes.FlatLightFlatIJTheme;
import proj.food.config.app_context.ApplicationContext;
import proj.food.vista.ViewType;
import proj.food.vista.implementation.swing.MainShell;

import javax.swing.*;

public class MainApp {

    public static void main(String[] args) {
        ApplicationContext.getInstance();

//        MediatorViewImpl mediator = new MediatorViewImpl();
//        mediator.addView(ViewType.START, new StartViewSwing());
//        mediator.addView(ViewType.FOOD, new FoodViewSwing());
//        mediator.addView(ViewType.CUSTOMER, new CustomerViewSwing());
//        mediator.changeView(ViewType.START);
        FlatLightFlatIJTheme.setup();

        SwingUtilities.invokeLater(() -> new MainShell().show(ViewType.START));

    }

}
