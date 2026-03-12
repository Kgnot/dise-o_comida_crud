package proj.food.services.food;

import proj.food.repository.food.FoodRepository;
import proj.food.services.dto.FoodDto;
import proj.food.services.mapper.FoodMapper;

import java.util.List;

public class FoodServiceImpl implements FoodService {

    private final FoodRepository foodRepository;

    public FoodServiceImpl(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    @Override
    public List<FoodDto> findAll() {
        return FoodMapper.toDtoList(foodRepository.findAll());
    }

    @Override
    public FoodDto findById(Long id) {
        return FoodMapper.toDto(foodRepository.findById(id));
    }

    @Override
    public void save(FoodDto foodDto) {
        foodRepository.save(FoodMapper.toEntity(foodDto));
    }

    @Override
    public void deleteById(Long id) {
        foodRepository.deleteById(id);
    }
}
