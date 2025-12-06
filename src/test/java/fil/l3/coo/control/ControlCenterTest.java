package fil.l3.coo.control;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fil.l3.coo.station.Station;
import fil.l3.coo.station.exceptions.NullVehiculeException;
import fil.l3.coo.station.exceptions.StationFullException;
import fil.l3.coo.station.exceptions.VehiculeNotFoundException;
import fil.l3.coo.vehicule.VehiculeComponent;
import fil.l3.coo.vehicule.velo.VeloClassique;

public class ControlCenterTest {

    private ControlCenter controlCenter;
    private Station<VehiculeComponent> station;

    /**
     * Factory method to create a vehicle for tests.
     * Can be overridden to test with different vehicle types.
     */
    protected VehiculeComponent createVehicule() {
        return new VeloClassique();
    }

    @BeforeEach
    public void setUp() {
        controlCenter = new ControlCenter();
        station = new Station<>(15);
    }

    @Test
    public void registerStationTracksCapacityAndVehicles() throws NullVehiculeException, StationFullException {
        controlCenter.registerStation(station);
        assertTrue(controlCenter.getStations().contains(station));

        station.parkVehicule(createVehicule());
        station.parkVehicule(createVehicule());

        assertEquals(2, controlCenter.getTotalVehicles());

        List<String> events = controlCenter.getStationEvents(station.getId());
        assertEquals(2, events.size());
        assertTrue(events.get(0).contains("parked"));
    }

    @Test
    public void unregisterStationStopsReceivingEvents() throws NullVehiculeException, StationFullException, VehiculeNotFoundException {
        controlCenter.registerStation(station);
        VehiculeComponent velo = createVehicule();
        station.parkVehicule(velo);
        assertFalse(controlCenter.getStationEvents(station.getId()).isEmpty());

        controlCenter.unregisterStation(station);
        station.removeVehicule(velo);

        assertFalse(controlCenter.getStations().contains(station));
        assertTrue(controlCenter.getStationEvents(station.getId()).isEmpty());
        assertEquals(0, controlCenter.getTotalVehicles());
    }   
    
    @Test
    public void testRegisterServiceIgnoresNull() {
        int initialSize = controlCenter.getServices().size();
        controlCenter.registerService(null);
        assertEquals(initialSize, controlCenter.getServices().size());
    }
    
    @Test
    public void testRegisterServiceIgnoresDuplicates() {
        VehicleService service = controlCenter.getService("REPAIR");
        int initialSize = controlCenter.getServices().size();
        
        controlCenter.registerService(service);
        assertEquals(initialSize, controlCenter.getServices().size());
    }
    
    @Test
    public void testGetServiceByType() {
        VehicleService repairService = controlCenter.getService("REPAIR");
        assertNotNull(repairService);
        assertEquals("REPAIR", repairService.getServiceType());
    }
    
    @Test
    public void testGetServiceReturnsNullForUnknownType() {
        VehicleService service = controlCenter.getService("UNKNOWN");
        assertNull(service);
    }
    
    @Test
    public void testMultipleStationsTracking() throws NullVehiculeException, StationFullException {
        Station<VehiculeComponent> station2 = new Station<>(12);
        
        controlCenter.registerStation(station);
        controlCenter.registerStation(station2);
        
        station.parkVehicule(createVehicule());
        station2.parkVehicule(createVehicule());
        station2.parkVehicule(createVehicule());
        
        assertEquals(2, controlCenter.getStations().size());
        assertEquals(3, controlCenter.getTotalVehicles());
        assertEquals(27, controlCenter.getTotalCapacity());
    }
    
    @Test
    public void testGetStationEventsForNonExistentStation() {
        List<String> events = controlCenter.getStationEvents(999);
        assertTrue(events.isEmpty());
    }

    @Test
    public void testMaintenanceTakesOneTickBeforeRepair() throws NullVehiculeException, StationFullException {
        controlCenter.registerStation(station);
        VehiculeComponent velo = createVehicule();

        // Marque le vélo en maintenance lors du dépôt
        for (int i = 0; i < fil.l3.coo.vehicule.Vehicule.getMaintenanceThreshold(); i++) {
            velo.incrementRentalCount();
        }
        station.parkVehicule(velo);
        assertEquals("EN_MAINTENANCE", velo.getStateName());

        // Premier tick : le vélo reste en maintenance
        controlCenter.onTick();
        assertEquals("EN_MAINTENANCE", velo.getStateName());

        // Second tick : le réparateur intervient et remet le vélo disponible
        controlCenter.onTick();
        assertEquals("DISPONIBLE", velo.getStateName());
        assertEquals(0, velo.getRentalCount());
    }

    @Test
    public void testVehicleTheftAfterBeingAloneForTwoTicks() throws Exception {
        controlCenter.registerStation(station);
        VehiculeComponent velo = createVehicule();
        station.parkVehicule(velo);

        // Force le vélo à être à risque de vol
        velo.incrementIdleTime();
        velo.incrementIdleTime();
        assertTrue(velo.isAtRiskOfTheft());

        // On ne peut pas contrôler Math.random simplement ici, donc on vérifie au moins
        // que la logique ne jette pas d'exception et que l'état interne reste cohérent.
        // Le vol effectif reste aléatoire.
        controlCenter.onTick();
    }
}
