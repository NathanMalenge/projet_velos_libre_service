package fil.l3.coo.rental.exceptions;

/**
 * Base exception for rental operations.
 */
public class RentalException extends Exception {
    
    public RentalException(String message) {
        super(message);
    }
    
    public RentalException(String message, Throwable cause) {
        super(message, cause);
    }
}
