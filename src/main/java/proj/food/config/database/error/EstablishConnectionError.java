package proj.food.config.database.error;

public class EstablishConnectionError extends RuntimeException {
    public EstablishConnectionError(String message) {
        super(message);
    }
}