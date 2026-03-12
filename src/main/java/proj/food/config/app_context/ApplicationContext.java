package proj.food.config.app_context;

import jakarta.persistence.EntityManager;
import proj.food.config.AppProperties;
import proj.food.config.app_context.factory.RepositoryFactory;
import proj.food.config.app_context.factory.ServiceFactory;
import proj.food.config.database.connect.Connect;
import proj.food.config.database.factory.ConnectFactory;
import proj.food.config.database.factory.LocalDatabaseConnectionFactory;
import proj.food.config.database.factory.PostgresDatabaseConnectionFactory;
import proj.food.repository.customer.CustomerRepository;
import proj.food.repository.food.FoodRepository;
import proj.food.services.customer.CustomerService;
import proj.food.services.food.FoodService;

import java.util.HashMap;
import java.util.Map;

public final class ApplicationContext {

    private static final ApplicationContext INSTANCE = new ApplicationContext();

    private final Map<Class<?>, Object> beans = new HashMap<>();

    private ApplicationContext() {
        initialize();
    }

    public static ApplicationContext getInstance() {
        return INSTANCE;
    }

    public <T> T getBean(Class<T> type) {
        Object bean = beans.get(type);
        if (bean == null) {
            throw new IllegalStateException("Bean no registrado para el tipo: " + type.getName());
        }
        return type.cast(bean);
    }

    private <T> void registerBean(Class<T> type, T instance) {
        beans.put(type, instance);
    }

    private void initialize() {
        ConnectFactory connectFactory = createConnectFactory();
        Connect connect = connectFactory.getConnectInstance();
        EntityManager entityManager = connect.getEntityManagerFactory();

        registerBean(ConnectFactory.class, connectFactory);
        registerBean(Connect.class, connect);
        registerBean(EntityManager.class, entityManager);

        CustomerRepository customerRepository = RepositoryFactory.createCustomerRepository(entityManager);
        FoodRepository foodRepository = RepositoryFactory.createFoodRepository(entityManager);
        registerBean(CustomerRepository.class, customerRepository);
        registerBean(FoodRepository.class, foodRepository);

        CustomerService customerService = ServiceFactory.createCustomerService(customerRepository);
        FoodService foodService = ServiceFactory.createFoodService(foodRepository);
        registerBean(CustomerService.class, customerService);
        registerBean(FoodService.class, foodService);
    }

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
