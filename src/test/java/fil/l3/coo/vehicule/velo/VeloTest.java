package fil.l3.coo.vehicule.velo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import fil.l3.coo.vehicule.Vehicule;
import fil.l3.coo.vehicule.VehiculeTest;

/**
 * Abstract test class for all bikes.
 * Extends VehiculeTest to inherit common vehicle tests.
 * Adds bike-specific tests.
 */
public abstract class VeloTest extends VehiculeTest {

    /**
     * Factory method to create the bike under test.
     * 
     * @return the bike to test
     */
    protected abstract Velo createVelo();
    
     /**
     * Implementation of VehiculeTest's factory method.
     * Delegates to createVelo().
     */
    @Override
    protected Vehicule createVehicule() {
        return createVelo();
    }
}
