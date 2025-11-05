package fil.l3.coo.rental.exceptions;

/**
 * Exception thrown when a vehicle is not available for rental.
 */
public class VehiculeNotAvailableException extends RentalException {
    
    public VehiculeNotAvailableException(String message) {
        super(message);
    }
}
