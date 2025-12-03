package fil.l3.coo.vehicule.state;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import fil.l3.coo.vehicule.Vehicule;
import fil.l3.coo.vehicule.velo.VeloClassique;

/**
 * Tests unitaires pour DisponibleState
 */
public class DisponibleStateTest {
    
    private Vehicule vehicule;
    private DisponibleState state;
    
    @BeforeEach
    public void setUp() {
        vehicule = new VeloClassique();
        state = new DisponibleState();
        vehicule.setState(state);
    }
    
    @Test
    public void testCanBeRented() {
        assertTrue(state.canBeRented());
    }
    
    @Test
    public void testCannotBeReturned() {
        assertFalse(state.canBeReturned());
    }
    
    @Test
    public void testCanBeRedistributed() {
        assertTrue(state.canBeRedistributed());
    }
    
    @Test
    public void testRentTransitionsToEnLocation() {
        int initialCount = vehicule.getRentalCount();
        state.rent(vehicule);
        
        assertEquals("EN_LOCATION", vehicule.getStateName());
        assertEquals(initialCount + 1, vehicule.getRentalCount());
    }
    
    @Test
    public void testReturnThrowsException() {
        assertThrows(IllegalStateException.class, () -> {
            state.returnVehicule(vehicule);
        });
    }
    
    @Test
    public void testSendToMaintenanceTransitions() {
        state.sendToMaintenance(vehicule);
        assertEquals("EN_MAINTENANCE", vehicule.getStateName());
    }
    
    @Test
    public void testMarkAsStolenTransitions() {
        state.markAsStolen(vehicule);
        assertEquals("VOLE", vehicule.getStateName());
    }
    
}
