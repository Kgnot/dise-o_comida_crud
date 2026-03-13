package proj.food;

import com.formdev.flatlaf.intellijthemes.FlatLightFlatIJTheme;
import proj.food.config.app_context.ApplicationContext;
import proj.food.vista.ViewType;
import proj.food.vista.implementation.fx.FxRuntime;
import proj.food.vista.implementation.fx.MainShellFX;
import proj.food.vista.implementation.swing.MainShell;

import javax.swing.*;

public class MainApp {

    public static void main(String[] args) {
        ApplicationContext.getInstance();
        FxRuntime.runOnFxThread(() -> new MainShellFX().show(ViewType.START));
        FlatLightFlatIJTheme.setup();
        SwingUtilities.invokeLater(() -> new MainShell().show(ViewType.START));

    }

}
