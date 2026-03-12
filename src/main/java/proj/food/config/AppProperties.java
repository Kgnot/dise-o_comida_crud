package proj.food.config;

import java.io.InputStream;
import java.util.Properties;

public final class AppProperties {

    private static final String FILE_NAME = "application.properties";
    private static final Properties PROPERTIES = load();

    public static String get(String key) {

        String envValue = System.getenv(key);
        if (envValue != null && !envValue.isBlank()) {
            return envValue;
        }

        String value = PROPERTIES.getProperty(key);

        if (value == null || value.isBlank()) {
            throw new IllegalStateException("No existe la propiedad requerida: " + key);
        }

        return value;
    }

    public static String getOptional(String key) {

        String envValue = System.getenv(key);
        if (envValue != null) {
            return envValue;
        }

        return PROPERTIES.getProperty(key, "");
    }

    private static Properties load() {

        InputStream inputStream =
                AppProperties.class.getClassLoader().getResourceAsStream(FILE_NAME);

        if (inputStream == null) {
            throw new IllegalStateException(
                    "No se encontro el archivo " + FILE_NAME + " en resources");
        }

        try {
            Properties properties = new Properties();
            properties.load(inputStream);
            return properties;

        } catch (Exception e) {
            throw new IllegalStateException("Error cargando propiedades", e);
        }
    }
}