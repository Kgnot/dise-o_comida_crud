package proj.food.vista.mediatr;

import proj.food.vista.ViewType;
import proj.food.vista.implementation.swing.MainShell;
import proj.food.vista.interfaces.ViewApplication;

import java.util.HashMap;
import java.util.Map;

public class MediatorViewSwing implements MediatorView {

    private final Map<ViewType, ViewApplication> views = new HashMap<>();
    private final MainShell shell;

    public MediatorViewSwing(MainShell shell) {
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
        shell.showView(type);
    }

    @Override
    public void exitApplication() {
        System.exit(0);
    }
}