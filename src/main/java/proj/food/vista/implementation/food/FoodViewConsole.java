package proj.food.vista.implementation.food;

import proj.food.controller.FoodViewController;
import proj.food.vista.interfaces.FoodView;
import proj.food.vista.mediatr.MediatorView;

import java.util.Scanner;

public class FoodViewConsole implements FoodView {

    private final Scanner scanner = new Scanner(System.in);
    private MediatorView mediator;
    private FoodViewController controller;

    @Override
    public void showMenu() {
        System.out.println("\n=== Food MENU ===");
        System.out.println("1. Show Customer List");
        System.out.println("2. Exit");
        System.out.print("Choose an option: ");

        String option = scanner.nextLine();
        getController().processMenuOption(option);
    }

    private FoodViewController getController() {
        if (controller == null) {
            controller = new FoodViewController(this);
        }
        return controller;
    }


    @Override
    public void exit() {

    }

    @Override
    public void setMediator(MediatorView mediator) {
        this.mediator = mediator;
    }
}
