package fil.l3.coo.vehicule.state;

import fil.l3.coo.vehicule.Vehicule;

/**
 * State representing a vehicle that has been stolen.
 * This is a terminal state - the vehicle cannot be used anymore.
 */
public class VoleState implements VehiculeState {
    
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
        throw new IllegalStateException("Cannot rent a stolen vehicle");
    }
    
    @Override
    public void returnVehicule(Vehicule vehicule) {
        throw new IllegalStateException("Cannot return a stolen vehicle");
    }
    
    @Override
    public void sendToMaintenance(Vehicule vehicule) {
        throw new IllegalStateException("Cannot send to maintenance a stolen vehicle");
    }
    
    @Override
    public void markAsStolen(Vehicule vehicule) {
    }
    
    @Override
    public String getStateName() {
        return "VOLE";
    }
    
    @Override
    public String toString() {
        return getStateName();
    }
}
