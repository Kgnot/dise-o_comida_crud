package proj.food.services.mapper;

import proj.food.entity.FoodEntity;
import proj.food.services.dto.FoodDto;

import java.util.List;

public final class FoodMapper {

    private FoodMapper() {
    }

    public static FoodDto toDto(FoodEntity entity) {
        if (entity == null) {
            return null;
        }
        return new FoodDto(entity.getId(), entity.getName(), entity.getPrice());
    }

    public static FoodEntity toEntity(FoodDto dto) {
        if (dto == null) {
            return null;
        }
        return new FoodEntity(dto.id(), dto.name(), dto.price());
    }

    public static List<FoodDto> toDtoList(List<FoodEntity> entities) {
        return entities.stream().map(FoodMapper::toDto).toList();
    }
}

