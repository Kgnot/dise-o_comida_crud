package proj.food.config.database.factory;

import jakarta.persistence.EntityManagerFactory;
import proj.food.config.database.connect.Connect;

public abstract class ConnectFactory {
    protected EntityManagerFactory emf;

    public abstract Connect getConnectInstance();

}