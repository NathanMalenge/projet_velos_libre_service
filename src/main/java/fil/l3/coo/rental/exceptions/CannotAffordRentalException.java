package fil.l3.coo.rental.exceptions;

/**
 * Exception thrown when a user cannot afford a rental.
 */
public class CannotAffordRentalException extends RentalException {
    
    public CannotAffordRentalException(String message) {
        super(message);
    }
}
