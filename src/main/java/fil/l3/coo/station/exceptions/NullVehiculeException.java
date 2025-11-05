package fil.l3.coo.station.exceptions;

/**
 * Exception thrown when trying to operate on a null vehicle.
 */
public class NullVehiculeException extends IllegalArgumentException {
    
    /**
     * Creates a new NullVehiculeException with a default message.
     */
    public NullVehiculeException() {
        super("Le véhicule ne peut pas être null");
    }
    
    /**
     * Creates a new NullVehiculeException with a custom message.
     * 
     * @param message the detail message
     */
    public NullVehiculeException(String message) {
        super(message);
    }
}
