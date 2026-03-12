package proj.food.repository.food;

import proj.food.entity.FoodEntity;

import java.util.List;

public interface FoodRepository {

    List<FoodEntity> findAll();

    FoodEntity findById(Long id);

    void save(FoodEntity food);

    void deleteById(Long id);


}
