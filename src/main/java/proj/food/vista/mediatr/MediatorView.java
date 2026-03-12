package proj.food.vista.mediatr;

import proj.food.vista.ViewType;
import proj.food.vista.interfaces.ViewApplication;

public interface MediatorView {

    void changeView(ViewType view);

    void addView(ViewType view, ViewApplication viewApplication);

    void exitApplication();
}