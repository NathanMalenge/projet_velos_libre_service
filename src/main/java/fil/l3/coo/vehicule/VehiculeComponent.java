package fil.l3.coo.vehicule;

import fil.l3.coo.vehicule.state.VehiculeState;

/**
 * Component interface for the Decorator pattern.
 * Allows adding accessories to any vehicle.
 * Also provides state management for vehicle lifecycle.
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
    
    /**
     * Gets the current state of this vehicle.
     * 
     * @return the vehicle state
     */
    VehiculeState getState();
    
    /**
     * Sets the state of this vehicle.
     * 
     * @param state the new state
     */
    void setState(VehiculeState state);
    
    /**
     * Gets the state name of this vehicle.
     * 
     * @return the state name
     */
    String getStateName();
    
    /**
     * Gets the number of times this vehicle has been rented.
     * 
     * @return the rental count
     */
    int getRentalCount();
    
    /**
     * Increments the rental counter.
     */
    void incrementRentalCount();
    
    /**
     * Resets the rental counter to zero.
     */
    void resetRentalCount();
    
    /**
     * Checks if this vehicle needs maintenance.
     * 
     * @return true if maintenance is needed, false otherwise
     */
    boolean needsMaintenance();
    
    /**
     * Gets the number of time intervals this vehicle has been idle.
     * 
     * @return the idle time in intervals
     */
    int getIdleTimeIntervals();
    
    /**
     * Increments the idle time counter.
     */
    void incrementIdleTime();
    
    /**
     * Resets the idle time counter to zero.
     */
    void resetIdleTime();
    
    /**
     * Checks if this vehicle is at risk of being stolen.
     * 
     * @return true if at risk, false otherwise
     */
    boolean isAtRiskOfTheft();
}
