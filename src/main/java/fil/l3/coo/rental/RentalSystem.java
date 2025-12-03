package fil.l3.coo.rental;

import fil.l3.coo.user.User;
import fil.l3.coo.user.exceptions.*;
import fil.l3.coo.vehicule.VehiculeComponent;
import fil.l3.coo.vehicule.Vehicule;
import fil.l3.coo.vehicule.decorator.VehiculeDecorator;
import fil.l3.coo.station.Station;
import fil.l3.coo.station.exceptions.VehiculeNotFoundException;
import fil.l3.coo.station.exceptions.NullVehiculeException;
import fil.l3.coo.station.exceptions.StationFullException;
import fil.l3.coo.rental.exceptions.*;

/**
 * Vehicle rental system.
 * Handles renting and returning vehicles between users and stations.
 */
public class RentalSystem {
    
    /**
     * Gets the underlying Vehicule from a VehiculeComponent (unwraps decorators).
     * 
     * @param component the component to unwrap
     * @return the underlying Vehicule
     */
    private Vehicule getUnderlyingVehicule(VehiculeComponent component) {
        if (component instanceof Vehicule) {
            return (Vehicule) component;
        }
        if (component instanceof VehiculeDecorator) {
            VehiculeDecorator decorator = (VehiculeDecorator) component;
            VehiculeComponent wrapped = decorator.getWrappedVehicule();
            return getUnderlyingVehicule(wrapped);
        }
        
        throw new IllegalArgumentException("Component is neither Vehicule nor VehiculeDecorator");
    }
    
    /**
     * Attempts to rent a specific vehicle from a station for a user.
     * The user must choose the vehicle from the available vehicles in the station.
     * 
     * @param <T> the type of vehicle
     * @param user the user wanting to rent
     * @param station the station to rent from
     * @param vehicule the specific vehicle to rent
     * @return the rental location if successful
     * @throws VehiculeNotAvailableException if the vehicle is not available or null
     * @throws CannotAffordRentalException if the user cannot afford the rental
     */
    public <T extends VehiculeComponent> Location rentVehicule(User user, Station<T> station, T vehicule) 
            throws VehiculeNotAvailableException, CannotAffordRentalException {
        if (vehicule == null) {
            throw new VehiculeNotAvailableException("Cannot rent null vehicle");
        }
        if (!vehicule.getState().canBeRented()) {
            throw new VehiculeNotAvailableException(
                "Vehicle cannot be rented in state: " + vehicule.getStateName()
            );
        }
        double cost = vehicule.getPrice();
        if (!user.canAfford(cost)) {
            throw new CannotAffordRentalException(
                "User cannot afford rental cost: " + cost + " (balance: " + user.getWallet() + ")"
            );
        }
        try {
            T removedVehicule = station.removeVehicule(vehicule); //TODO changer la maniere dont on choisit le velo.
            user.deductMoney(cost);
            removedVehicule.getState().rent(getUnderlyingVehicule(removedVehicule));
            
            return new Location(user, removedVehicule);
        } catch (NullVehiculeException | VehiculeNotFoundException e) {
            throw new VehiculeNotAvailableException("Vehicle not found in station: " + e.getMessage());
        } catch (InsufficientFundsException | NegativeAmountException e) {
            try {
                station.parkVehicule(vehicule);
            } catch (NullVehiculeException | StationFullException ex) {
            }
            throw new CannotAffordRentalException("Failed to deduct rental cost: " + e.getMessage());
        }
    }
    
    /**
     * Returns a vehicle to a station.
     * 
     * @param <T> the type of vehicle
     * @param location the rental to end
     * @param toStation the station to return the vehicle to
     * @return true if successful, false otherwise
     */
    public <T extends VehiculeComponent> boolean returnVehicule(Location location, Station<T> toStation) {
        @SuppressWarnings("unchecked")
        T vehicule = (T) location.getVehicule();
        if (!vehicule.getState().canBeReturned()) {
            return false;
        }
        try {
            vehicule.getState().returnVehicule(getUnderlyingVehicule(vehicule));
            
            toStation.parkVehicule(vehicule);
            return true;
        } catch (NullVehiculeException | StationFullException e) {
            return false;
        }
    }
}
