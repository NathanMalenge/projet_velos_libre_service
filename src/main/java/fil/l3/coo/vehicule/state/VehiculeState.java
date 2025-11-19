package fil.l3.coo.vehicule.state;

import fil.l3.coo.vehicule.Vehicule;

/**
 * Interface representing the state of a vehicle.
 * Implements the State pattern to manage vehicle lifecycle.
 */
public interface VehiculeState {
    
    /**
     * Checks if the vehicle can be rented in this state.
     * 
     * @return true if the vehicle can be rented, false otherwise
     */
    boolean canBeRented();
    
    /**
     * Checks if the vehicle can be returned in this state.
     * 
     * @return true if the vehicle can be returned, false otherwise
     */
    boolean canBeReturned();
    
    /**
     * Checks if the vehicle can be redistributed in this state.
     * 
     * @return true if the vehicle can be redistributed, false otherwise
     */
    boolean canBeRedistributed();
    
    /**
     * Attempts to rent the vehicle.
     * Changes state if rental is successful.
     * 
     * @param vehicule the vehicle to rent
     */
    void rent(Vehicule vehicule);
    
    /**
     * Attempts to return the vehicle.
     * Changes state if return is successful.
     * 
     * @param vehicule the vehicle to return
     */
    void returnVehicule(Vehicule vehicule);
    
    /**
     * Marks the vehicle as needing maintenance.
     * 
     * @param vehicule the vehicle needing maintenance
     */
    void sendToMaintenance(Vehicule vehicule);
    
    /**
     * Marks the vehicle as stolen.
     * 
     * @param vehicule the vehicle that was stolen
     */
    void markAsStolen(Vehicule vehicule);
    
    /**
     * Gets the name of this state.
     * 
     * @return the state name
     */
    String getStateName();
}
