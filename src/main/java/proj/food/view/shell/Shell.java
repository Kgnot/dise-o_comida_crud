package proj.food.view.shell;

import proj.food.view.ViewType;

// Interface who define navigate and launch
public interface Shell {
    void navigate(ViewType type);
    void launch(ViewType initial);
}
