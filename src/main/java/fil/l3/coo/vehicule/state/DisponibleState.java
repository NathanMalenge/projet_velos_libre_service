package fil.l3.coo.vehicule.state;

import fil.l3.coo.vehicule.Vehicule;

/**
 * State representing a vehicle that is available for rental.
 */
public class DisponibleState implements VehiculeState {
    
    @Override
    public boolean canBeRented() {
        return true;
    }
    
    @Override
    public boolean canBeReturned() {
        return false;
    }
    
    @Override
    public boolean canBeRedistributed() {
        return true;
    }
    
    @Override
    public void rent(Vehicule vehicule) {
        vehicule.setState(new EnLocationState());
        vehicule.incrementRentalCount();
    }
    
    @Override
    public void returnVehicule(Vehicule vehicule) {
        throw new IllegalStateException("Cannot return a vehicle that is not rented");
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
        return "DISPONIBLE";
    }
    
    @Override
    public String toString() {
        return getStateName();
    }
}
