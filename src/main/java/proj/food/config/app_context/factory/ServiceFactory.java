package proj.food.config.app_context.factory;

import proj.food.config.AppProperties;
import proj.food.repository.customer.CustomerRepository;
import proj.food.repository.food.FoodRepository;
import proj.food.services.customer.CustomerService;
import proj.food.services.customer.CustomerServiceImpl;
import proj.food.services.food.FoodService;
import proj.food.services.food.FoodServiceImpl;

// Factory class to create instances of services based on configuration properties
public final class ServiceFactory {

    private ServiceFactory() {
    }

    public static CustomerService createCustomerService(CustomerRepository customerRepository) {
        String impl = AppProperties.getOptional("APP_CUSTOMER_SERVICE_IMPL");
        String selected = impl == null || impl.isBlank() ? "default" : impl.trim().toLowerCase();
        // just there is only one implementation of CustomerService, so we can use "default" as the default value
        return switch (selected) {
            case "default" -> new CustomerServiceImpl(customerRepository);
            default -> throw new IllegalStateException("Implementacion de CustomerService no soportada: " + selected);
        };
    }

    public static FoodService createFoodService(FoodRepository foodRepository) {
        String impl = AppProperties.getOptional("APP_FOOD_SERVICE_IMPL");
        String selected = impl == null || impl.isBlank() ? "default" : impl.trim().toLowerCase();

        return switch (selected) {
            case "default" -> new FoodServiceImpl(foodRepository);
            default -> throw new IllegalStateException("Implementacion de FoodService no soportada: " + selected);
        };
    }
}

