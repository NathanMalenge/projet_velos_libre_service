package fil.l3.coo.vehicule.state;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import fil.l3.coo.vehicule.Vehicule;
import fil.l3.coo.vehicule.velo.VeloClassique;

/**
 * Tests unitaires pour VoleState 
 */
public class VoleStateTest {
    
    private Vehicule vehicule;
    private VoleState state;
    
    @BeforeEach
    public void setUp() {
        vehicule = new VeloClassique();
        state = new VoleState();
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
    public void testSendToMaintenanceThrowsException() {
        assertThrows(IllegalStateException.class, () -> {
            state.sendToMaintenance(vehicule);
        });
    }
    
    @Test
    public void testMarkAsStolenDoesNothing() {
        state.markAsStolen(vehicule);
        assertEquals("VOLE", vehicule.getStateName());
    }
    
}
