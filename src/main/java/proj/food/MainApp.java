package proj.food;

import proj.food.config.app_context.ApplicationContext;
import proj.food.vista.ViewType;
import proj.food.vista.implementation.fx.FxRuntime;
import proj.food.vista.implementation.fx.MainShellFX;

public class MainApp {

    public static void main(String[] args) {
        ApplicationContext.getInstance();
        FxRuntime.runOnFxThread(() -> new MainShellFX().show(ViewType.START));
    }
}
