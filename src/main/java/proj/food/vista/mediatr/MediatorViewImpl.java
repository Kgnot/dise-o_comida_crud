package proj.food.vista.mediatr;

import proj.food.vista.ViewType;
import proj.food.vista.interfaces.ViewApplication;

import java.util.HashMap;
import java.util.Map;

public class MediatorViewImpl implements MediatorView {

    private Map<ViewType, ViewApplication> views = new HashMap<>();

    private ViewApplication currentView;

    @Override
    public void addView(ViewType type, ViewApplication view) {
        views.put(type, view);
        view.setMediator(this);
    }

    @Override
    public void changeView(ViewType type) {

        ViewApplication next = views.get(type);

        if (next == null) {
            throw new IllegalStateException("View not registered: " + type);
        }

//        if (currentView != null) {
//            currentView.exit();
//        }

        currentView = next;
        currentView.showMenu();
    }

    @Override
    public void exitApplication() {
        System.exit(0);
    }
}