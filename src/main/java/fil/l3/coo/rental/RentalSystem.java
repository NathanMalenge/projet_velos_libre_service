package fil.l3.coo.rental;

import fil.l3.coo.user.User;
import fil.l3.coo.user.exceptions.*;
import fil.l3.coo.vehicule.VehiculeComponent;
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
        
        double cost = vehicule.getPrice();
        if (!user.canAfford(cost)) {
            throw new CannotAffordRentalException(
                "User cannot afford rental cost: " + cost + " (balance: " + user.getWallet() + ")"
            );
        }
        
        try {
            T removedVehicule = station.removeVehicule(vehicule);
            user.deductMoney(cost);
            return new Location(user, removedVehicule);
        } catch (NullVehiculeException | VehiculeNotFoundException e) {
            throw new VehiculeNotAvailableException("Vehicle not found in station: " + e.getMessage());
        } catch (InsufficientFundsException | NegativeAmountException e) {
            try {
                station.parkVehicule(vehicule);
            } catch (NullVehiculeException | StationFullException ex) {
                // Ignore - vehicle wasn't removed anyway
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
        
        try {
            toStation.parkVehicule(vehicule);
            return true;
        } catch (NullVehiculeException | StationFullException e) {
            return false;
        }
    }
}
