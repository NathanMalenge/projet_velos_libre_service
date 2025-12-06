package fil.l3.coo.vehicule.trottinette;

import fil.l3.coo.vehicule.Vehicule;

/**
 * Simple example of a new type of vehicle.
 * <p>
 * This electric scooter reuses the generic {@link Vehicule} lifecycle
 * (State pattern) and can be stored in stations like bikes. It mainly
 * serves to illustrate the extensibility requirement of the project.
 */
public class TrottinetteElectrique extends Vehicule {

    private static final double BASE_PRICE = 1.5;

    @Override
    public double getPrice() {
        return BASE_PRICE;
    }

    @Override
    public String getDescription() {
        return "TrottinetteElectrique";
    }

    @Override
    public String getType() {
        return "TrottinetteElectrique";
    }
}
