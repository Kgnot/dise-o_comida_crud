package proj.food.config.app_context.factory;

import proj.food.config.AppProperties;
import proj.food.config.app_context.database.factory.ConnectFactory;
import proj.food.config.app_context.database.factory.LocalDatabaseConnectionFactory;
import proj.food.config.app_context.database.factory.PostgresDatabaseConnectionFactory;

public class BeanConnectFactory {


    // Select database provider from properties with a local fallback.
    public static ConnectFactory createConnectFactory() {
        String provider = AppProperties.getOptional("APP_DATABASE_PROVIDER");
        String selected = provider == null || provider.isBlank() ? "local" : provider.trim().toLowerCase();

        return switch (selected) {
            case "postgres" -> new PostgresDatabaseConnectionFactory();
            case "local" -> new LocalDatabaseConnectionFactory();
            default -> throw new IllegalStateException("Proveedor de base de datos no soportado: " + selected);
        };
    }
}
