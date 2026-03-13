package proj.food.controller;

import proj.food.config.app_context.ApplicationContext;
import proj.food.services.dto.FoodDto;
import proj.food.services.food.FoodService;
import proj.food.view.interfaces.FoodView;

import java.util.List;

public class FoodViewController {

    private final FoodView foodView;
    private final FoodService foodService;

    public FoodViewController(FoodView foodView) {
        this.foodView = foodView;
        this.foodService = ApplicationContext.getInstance().getBean(FoodService.class);
    }

    public void processMenuOption(String option) {
        // String options keep compatibility across console, Swing, and FX views.
        switch (option) {
            case "1":
                showFoodList();
                break;
            case "2":
                foodView.insertFood();
                break;
            case "3":
                foodView.updateFood();
                break;
            case "4":
                foodView.deleteFood();
                break;
            case "5":
                exit();
                break;
            default:
                foodView.showError("Invalid option");
                foodView.show();
        }
    }

    public void showFoodList() {
        List<FoodDto> foods = foodService.findAll();
        foodView.showFoodList(foods);
    }
    // To insert a new food
    public void insertFood(FoodDto newFood) {
        try {
            foodService.save(newFood);
            showFoodList();
        } catch (Exception e) {
            foodView.showError("Error inserting food: " + e.getMessage());
        }
    }
    // The update and delete methods receive the FoodDto to be updated/deleted, which can be obtained from the view's table selection.

    public void updateFood(FoodDto updatedFood) {
        try {
            foodService.save(updatedFood);
            showFoodList();
        } catch (Exception e) {
            foodView.showError("Error updating food: " + e.getMessage());
        }
    }

    public void deleteFood(FoodDto deletedFood) {
        try {
            foodService.deleteById(deletedFood.id());
            showFoodList();
        } catch (Exception e) {
            foodView.showError("Error deleting food: " + e.getMessage());
        }
    }

    private void exit() {
        foodView.exit();
    }
}
