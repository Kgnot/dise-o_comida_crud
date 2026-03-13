package proj.food.services.food;

import proj.food.services.dto.FoodDto;

import java.util.List;
// Service interface for managing food-related operations, providing methods to find, save, and delete food items
public interface FoodService {

    List<FoodDto> findAll();

    FoodDto findById(Long id);

    void save(FoodDto foodDto);

    void deleteById(Long id);
}
