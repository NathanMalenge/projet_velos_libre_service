package fil.l3.coo.vehicule.state;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import fil.l3.coo.vehicule.Vehicule;
import fil.l3.coo.vehicule.velo.VeloClassique;

/**
 * Tests unitaires pour EnMaintenanceState
 */
public class EnMaintenanceStateTest {
    
    private Vehicule vehicule;
    private EnMaintenanceState state;
    
    @BeforeEach
    public void setUp() {
        vehicule = new VeloClassique();
        state = new EnMaintenanceState();
        vehicule.setState(state);
    }
    
    @Test
    public void testCannotBeRented() {
        assertFalse(state.canBeRented());
    }
    
    @Test
    public void testCannotBeReturned() {
        assertFalse(state.canBeReturned());
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
    public void testReturnThrowsException() {
        assertThrows(IllegalStateException.class, () -> {
            state.returnVehicule(vehicule);
        });
    }
    
    @Test
    public void testSendToMaintenanceDoesNothing() {
        state.sendToMaintenance(vehicule);
        assertEquals("EN_MAINTENANCE", vehicule.getStateName());
    }
    
    @Test
    public void testMarkAsStolenThrowsException() {
        assertThrows(IllegalStateException.class, () -> {
            state.markAsStolen(vehicule);
        });
    }
    
    @Test
    public void testCompleteMaintenanceResetsCounterAndReturnsToDisponible() {
        for (int i = 0; i < 15; i++) {
            vehicule.incrementRentalCount();
        }
        assertEquals(15, vehicule.getRentalCount());
        
        state.completeMaintenance(vehicule);
        
        assertEquals("DISPONIBLE", vehicule.getStateName());
        assertEquals(0, vehicule.getRentalCount());
    }
    
}
