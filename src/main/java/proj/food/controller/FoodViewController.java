package proj.food.controller;

import proj.food.config.app_context.ApplicationContext;
import proj.food.services.dto.FoodDto;
import proj.food.services.food.FoodService;
import proj.food.vista.interfaces.FoodView;

import java.util.List;

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
                List<FoodDto> foods = foodService.findAll();
                System.out.println("Showing food list... total: " + foods.size());
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
