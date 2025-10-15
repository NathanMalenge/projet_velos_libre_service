package fil.l3.coo.vehicule.velo;

import fil.l3.coo.vehicule.Vehicule;
import fil.l3.coo.vehicule.VehiculeComponent;

/**
 * Abstract class representing a bike (Velo).
 * Extends Vehicule and implements VehiculeComponent for the Decorator pattern.
 */
public abstract class Velo extends Vehicule implements VehiculeComponent {

    /**
     * Creates a new bike.
     * The bike is set as available by default.
     */
    public Velo() {
        super();
    }

    /**
     * Gets the description of this bike.
     * Default implementation returns the type.
     * 
     * @return the bike description
     */
    @Override
    public String getDescription() {
        return getType();
    }

    /**
     * Gets the rental price of this bike.
     * 
     * @return the total price including base price and accessories
     */
    @Override
    public abstract double getPrice();
    
    /**
     * Gets the type of this bike.
     * 
     * @return the bike type as a string
     */
    @Override
    public abstract String getType();
    
    @Override
    public String toString() {
        return getDescription() + "{" +
                "isAvailable=" + isAvailable() +
                ", price=" + getPrice() +
                '}';
    }
}
