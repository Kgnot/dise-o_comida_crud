package proj.food.config.app_context.database.factory;

import proj.food.config.AppProperties;
import proj.food.config.app_context.database.connect.Connect;
import proj.food.config.app_context.database.connect.ConnectDatabase;

import java.util.logging.Logger;

public class PostgresDatabaseConnectionFactory extends ConnectFactory {
    private static final Logger logger = Logger.getLogger(PostgresDatabaseConnectionFactory.class.getName());

    private final Connect instance;

    public PostgresDatabaseConnectionFactory() {
        Connect db = new ConnectDatabase(
                AppProperties.get("DATABASE_URL"),
                AppProperties.get("DATABASE_USER"),
                AppProperties.get("DATABASE_PASSWORD")
        );
        db.addOverride("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        db.addOverride("hibernate.hbm2ddl.auto", "validate");
        db.establishConnection();
        this.instance = db;
        logger.info("Connection established with PostgreSQL database");
    }

    @Override
    public Connect getConnectInstance() {
        return instance;
    }
}