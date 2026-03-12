package proj.food.vista.interfaces;

import proj.food.vista.mediatr.MediatorView;

public interface ViewApplication {

    void showMenu();

    void exit();

    void setMediator(MediatorView mediator);

}