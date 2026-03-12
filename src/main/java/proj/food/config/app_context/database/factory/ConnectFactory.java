package proj.food.config.app_context.database.factory;

import proj.food.config.app_context.database.connect.Connect;

public abstract class ConnectFactory {

    public abstract Connect getConnectInstance();

}