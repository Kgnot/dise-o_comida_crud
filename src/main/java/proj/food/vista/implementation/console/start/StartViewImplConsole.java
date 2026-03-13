package proj.food.vista.implementation.console.start;

import proj.food.controller.StartViewController;
import proj.food.vista.ViewType;
import proj.food.vista.interfaces.StartView;
import proj.food.vista.mediatr.MediatorView;

import java.util.Scanner;

public class StartViewImplConsole implements StartView {

    private StartViewController controller;
    private MediatorView mediator;
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void showMenu() {

        System.out.println("¡Bienvenidos a la plataforma de comida!");
        System.out.println("1. Ir a Vista de Comida");
        System.out.println("2. Ir a Vista de Clientes");
        System.out.println("3. Salir");
        System.out.print("Elija una opción: ");

        String option = scanner.nextLine();

        getController().processMenuOption(option);
    }

    @Override
    public void exit() {
        System.exit(0);
    }

    private StartViewController getController() {
        if (controller == null) {
            controller = new StartViewController(this);
        }
        return controller;
    }

    @Override
    public void goToFoodView() {
        mediator.changeView(ViewType.FOOD);
    }

    @Override
    public void goToCustomerView() {
        mediator.changeView(ViewType.CUSTOMER);
    }

    @Override
    public void setMediator(MediatorView mv) {
        this.mediator = mv;
    }
}