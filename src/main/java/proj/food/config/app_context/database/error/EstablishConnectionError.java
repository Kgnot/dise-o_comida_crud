package proj.food.config.app_context.database.error;

public class EstablishConnectionError extends RuntimeException {
    public EstablishConnectionError(String message) {
        super(message);
    }
}