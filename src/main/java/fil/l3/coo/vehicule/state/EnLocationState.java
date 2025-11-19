package fil.l3.coo.vehicule.state;

import fil.l3.coo.vehicule.Vehicule;

/**
 * State representing a vehicle that is currently rented.
 */
public class EnLocationState implements VehiculeState {
    
    @Override
    public boolean canBeRented() {
        return false;
    }
    
    @Override
    public boolean canBeReturned() {
        return true;
    }
    
    @Override
    public boolean canBeRedistributed() {
        return false;
    }
    
    @Override
    public void rent(Vehicule vehicule) {
        throw new IllegalStateException("Cannot rent a vehicle that is already rented");
    }
    
    @Override
    public void returnVehicule(Vehicule vehicule) {
        if (vehicule.needsMaintenance()) {
            vehicule.setState(new HorsServiceState());
        } else {
            vehicule.setState(new DisponibleState());
        }
        vehicule.resetIdleTime();
    }
    
    @Override
    public void sendToMaintenance(Vehicule vehicule) {
        throw new IllegalStateException("Cannot send to maintenance a vehicle that is rented");
    }
    
    @Override
    public void markAsStolen(Vehicule vehicule) {
        throw new IllegalStateException("Cannot mark as stolen a vehicle that is rented");
    }
    
    @Override
    public String getStateName() {
        return "EN_LOCATION";
    }
    
    @Override
    public String toString() {
        return getStateName();
    }
}
