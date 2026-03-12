package proj.food.services.food;

import proj.food.entity.FoodEntity;

import java.util.List;

public interface FoodService {

    List<FoodEntity> findAll();

    FoodEntity findById(Long id);

    void save(FoodEntity foodEntity);

    void deleteById(Long id);


}
