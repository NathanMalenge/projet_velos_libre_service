package fil.l3.coo.vehicule.state;

import fil.l3.coo.vehicule.Vehicule;

/**
 * State representing a vehicle that is being maintained by a repairman.
 */
public class EnMaintenanceState implements VehiculeState {
    
    @Override
    public boolean canBeRented() {
        return false;
    }
    
    @Override
    public boolean canBeReturned() {
        return false;
    }
    
    @Override
    public boolean canBeRedistributed() {
        return false;
    }
    
    @Override
    public void rent(Vehicule vehicule) {
        throw new IllegalStateException("Cannot rent a vehicle under maintenance");
    }
    
    @Override
    public void returnVehicule(Vehicule vehicule) {
        throw new IllegalStateException("Cannot return a vehicle under maintenance");
    }
    
    @Override
    public void sendToMaintenance(Vehicule vehicule) {
    }
    
    @Override
    public void markAsStolen(Vehicule vehicule) {
        throw new IllegalStateException("Cannot mark as stolen a vehicle under maintenance");
    }
    
    /**
     * Completes the maintenance and returns the vehicle to available state.
     * 
     * @param vehicule the vehicle that has been repaired
     */
    public void completeMaintenance(Vehicule vehicule) {
        vehicule.resetRentalCount();
        vehicule.setState(new DisponibleState());
    }
    
    @Override
    public String getStateName() {
        return "EN_MAINTENANCE";
    }
    
    @Override
    public String toString() {
        return getStateName();
    }
}
