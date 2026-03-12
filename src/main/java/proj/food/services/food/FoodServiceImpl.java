package proj.food.services.food;

import proj.food.entity.FoodEntity;
import proj.food.repository.food.FoodRepository;

import java.util.List;

public class FoodServiceImpl implements FoodService {


    private final FoodRepository foodRepository;

    public FoodServiceImpl(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    @Override
    public List<FoodEntity> findAll() {
        return foodRepository.findAll();
    }

    @Override
    public FoodEntity findById(Long id) {
        return foodRepository.findById(id);
    }

    @Override
    public void save(FoodEntity foodEntity) {
        foodRepository.save(foodEntity);
    }

    @Override
    public void deleteById(Long id) {
        foodRepository.deleteById(id);
    }
}
