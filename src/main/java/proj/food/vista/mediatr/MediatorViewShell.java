package proj.food.vista.mediatr;

import proj.food.vista.ViewType;
import proj.food.vista.interfaces.ViewApplication;
import proj.food.vista.shell.Shell;

import java.util.HashMap;
import java.util.Map;
// To manage the views in the application using a mediator pattern, allowing for decoupling between the views and the shell
public class MediatorViewShell implements MediatorView {

    private final Map<ViewType, ViewApplication> views = new HashMap<>();
    private final Shell shell;

    public MediatorViewShell(Shell shell) {
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