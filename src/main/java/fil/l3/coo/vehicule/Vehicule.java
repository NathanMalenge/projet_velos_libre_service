package fil.l3.coo.vehicule;

import fil.l3.coo.vehicule.state.VehiculeState;
import fil.l3.coo.vehicule.state.DisponibleState;

/**
 * Abstract class representing a vehicle for urban transportation.
 * This can be extended for bikes, scooters, or other modes of transport.
 * Implements the State pattern for managing vehicle lifecycle.
 */
public abstract class Vehicule {
    
    private boolean available;
    private VehiculeState state;
    private int rentalCount;
    private int idleTimeIntervals;
    private boolean inMaintenanceSinceOneTick;
    private static final int MAX_RENTALS_BEFORE_MAINTENANCE = 10;
    private static final int MAX_IDLE_TIME_BEFORE_THEFT = 2;
    
    /**
     * Creates a new vehicle.
     * By default, the vehicle is available and in DISPONIBLE state.
     */
    public Vehicule() {
        this.available = true;
        this.state = new DisponibleState();
        this.rentalCount = 0;
        this.idleTimeIntervals = 0;
        this.inMaintenanceSinceOneTick = false;
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
        
    /**
     * Gets the current state of this vehicle.
     * 
     * @return the vehicle state
     */
    public VehiculeState getState() {
        return state;
    }
    
    /**
     * Sets the state of this vehicle.
     * 
     * @param state the new state
     */
    public void setState(VehiculeState state) {
        this.state = state;
    }
    
    /**
     * Gets the state name of this vehicle.
     * 
     * @return the state name
     */
    public String getStateName() {
        return state.getStateName();
    }
    
    
    /**
     * Gets the number of times this vehicle has been rented.
     * 
     * @return the rental count
     */
    public int getRentalCount() {
        return rentalCount;
    }
    
    /**
     * Increments the rental counter.
     * Called each time the vehicle is rented.
     */
    public void incrementRentalCount() {
        this.rentalCount++;
    }
    
    /**
     * Resets the rental counter to zero.
     * Called after maintenance is completed.
     */
    public void resetRentalCount() {
        this.rentalCount = 0;
    }
    
    /**
     * Checks if this vehicle needs maintenance.
     * 
     * @return true if rental count exceeds threshold, false otherwise
     */
    public boolean needsMaintenance() {
        return rentalCount >= MAX_RENTALS_BEFORE_MAINTENANCE;
    }

    public boolean isInMaintenanceSinceOneTick(){
        return this.inMaintenanceSinceOneTick;
    }

    public void resetMaintenanceTick(){
        this.inMaintenanceSinceOneTick = false;
    }

    public void addTickToMaintenance(){
        this.inMaintenanceSinceOneTick = true;
    }
    
    
    /**
     * Gets the number of time intervals this vehicle has been idle.
     * 
     * @return the idle time in intervals
     */
    public int getIdleTimeIntervals() {
        return idleTimeIntervals;
    }
    
    /**
     * Increments the idle time counter.
     * Called at each time interval when vehicle is alone in station.
     */
    public void incrementIdleTime() {
        this.idleTimeIntervals++;
    }
    
    /**
     * Resets the idle time counter to zero.
     * Called when vehicle is rented or moved.
     */
    public void resetIdleTime() {
        this.idleTimeIntervals = 0;
    }
    
    /**
     * Checks if this vehicle is at risk of being stolen.
     * 
     * @return true if idle time exceeds theft threshold, false otherwise
     */
    public boolean isAtRiskOfTheft() {
        return idleTimeIntervals >= MAX_IDLE_TIME_BEFORE_THEFT;
    }
    
    /**
     * Gets the maximum number of rentals before maintenance is required.
     * 
     * @return the maintenance threshold
     */
    public static int getMaintenanceThreshold() {
        return MAX_RENTALS_BEFORE_MAINTENANCE;
    }
    
    /**
     * Gets the maximum idle time before theft risk.
     * 
     * @return the theft threshold in intervals
     */
    public static int getTheftThreshold() {
        return MAX_IDLE_TIME_BEFORE_THEFT;
    }
}
