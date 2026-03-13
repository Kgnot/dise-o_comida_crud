package proj.food.vista.shell;

import proj.food.vista.ViewType;

// Interface for the main application shell, responsible for managing the display of different views based on user interactions and application state
public interface Shell {
    void showView(ViewType type);
    void show(ViewType initial);
}
