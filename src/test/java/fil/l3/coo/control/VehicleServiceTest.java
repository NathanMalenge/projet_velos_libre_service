package fil.l3.coo.control;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fil.l3.coo.station.Station;
import fil.l3.coo.vehicule.VehiculeComponent;
import fil.l3.coo.vehicule.state.EnMaintenanceState;
import fil.l3.coo.vehicule.velo.VeloClassique;

public class VehicleServiceTest {

    private ControlCenter controlCenter;
    private Repairer repairer;
    private Station<VehiculeComponent> station;
    private VehiculeComponent vehicule;

    @BeforeEach
    public void setUp() {
        controlCenter = new ControlCenter();
        repairer = new Repairer();
        station = new Station<>(15);
        vehicule = new VeloClassique();
    }

    @Test
    public void testRepairVehicleInMaintenance() {
        vehicule.setState(new EnMaintenanceState());
        assertEquals("EN_MAINTENANCE", vehicule.getStateName());

        boolean repaired = repairer.repair(station, vehicule);
        
        assertTrue(repaired);
        assertEquals("DISPONIBLE", vehicule.getStateName());
    }

    @Test
    public void testVehicleServiceInterface() {
        VehicleService service = repairer;
        vehicule.setState(new EnMaintenanceState());
        boolean serviced = service.service(station, vehicule);
        assertTrue(serviced);
        assertEquals("REPAIR", service.getServiceType());
    }
    
    @Test
    public void testControlCenterHasRepairService() {
        VehicleService repairService = controlCenter.getService("REPAIR");
        assertNotNull(repairService);
        assertEquals("REPAIR", repairService.getServiceType());
    }
}
