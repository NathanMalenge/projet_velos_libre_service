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
}
