package fil.l3.coo.vehicule;

/**
 * Abstract class representing a vehicle for urban transportation.
 * This can be extended for bikes, scooters, or other modes of transport.
 */
public abstract class Vehicule {
    
    private boolean available;
    
    /**
     * Creates a new vehicle.
     * By default, the vehicle is available.
     */
    public Vehicule() {
        this.available = true;
    }
    
    /**
     * Gets the rental price for this vehicle.
     * 
     * @return the price in euros
     */
    public abstract double getPrice();
    
    /**
     * Gets the description of this vehicle.
     * 
     * @return a description string
     */
    public abstract String getDescription();
    
    /**
     * Gets the type of this vehicle.
     * 
     * @return the vehicle type (e.g., "VeloClassique", "Trottinette")
     */
    public abstract String getType();
    
    /**
     * Checks if this vehicle is available for rental.
     * 
     * @return true if available, false otherwise
     */
    public boolean isAvailable() {
        return available;
    }
    
    /**
     * Sets the availability status of this vehicle.
     * 
     * @param available the new availability status
     */
    public void setAvailable(boolean available) {
        this.available = available;
    }
}
