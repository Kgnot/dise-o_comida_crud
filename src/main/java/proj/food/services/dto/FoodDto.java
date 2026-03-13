package proj.food.services.dto;

// Data Transfer Object (DTO) for Food entity, used to transfer food data between layers of the application
public record FoodDto(Long id, String name, Double price) {
}

