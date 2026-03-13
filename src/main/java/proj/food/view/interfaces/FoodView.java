package proj.food.view.interfaces;

import proj.food.services.dto.FoodDto;

import java.util.List;

public interface FoodView extends ViewApplication {

    void showFoodList(List<FoodDto> entities);

    void insertFood();

    void updateFood();

    void deleteFood();

    void showError(String message);
}
