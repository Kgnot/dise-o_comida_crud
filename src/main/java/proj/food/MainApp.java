package proj.food;

import com.formdev.flatlaf.intellijthemes.FlatLightFlatIJTheme;
import proj.food.config.app_context.ApplicationContext;
import proj.food.view.factory.UIFactory;
import proj.food.view.factory.UIType;

public class MainApp {

    public static void main(String[] args) {
        ApplicationContext.getInstance();
        FlatLightFlatIJTheme.setup();
        UIFactory.launch(UIType.SWING);
    }
}
