package fil.l3.coo.station.exceptions;

/**
 * Exception thrown when a vehicle is not found in a station.
 */
public class VehiculeNotFoundException extends Exception {
    
    /**
     * Creates a new VehiculeNotFoundException with a default message.
     */
    public VehiculeNotFoundException() {
        super("Le véhicule demandé n'existe pas dans cette station");
    }
    
    /**
     * Creates a new VehiculeNotFoundException with a custom message.
     * 
     * @param message the detail message
     */
    public VehiculeNotFoundException(String message) {
        super(message);
    }
}
