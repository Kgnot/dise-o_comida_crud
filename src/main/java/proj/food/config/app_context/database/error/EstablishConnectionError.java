package proj.food.config.app_context.database.error;

// Custom exception to indicate errors during database connection establishment
public class EstablishConnectionError extends RuntimeException {
    public EstablishConnectionError(String message) {
        super(message);
    }
}