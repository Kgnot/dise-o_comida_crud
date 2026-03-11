package proj.food.config;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class AppProperties {

    private static final String FILE_NAME = "application.yml";
    private static final Map<String, String> PROPERTIES = load();

    private AppProperties() {
    }

    public static String get(String key) {
        String envValue = System.getenv(key);
        if (envValue != null && !envValue.isBlank()) {
            return envValue;
        }

        String value = PROPERTIES.get(key);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("No existe la propiedad requerida: " + key);
        }

        return value;
    }

    // Para propiedades opcionales como contraseñas vacías (ej: H2)
    public static String getOptional(String key) {
        String envValue = System.getenv(key);
        if (envValue != null) {
            return envValue;
        }
        return PROPERTIES.getOrDefault(key, "");
    }

    private static Map<String, String> load() {
        InputStream inputStream = AppProperties.class.getClassLoader().getResourceAsStream(FILE_NAME);
        if (inputStream == null) {
            throw new IllegalStateException("No se encontro el archivo " + FILE_NAME + " en resources");
        }

        Yaml yaml = new Yaml();
        Object rawYaml = yaml.load(inputStream);
        if (!(rawYaml instanceof Map<?, ?> rootMap)) {
            throw new IllegalStateException("El archivo " + FILE_NAME + " no contiene un objeto YAML valido");
        }

        Map<String, String> flattened = new HashMap<>();
        flatten(rootMap, "", flattened);
        return flattened;
    }

    @SuppressWarnings("unchecked")
    private static void flatten(Map<?, ?> source, String prefix, Map<String, String> target) {
        for (Map.Entry<?, ?> entry : source.entrySet()) {
            String key = Objects.toString(entry.getKey(), "").trim();
            if (key.isEmpty()) {
                continue;
            }

            String currentKey = prefix.isEmpty() ? key : prefix + "." + key;
            Object value = entry.getValue();

            if (value instanceof Map<?, ?> nestedMap) {
                flatten((Map<?, ?>) nestedMap, currentKey, target);
                continue;
            }

            if (value != null) {
                target.put(currentKey, value.toString());
                target.put(currentKey.toUpperCase().replace('.', '_'), value.toString());
            }
        }
    }
}

