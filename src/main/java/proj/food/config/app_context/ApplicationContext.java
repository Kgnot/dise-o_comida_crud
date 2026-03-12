package proj.food.config.app_context;

import jakarta.persistence.EntityManager;
import proj.food.config.AppProperties;
import proj.food.config.app_context.factory.RepositoryFactory;
import proj.food.config.app_context.factory.ServiceFactory;
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

// Context application for managing dependencies and configurations in the food ordering system
public final class ApplicationContext {
    // make it a singleton to ensure only one instance manages the application context
    private static final ApplicationContext INSTANCE = new ApplicationContext();
    // necessary to store beans
    private final Map<Class<?>, Object> beans = new HashMap<>();

    private ApplicationContext() {
        initialize();
    }

    public static ApplicationContext getInstance() {
        return INSTANCE;
    }
    // to obtain a bean of specified class type.
    public <T> T getBean(Class<T> type) {
        Object bean = beans.get(type);
        if (bean == null) {
            throw new IllegalStateException("Bean no registrado para el tipo: " + type.getName());
        }
        return type.cast(bean);
    }
    // to register an bean in the map
    private <T> void registerBean(Class<T> type, T instance) {
        beans.put(type, instance);
    }
    // here we initialize all necesary componentes
    private void initialize() {
        ConnectFactory connectFactory = createConnectFactory();
        Connect connect = connectFactory.getConnectInstance();
        // create the entity manager
        EntityManager entityManager = connect.getEntityManagerFactory();
        // registir the beans in the context, maybe isnt necessary
        registerBean(ConnectFactory.class, connectFactory);
        registerBean(Connect.class, connect);
        registerBean(EntityManager.class, entityManager);
        // this is create the repositories and services based on the factory and register them in the context
        CustomerRepository customerRepository = RepositoryFactory.createCustomerRepository(entityManager);
        FoodRepository foodRepository = RepositoryFactory.createFoodRepository(entityManager);
        registerBean(CustomerRepository.class, customerRepository);
        registerBean(FoodRepository.class, foodRepository);

        CustomerService customerService = ServiceFactory.createCustomerService(customerRepository);
        FoodService foodService = ServiceFactory.createFoodService(foodRepository);
        registerBean(CustomerService.class, customerService);
        registerBean(FoodService.class, foodService);
    }
    // this is to create the connection facotry based on config properties
    private ConnectFactory createConnectFactory() {
        String provider = AppProperties.getOptional("APP_DATABASE_PROVIDER");
        String selected = provider == null || provider.isBlank() ? "local" : provider.trim().toLowerCase();

        return switch (selected) {
            case "postgres" -> new PostgresDatabaseConnectionFactory();
            case "local" -> new LocalDatabaseConnectionFactory();
            default -> throw new IllegalStateException("Proveedor de base de datos no soportado: " + selected);
        };
    }
}
