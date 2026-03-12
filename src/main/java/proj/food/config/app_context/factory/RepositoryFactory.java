package proj.food.config.app_context.factory;

import jakarta.persistence.EntityManager;
import proj.food.config.AppProperties;
import proj.food.repository.customer.CustomerRepository;
import proj.food.repository.customer.CustomerRepositoryImpl;
import proj.food.repository.food.FoodRepository;
import proj.food.repository.food.FoodRepositoryImpl;

public final class RepositoryFactory {

    private RepositoryFactory() {
    }

    public static CustomerRepository createCustomerRepository(EntityManager entityManager) {
        String impl = AppProperties.getOptional("APP_CUSTOMER_REPOSITORY_IMPL");
        String selected = impl == null || impl.isBlank() ? "jpa" : impl.trim().toLowerCase();

        return switch (selected) {
            case "jpa" -> new CustomerRepositoryImpl(entityManager);
            default -> throw new IllegalStateException("Implementacion de CustomerRepository no soportada: " + selected);
        };
    }

    public static FoodRepository createFoodRepository(EntityManager entityManager) {
        String impl = AppProperties.getOptional("APP_FOOD_REPOSITORY_IMPL");
        String selected = impl == null || impl.isBlank() ? "jpa" : impl.trim().toLowerCase();

        return switch (selected) {
            case "jpa" -> new FoodRepositoryImpl(entityManager);
            default -> throw new IllegalStateException("Implementacion de FoodRepository no soportada: " + selected);
        };
    }
}

