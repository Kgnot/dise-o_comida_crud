package proj.food.vista.mediatr;

import proj.food.vista.ViewType;
import proj.food.vista.implementation.fx.FxRuntime;
import proj.food.vista.implementation.fx.MainShellFX;
import proj.food.vista.interfaces.ViewApplication;

import java.util.HashMap;
import java.util.Map;

public class MediatorViewFX implements MediatorView {

    private final Map<ViewType, ViewApplication> views = new HashMap<>();
    private final MainShellFX shell;

    public MediatorViewFX(MainShellFX shell) {
        this.shell = shell;
    }

    @Override
    public void addView(ViewType type, ViewApplication view) {
        views.put(type, view);
        view.setMediator(this);
    }

    @Override
    public void changeView(ViewType type) {
        if (!views.containsKey(type)) {
            throw new IllegalStateException("View not registered: " + type);
        }
        // Always switch views on the JavaFX Application Thread.
        FxRuntime.runOnFxThread(() -> shell.showView(type));
    }

    @Override
    public void exitApplication() {
        // Exit from the FX thread to keep UI shutdown consistent.
        FxRuntime.runOnFxThread(() -> System.exit(0));
    }
}
