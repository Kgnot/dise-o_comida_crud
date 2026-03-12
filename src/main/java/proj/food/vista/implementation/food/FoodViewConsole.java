package proj.food.vista.implementation.food;

import proj.food.controller.FoodViewController;
import proj.food.vista.interfaces.FoodView;
import proj.food.vista.mediatr.MediatorView;

import java.util.Scanner;

public class FoodViewConsole implements FoodView {

    private final Scanner scanner = new Scanner(System.in);
    private FoodViewController controller;
    private MediatorView mediator;

    private FoodViewController getController() {
        if (controller == null) {
            controller = new FoodViewController(this);
        }
        return controller;
    }

    @Override
    public void showMenu() {
        System.out.println("\n=== FOOD MENU ===");
        System.out.println("1. Show Food List");
        System.out.println("2. Exit");
        System.out.print("Choose an option: ");

        String option = scanner.nextLine();
        getController().processMenuOption(option);
    }

    @Override
    public void exit() {
        System.out.println("Exiting Food View...");
    }

    @Override
    public void setMediator(MediatorView mediator) {
        this.mediator = mediator;
    }
}
