package fil.l3.coo.vehicule;

/**
 * Component interface for the Decorator pattern.
 * Allows adding accessories to any vehicle.
 */
public interface VehiculeComponent {
    
    /**
     * Gets the total price including all decorations.
     * 
     * @return the price in euros
     */
    double getPrice();
    
    /**
     * Gets the full description including all decorations.
     * 
     * @return the description string
     */
    String getDescription();
    
    /**
     * Checks if the vehicle is available for rental.
     * 
     * @return true if available, false otherwise
     */
    boolean isAvailable();
    
    /**
     * Sets the availability status of the vehicle.
     * 
     * @param available the new availability status
     */
    void setAvailable(boolean available);
    
    /**
     * Gets the type of the vehicle.
     * 
     * @return the type string
     */
    String getType();
}
