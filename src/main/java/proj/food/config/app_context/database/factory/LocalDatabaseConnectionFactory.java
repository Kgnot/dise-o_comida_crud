package proj.food.config.app_context.database.factory;

import proj.food.config.AppProperties;
import proj.food.config.app_context.database.connect.Connect;
import proj.food.config.app_context.database.connect.ConnectDatabase;

import java.util.logging.Logger;

public class LocalDatabaseConnectionFactory extends ConnectFactory {
    private static final Logger logger = Logger.getLogger(LocalDatabaseConnectionFactory.class.getName());

    private final Connect instance;

    public LocalDatabaseConnectionFactory() {
        Connect db = new ConnectDatabase(
                AppProperties.get("LOCAL_DATABASE_URL"),
                AppProperties.get("LOCAL_DATABASE_USER"),
                AppProperties.getOptional("LOCAL_DATABASE_PASSWORD")
        );
        db.addOverride("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        db.addOverride("hibernate.hbm2ddl.auto", "create-drop");
        db.establishConnection();
        this.instance = db;
        logger.info("Conexión establecida con base de datos local (H2)");
    }

    @Override
    public Connect getConnectInstance() {
        return instance;
    }
}

