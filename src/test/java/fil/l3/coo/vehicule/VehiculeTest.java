package fil.l3.coo.vehicule;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Abstract base test class for all vehicles.
 * Tests common vehicle behavior (availability).
 */
public abstract class VehiculeTest {

    /**
     * Factory method to create the vehicle under test.
     * Subclasses must implement this to provide their specific vehicle type.
     * 
     * @return the vehicle to test
     */
    protected abstract Vehicule createVehicule();

    @Test
    public void testSetAvailability() {
        Vehicule vehicule = createVehicule();
        
        vehicule.setAvailable(false);
        assertFalse(vehicule.isAvailable(), "Vehicle should be unavailable after setting");
        
        vehicule.setAvailable(true);
        assertTrue(vehicule.isAvailable(), "Vehicle should be available after setting");
    } 
}
