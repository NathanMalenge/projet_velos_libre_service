package fil.l3.coo.vehicule.trottinette;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fil.l3.coo.vehicule.Vehicule;
import fil.l3.coo.vehicule.VehiculeTest;

/**
 * Test class for TrottinetteElectrique.
 * Extends VehiculeTest to inherit common vehicle tests and adds scooter-specific tests.
 */
public class TrottinetteElectriqueTest extends VehiculeTest {

    private TrottinetteElectrique trottinette;

    @Override
    protected Vehicule createVehicule() {
        return new TrottinetteElectrique();
    }

    @BeforeEach
    public void setUp() {
        trottinette = new TrottinetteElectrique();
    }

    @Test
    public void testRentalCount() {
        assertEquals(0, trottinette.getRentalCount(), "Initial rental count should be 0");

        trottinette.getState().rent(trottinette);
        trottinette.getState().returnVehicule(trottinette);

        assertEquals(1, trottinette.getRentalCount(), "Rental count should be 1 after one rental");
    }

    @Test
    public void testMaintenanceAfterTenRentals() {
        for (int i = 0; i < 10; i++) {
            trottinette.getState().rent(trottinette);
            trottinette.getState().returnVehicule(trottinette);
        }
        assertEquals("HORS_SERVICE", trottinette.getStateName(), "State should be HORS_SERVICE after 10 rentals");
    }

    @Test
    public void testSendToMaintenance() {
        trottinette.getState().sendToMaintenance(trottinette);
        assertEquals("EN_MAINTENANCE", trottinette.getStateName(), "State should be EN_MAINTENANCE");
    }

    @Test
    public void testMarkAsStolen() {
        trottinette.getState().markAsStolen(trottinette);
        assertEquals("VOLE", trottinette.getStateName(), "State should be VOLE");
    }

    @Test
    public void testIdleTimeTracking() {
        assertEquals(0, trottinette.getIdleTimeIntervals(), "Initial idle time should be 0");
        trottinette.incrementIdleTime();
        assertEquals(1, trottinette.getIdleTimeIntervals(), "Idle time should be 1 after increment");
        trottinette.resetIdleTime();
        assertEquals(0, trottinette.getIdleTimeIntervals(), "Idle time should be 0 after reset");
    }

    @Test
    public void testTheftRisk() {
        assertFalse(trottinette.isAtRiskOfTheft(), "Should not be at risk initially");
        trottinette.incrementIdleTime();
        trottinette.incrementIdleTime();
        assertTrue(trottinette.isAtRiskOfTheft(), "Should be at risk after 2 idle intervals");
    }

    @Test
    public void testNeedsMaintenance() {
        assertFalse(trottinette.needsMaintenance(), "Should not need maintenance initially");
        for (int i = 0; i < 10; i++) {
            trottinette.incrementRentalCount();
        }
        assertTrue(trottinette.needsMaintenance(), "Should need maintenance after 10 rentals");
    }

    @Test
    public void testResetRentalCount() {
        trottinette.incrementRentalCount();
        trottinette.incrementRentalCount();
        assertEquals(2, trottinette.getRentalCount(), "Rental count should be 2");
        trottinette.resetRentalCount();
        assertEquals(0, trottinette.getRentalCount(), "Rental count should be 0 after reset");
    }
}
