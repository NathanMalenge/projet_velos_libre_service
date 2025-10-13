package fil.l3.coo.station.exceptions;

/**
 * Exception thrown when trying to park a bike in a full station.
 */
public class StationFullException extends Exception {
    
    /**
     * Creates a new StationFullException with a default message.
     */
    public StationFullException() {
        super("La station est pleine, impossible de garer le vélo");
    }
    
    /**
     * Creates a new StationFullException with a custom message.
     * 
     * @param message the detail message
     */
    public StationFullException(String message) {
        super(message);
    }
}
