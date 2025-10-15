package fil.l3.coo.vehicule.decorator;

import fil.l3.coo.vehicule.VehiculeComponent;

/**
 * Decorator that adds a basket to a vehicle.
 * Increases the rental price by 0.5 and updates the description.
 */
public class BasketDecorator extends VehiculeDecorator {
    
    private static final double BASKET_PRICE = 0.5;
    
    /**
     * Creates a basket decorator for the given vehicle.
     * 
     * @param vehicule the vehicle to add a basket to
     */
    public BasketDecorator(VehiculeComponent vehicule) {
        super(vehicule);
    }
    
    /**
     * Gets the price including the basket cost.
     * 
     * @return the base price plus basket cost (0.5)
     */
    @Override
    public double getPrice() {
        return vehicule.getPrice() + BASKET_PRICE;
    }
    
    /**
     * Gets the description including the basket.
     * 
     * @return the base description plus " + Basket"
     */
    @Override
    public String getDescription() {
        return vehicule.getDescription() + " + Basket";
    }
}
