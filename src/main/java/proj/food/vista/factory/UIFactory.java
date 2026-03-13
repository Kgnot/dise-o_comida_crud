package proj.food.vista.factory;

import proj.food.vista.ViewType;
import proj.food.vista.implementation.console.customer.CustomerViewConsole;
import proj.food.vista.implementation.console.food.FoodViewConsole;
import proj.food.vista.implementation.console.start.StartViewImplConsole;
import proj.food.vista.implementation.fx.FxRuntime;
import proj.food.vista.implementation.fx.MainShellFX;
import proj.food.vista.implementation.swing.MainShellSwing;
import proj.food.vista.mediatr.MediatorViewImpl;

import javax.swing.*;

// Factory class to launch the application in different UI modes
public class UIFactory {
    // Factory method to launch the application in the specified UI mode
    public static void launch(UIType mode) {
        switch (mode) {
            case CONSOLE -> launchConsole();
            case SWING -> launchSwing();
            case FX -> launchFX();
            default -> throw new IllegalArgumentException("Unknown mode: " + mode);
        }
    }

    // Method to launch the application in console mode, setting up the mediator and views for console interaction
    private static void launchConsole() {
        MediatorViewImpl mediator = new MediatorViewImpl();
        mediator.addView(ViewType.START, new StartViewImplConsole());
        mediator.addView(ViewType.FOOD, new FoodViewConsole());
        mediator.addView(ViewType.CUSTOMER, new CustomerViewConsole());
        mediator.changeView(ViewType.START);
    }

    private static void launchSwing() {
        SwingUtilities.invokeLater(() -> MainShellSwing.create().show(ViewType.START));
    }

    private static void launchFX() {
        FxRuntime.runOnFxThread(() -> MainShellFX.create().show(ViewType.START));
    }


}
