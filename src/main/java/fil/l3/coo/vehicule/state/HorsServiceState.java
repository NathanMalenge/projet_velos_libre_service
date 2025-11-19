package fil.l3.coo.vehicule.state;

import fil.l3.coo.vehicule.Vehicule;

/**
 * State representing a vehicle that is out of service and needs maintenance.
 */
public class HorsServiceState implements VehiculeState {
    
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
        throw new IllegalStateException("Cannot rent a vehicle that is out of service");
    }
    
    @Override
    public void returnVehicule(Vehicule vehicule) {
        throw new IllegalStateException("Cannot return a vehicle that is out of service");
    }
    
    @Override
    public void sendToMaintenance(Vehicule vehicule) {
        vehicule.setState(new EnMaintenanceState());
    }
    
    @Override
    public void markAsStolen(Vehicule vehicule) {
        vehicule.setState(new VoleState());
    }
    
    @Override
    public String getStateName() {
        return "HORS_SERVICE";
    }
    
    @Override
    public String toString() {
        return getStateName();
    }
}
