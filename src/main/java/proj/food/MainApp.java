package proj.food;

import com.formdev.flatlaf.intellijthemes.FlatLightFlatIJTheme;
import proj.food.config.app_context.ApplicationContext;
import proj.food.vista.factory.UIFactory;
import proj.food.vista.factory.UIType;

public class MainApp {

    public static void main(String[] args) {
        ApplicationContext.getInstance();
        FlatLightFlatIJTheme.setup();
        UIFactory.launch(UIType.FX);
    }
}
