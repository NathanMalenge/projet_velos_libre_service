package fil.l3.coo.vehicule.state;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import fil.l3.coo.vehicule.Vehicule;
import fil.l3.coo.vehicule.velo.VeloClassique;

/**
 * Tests unitaires pour EnLocationState
 */
public class EnLocationStateTest {
    
    private Vehicule vehicule;
    private EnLocationState state;
    
    @BeforeEach
    public void setUp() {
        vehicule = new VeloClassique();
        state = new EnLocationState();
        vehicule.setState(state);
    }
    
    @Test
    public void testCannotBeRented() {
        assertFalse(state.canBeRented());
    }
    
    @Test
    public void testCanBeReturned() {
        assertTrue(state.canBeReturned());
    }
    
    @Test
    public void testCannotBeRedistributed() {
        assertFalse(state.canBeRedistributed());
    }
    
    @Test
    public void testRentThrowsException() {
        assertThrows(IllegalStateException.class, () -> {
            state.rent(vehicule);
        });
    }
    
    @Test
    public void testReturnTransitionsToDisponible() {
        state.returnVehicule(vehicule);
        assertEquals("DISPONIBLE", vehicule.getStateName());
        assertEquals(0, vehicule.getIdleTimeIntervals());
    }
    
    @Test
    public void testReturnTransitionsToHorsServiceWhenMaintenanceNeeded() {
        for (int i = 0; i < Vehicule.getMaintenanceThreshold(); i++) {
            vehicule.incrementRentalCount();
        }
        
        state.returnVehicule(vehicule);
        assertEquals("HORS_SERVICE", vehicule.getStateName());
    }
    
    @Test
    public void testSendToMaintenanceThrowsException() {
        assertThrows(IllegalStateException.class, () -> {
            state.sendToMaintenance(vehicule);
        });
    }
    
    @Test
    public void testMarkAsStolenThrowsException() {
        assertThrows(IllegalStateException.class, () -> {
            state.markAsStolen(vehicule);
        });
    }
    
}
