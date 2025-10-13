package fil.l3.coo.station.exceptions;

/**
 * Exception thrown when a bike is not found in a station.
 */
public class BikeNotFoundException extends Exception {
    
    /**
     * Creates a new BikeNotFoundException with a default message.
     */
    public BikeNotFoundException() {
        super("Le vélo demandé n'existe pas dans cette station");
    }
    
    /**
     * Creates a new BikeNotFoundException with a custom message.
     * 
     * @param message the detail message
     */
    public BikeNotFoundException(String message) {
        super(message);
    }
}
