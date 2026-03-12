package proj.food.services.mapper;

import proj.food.entity.CustomerEntity;
import proj.food.services.dto.CustomerDto;

import java.util.List;

public final class CustomerMapper {

    private CustomerMapper() {
    }

    public static CustomerDto toDto(CustomerEntity entity) {
        if (entity == null) {
            return null;
        }
        return new CustomerDto(entity.getId(), entity.getName());
    }

    public static CustomerEntity toEntity(CustomerDto dto) {
        if (dto == null) {
            return null;
        }
        return new CustomerEntity(dto.id(), dto.name());
    }

    public static List<CustomerDto> toDtoList(List<CustomerEntity> entities) {
        return entities.stream().map(CustomerMapper::toDto).toList();
    }
}

