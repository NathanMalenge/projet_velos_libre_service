package fil.l3.coo.rental;

import fil.l3.coo.user.User;
import fil.l3.coo.vehicule.VehiculeComponent;

/**
 * Represents a vehicle rental transaction.
 * Tracks which user has rented which vehicle.
 */
public class Location {
    
    private User user;
    private VehiculeComponent vehicule;
    
    /**
     * Creates a new rental location.
     * 
     * @param user the user renting the vehicle
     * @param vehicule the vehicle being rented
     */
    public Location(User user, VehiculeComponent vehicule) {
        this.user = user;
        this.vehicule = vehicule;
    }
    
    /**
     * Gets the user who rented the vehicle.
     * 
     * @return the renting user
     */
    public User getUser() {
        return user;
    }
    
    /**
     * Gets the rented vehicle.
     * 
     * @return the rented vehicle
     */
    public VehiculeComponent getVehicule() {
        return vehicule;
    }
    
    /**
     * Calculates the cost of this rental based on the vehicle's price.
     * 
     * @return the rental cost
     */
    public double getCost() {
        return vehicule.getPrice();
    }
    
    @Override
    public String toString() {
        return "Location{" +
                "user=" + user +
                ", vehicule=" + vehicule.getDescription() +
                ", cost=" + getCost() +
                '}';
    }
}