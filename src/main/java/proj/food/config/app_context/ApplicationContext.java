package proj.food.config.app_context;

import jakarta.persistence.EntityManager;
import proj.food.config.AppProperties;
import proj.food.config.app_context.factory.BeanConnectFactory;
import proj.food.config.app_context.factory.BeanRepositoryFactory;
import proj.food.config.app_context.factory.BeanServiceFactory;
import proj.food.config.app_context.database.connect.Connect;
import proj.food.config.app_context.database.factory.ConnectFactory;
import proj.food.config.app_context.database.factory.LocalDatabaseConnectionFactory;
import proj.food.config.app_context.database.factory.PostgresDatabaseConnectionFactory;
import proj.food.repository.customer.CustomerRepository;
import proj.food.repository.food.FoodRepository;
import proj.food.services.customer.CustomerService;
import proj.food.services.food.FoodService;

import java.util.HashMap;
import java.util.Map;

// Application-wide context responsible for wiring and exposing shared dependencies.
public final class ApplicationContext {
    // Singleton instance used by controllers and factories.
    private static final ApplicationContext INSTANCE = new ApplicationContext();
    // In-memory registry of app beans.
    private final Map<Class<?>, Object> beans = new HashMap<>();

    private ApplicationContext() {
        initialize();
    }

    public static ApplicationContext getInstance() {
        return INSTANCE;
    }
    // Resolve a bean by type from the local registry.
    public <T> T getBean(Class<T> type) {
        Object bean = beans.get(type);
        if (bean == null) {
            throw new IllegalStateException("Bean no registrado para el tipo: " + type.getName());
        }
        return type.cast(bean);
    }
    // Register a bean instance under its class key.
    private <T> void registerBean(Class<T> type, T instance) {
        beans.put(type, instance);
    }
    // Build and register repositories/services once during startup.
    private void initialize() {
        ConnectFactory connectFactory = BeanConnectFactory.createConnectFactory();
        Connect connect = connectFactory.getConnectInstance();
        // create the entity manager
        EntityManager entityManager = connect.getEntityManagerFactory();
        // registir the beans in the context, maybe isnt necessary
        registerBean(ConnectFactory.class, connectFactory);
        registerBean(Connect.class, connect);
        registerBean(EntityManager.class, entityManager);
        // this is create the repositories and services based on the factory and register them in the context
        CustomerRepository customerRepository = BeanRepositoryFactory.createCustomerRepository(entityManager);
        FoodRepository foodRepository = BeanRepositoryFactory.createFoodRepository(entityManager);
        registerBean(CustomerRepository.class, customerRepository);
        registerBean(FoodRepository.class, foodRepository);

        CustomerService customerService = BeanServiceFactory.createCustomerService(customerRepository);
        FoodService foodService = BeanServiceFactory.createFoodService(foodRepository);
        registerBean(CustomerService.class, customerService);
        registerBean(FoodService.class, foodService);
    }

}
