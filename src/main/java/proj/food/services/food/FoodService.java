package proj.food.services.food;

import proj.food.services.dto.FoodDto;

import java.util.List;

public interface FoodService {

    List<FoodDto> findAll();

    FoodDto findById(Long id);

    void save(FoodDto foodDto);

    void deleteById(Long id);
}
