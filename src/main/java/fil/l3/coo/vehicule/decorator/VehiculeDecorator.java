package fil.l3.coo.vehicule.decorator;

import fil.l3.coo.vehicule.VehiculeComponent;

/**
 * Abstract decorator class for vehicle accessories.
 * This class implements the VehiculeComponent interface and contains a reference
 * to another VehiculeComponent, allowing for flexible composition of vehicle features.
 */
public abstract class VehiculeDecorator implements VehiculeComponent {
    
    protected VehiculeComponent vehicule;
    
    /**
     * Creates a new vehicle decorator wrapping the given vehicle component.
     * 
     * @param vehicule the vehicle component to decorate
     */
    public VehiculeDecorator(VehiculeComponent vehicule) {
        this.vehicule = vehicule;
    }
    
    /**
     * Delegates availability check to the wrapped component.
     * 
     * @return true if the wrapped vehicle is available
     */
    @Override
    public boolean isAvailable() {
        return vehicule.isAvailable();
    }
    
    /**
     * Delegates availability setting to the wrapped component.
     * 
     * @param available the availability status to set
     */
    @Override
    public void setAvailable(boolean available) {
        vehicule.setAvailable(available);
    }
    
    /**
     * Delegates type retrieval to the wrapped component.
     * 
     * @return the type of the base vehicle
     */
    @Override
    public String getType() {
        return vehicule.getType();
    }
    
    /**
     * Default implementation delegates to wrapped component.
     * Concrete decorators should override this to add accessory costs.
     * 
     * @return the price of the wrapped component
     */
    @Override
    public double getPrice() {
        return vehicule.getPrice();
    }
    
    /**
     * Default implementation delegates to wrapped component.
     * Concrete decorators should override this to add accessory descriptions.
     * 
     * @return the description of the wrapped component
     */
    @Override
    public String getDescription() {
        return vehicule.getDescription();
    }
}
