package fil.l3.coo.station.exceptions;

/**
 * Exception thrown when trying to operate on a null bike.
 */
public class NullBikeException extends IllegalArgumentException {
    
    /**
     * Creates a new NullBikeException with a default message.
     */
    public NullBikeException() {
        super("Le vélo ne peut pas être null");
    }
    
    /**
     * Creates a new NullBikeException with a custom message.
     * 
     * @param message the detail message
     */
    public NullBikeException(String message) {
        super(message);
    }
}
