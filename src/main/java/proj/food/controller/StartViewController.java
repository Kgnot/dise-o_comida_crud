package proj.food.controller;

import proj.food.vista.interfaces.StartView;

public class StartViewController {

    private final StartView view;

    public StartViewController(StartView view) {
        this.view = view;
    }

    public void processMenuOption(String option) {
        switch (option) {
            case "1":
                view.goToFoodView();
                break;
            case "2":
                view.goToCustomerView();
                break;
            case "3":
                System.out.println("Exiting application...");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid option. Please try again.");
                view.showMenu();
        }
    }
}
