package proj.food.view.factory;

import proj.food.view.ViewType;
import proj.food.view.implementation.console.MainShellConsole;
import proj.food.view.implementation.fx.FxRuntime;
import proj.food.view.implementation.fx.MainShellFX;
import proj.food.view.implementation.swing.MainShellSwing;

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
        MainShellConsole.create().launch(ViewType.START);
    }

    private static void launchSwing() {
        SwingUtilities.invokeLater(() -> MainShellSwing.create().launch(ViewType.START));
    }

    private static void launchFX() {
        FxRuntime.runOnFxThread(() -> MainShellFX.create().launch(ViewType.START));
    }


}
