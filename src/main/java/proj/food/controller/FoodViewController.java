package proj.food.controller;

import proj.food.vista.interfaces.FoodView;

public class FoodViewController {

    private final FoodView foodView;


    public FoodViewController(FoodView foodView) {
        this.foodView = foodView;
    }

    public void processMenuOption(String option) {
        switch (option) {
            case "1":
                System.out.println("Showing customer list...");
                break;
            case "2":
                System.out.println("Exiting Food View...");
                break;
            default:
                System.out.println("Invalid option");
                foodView.showMenu();
        }
    }
}
