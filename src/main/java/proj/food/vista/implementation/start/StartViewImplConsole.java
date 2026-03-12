package proj.food.vista.implementation.start;

import proj.food.controller.CustomerViewController;
import proj.food.controller.StartViewController;
import proj.food.vista.interfaces.StartView;

import java.util.Scanner;

public class StartViewImplConsole implements StartView {

    private StartViewController controller;
    private Scanner scanner = new Scanner(System.in);

    @Override
    public void showMenu() {
        System.out.println("Bienvenidos a la plataforma de comida!");
        System.out.println("1. Ir a Food View");
        System.out.println("2. Ir a Customer View");
        System.out.println("3. Exit");
        String option = scanner.nextLine();
        controller.processMenuOption(option);
    }

    private StartViewController getController() {
        if (controller == null) {
            controller = new StartViewController(this);
        }
        return controller;
    }



    @Override
    public void goToFoodView() {

    }

    @Override
    public void goToCustomerView() {

    }
}
