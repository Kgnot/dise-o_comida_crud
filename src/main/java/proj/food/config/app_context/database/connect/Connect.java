package proj.food.config.app_context.database.connect;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import proj.food.config.app_context.database.error.EstablishConnectionError;

import java.util.HashMap;
import java.util.Map;

public abstract class Connect {

    protected Map<String, Object> overrides = new HashMap<>();
    protected EntityManagerFactory entityManagerFactory;
    private static final String DB_NAME = "CRUDfood"; // Nombre generico que podriamos buscar por propiedades
    private final String URL, USER, PASSWORD;

    public Connect(String url, String user, String password) {
        this.URL = url;
        this.USER = user;
        this.PASSWORD = password;
    }

    public void addOverride(String key, Object value) {
        overrides.put(key, value);
    }

    public void establishConnection() {
        if (URL == null || USER == null || PASSWORD == null) {
            throw new EstablishConnectionError("Connection failed: Missing database configuration parameters (URL, USER, PASSWORD)");
        }

        overrides.put("jakarta.persistence.jdbc.url", URL);
        overrides.put("jakarta.persistence.jdbc.user", USER);
        overrides.put("jakarta.persistence.jdbc.password", PASSWORD);
        entityManagerFactory = Persistence.createEntityManagerFactory(DB_NAME, overrides);
    }

    public EntityManager getEntityManagerFactory() {
        return entityManagerFactory.createEntityManager();
    }
}



