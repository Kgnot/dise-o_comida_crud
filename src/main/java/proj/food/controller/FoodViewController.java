package proj.food.controller;

import proj.food.config.app_context.ApplicationContext;
import proj.food.services.food.FoodService;
import proj.food.vista.interfaces.FoodView;

public class FoodViewController {

    private final FoodView foodView;
    private final FoodService foodService;

    public FoodViewController(FoodView foodView) {
        this.foodView = foodView;
        this.foodService = ApplicationContext.getInstance().getBean(FoodService.class);
    }

    public void processMenuOption(String option) {
        switch (option) {
            case "1":
                System.out.println("Showing food list... total: " + foodService.findAll().size());
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
