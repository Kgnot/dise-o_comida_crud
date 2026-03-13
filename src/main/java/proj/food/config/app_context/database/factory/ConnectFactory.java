package proj.food.config.app_context.database.factory;

import proj.food.config.app_context.database.connect.Connect;

// Method factory class to create instances of Connect for database connections
public abstract class ConnectFactory {

    public abstract Connect getConnectInstance();

}