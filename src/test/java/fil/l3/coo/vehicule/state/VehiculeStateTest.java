package fil.l3.coo.vehicule.state;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import fil.l3.coo.vehicule.velo.VeloClassique;
import fil.l3.coo.vehicule.Vehicule;

/**
 * Test class for vehicle state transitions.
 */
public class VehiculeStateTest {
    
    private VeloClassique velo;
    
    @BeforeEach
    public void setUp() {
        velo = new VeloClassique();
    }
    
    @Test
    public void testInitialStateIsDisponible() {
        assertEquals("DISPONIBLE", velo.getStateName());
        assertTrue(velo.getState().canBeRented());
        assertFalse(velo.getState().canBeReturned());
    }
    
    @Test
    public void testRentTransitionsToEnLocation() {
        assertEquals(0, velo.getRentalCount());
        
        velo.getState().rent(velo);
        
        assertEquals("EN_LOCATION", velo.getStateName());
        assertEquals(1, velo.getRentalCount());
        assertFalse(velo.getState().canBeRented());
        assertTrue(velo.getState().canBeReturned());
    }
    
    @Test
    public void testReturnTransitionsToDisponible() {
        velo.getState().rent(velo);
        assertEquals("EN_LOCATION", velo.getStateName());
        
        velo.getState().returnVehicule(velo);
        
        assertEquals("DISPONIBLE", velo.getStateName());
        assertTrue(velo.getState().canBeRented());
    }
    
    @Test
    public void testReturnAfterMultipleRentalsTriggersMaintenanceWhenThresholdReached() {
        for (int i = 0; i < Vehicule.getMaintenanceThreshold() - 1; i++) {
            velo.getState().rent(velo);
            velo.getState().returnVehicule(velo);
            assertEquals("DISPONIBLE", velo.getStateName());
        }
        
        velo.getState().rent(velo);
        assertEquals(Vehicule.getMaintenanceThreshold(), velo.getRentalCount());
        
        velo.getState().returnVehicule(velo);
        
        assertEquals("HORS_SERVICE", velo.getStateName());
        assertFalse(velo.getState().canBeRented());
    }
    
    @Test
    public void testSendToMaintenanceTransitionsToEnMaintenance() {
        velo.getState().sendToMaintenance(velo);
        
        assertEquals("EN_MAINTENANCE", velo.getStateName());
        assertFalse(velo.getState().canBeRented());
        assertFalse(velo.getState().canBeRedistributed());
    }
    
    
    @Test
    public void testCompletingMaintenanceResetsCounterAndReturnsToDisponible() {
        for (int i = 0; i < Vehicule.getMaintenanceThreshold() + 1; i++) {
            velo.incrementRentalCount();
        }
        velo.setState(new EnMaintenanceState());
        
        assertEquals(Vehicule.getMaintenanceThreshold() + 1, velo.getRentalCount());
        
        EnMaintenanceState maintenanceState = (EnMaintenanceState) velo.getState();
        maintenanceState.completeMaintenance(velo);
        
        assertEquals("DISPONIBLE", velo.getStateName());
        assertEquals(0, velo.getRentalCount());
    }
    
    @Test
    public void testMarkAsStolenTransitionsToVoleState() {
        velo.getState().markAsStolen(velo);
        
        assertEquals("VOLE", velo.getStateName());
        assertFalse(velo.getState().canBeRented());
        assertFalse(velo.getState().canBeReturned());
        assertFalse(velo.getState().canBeRedistributed());
    }
    
    @Test
    public void testIdleTimeTracking() {
        assertEquals(0, velo.getIdleTimeIntervals());
        assertFalse(velo.isAtRiskOfTheft());
        
        velo.incrementIdleTime();
        assertEquals(1, velo.getIdleTimeIntervals());
        
        velo.incrementIdleTime();
        assertEquals(2, velo.getIdleTimeIntervals());
        assertTrue(velo.isAtRiskOfTheft());
        
        velo.resetIdleTime();
        assertEquals(0, velo.getIdleTimeIntervals());
        assertFalse(velo.isAtRiskOfTheft());
    }
    
    @Test
    public void testCannotRentInEnLocationState() {
        velo.getState().rent(velo);
        
        assertThrows(IllegalStateException.class, () -> {
            velo.getState().rent(velo);
        });
    }
    
    @Test
    public void testCannotReturnInDisponibleState() {
        assertThrows(IllegalStateException.class, () -> {
            velo.getState().returnVehicule(velo);
        });
    }
    
    @Test
    public void testCannotRentStolenVehicle() {
        velo.setState(new VoleState());
        
        assertThrows(IllegalStateException.class, () -> {
            velo.getState().rent(velo);
        });
    }
    
    @Test
    public void testCannotRentVehicleUnderMaintenance() {
        velo.setState(new EnMaintenanceState());
        
        assertThrows(IllegalStateException.class, () -> {
            velo.getState().rent(velo);
        });
    }
}
