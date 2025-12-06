package fil.l3.coo.vehicule.decorator;

import fil.l3.coo.vehicule.VehiculeComponent;

/**
 * Decorator that adds a baggage rack to a vehicle.
 * Increases the rental price by 0.3 and updates the description.
 */
public class BaggageDecorator extends VehiculeDecorator {
    
    private static final double BAGGAGE_PRICE = 0.3;
    
    /**
     * Creates a baggage decorator for the given vehicle.
     * 
     * @param vehicule the vehicle to add a baggage rack to
     */
    public BaggageDecorator(VehiculeComponent vehicule) {
        super(vehicule);
    }
    
    /**
     * Gets the price including the baggage rack cost.
     * 
     * @return the base price plus baggage cost (0.3)
     */
    @Override
    public double getPrice() {
        return vehicule.getPrice() + BAGGAGE_PRICE;
    }
    
    /**
     * Gets the description including the baggage rack.
     * 
     * @return the base description plus " + Baggage"
     */
    @Override
    public String getDescription() {
        return vehicule.getDescription() + " + Baggage";
    }

    @Override
    public void addTickToMaintenance() {
        vehicule.addTickToMaintenance();
    }

    @Override
    public void resetMaintenanceTick() {
        vehicule.resetMaintenanceTick();
    }

    @Override
    public boolean isInMaintenanceSinceOneTick() {
        return vehicule.isInMaintenanceSinceOneTick();
    }

}
