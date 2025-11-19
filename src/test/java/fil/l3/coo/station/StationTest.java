package fil.l3.coo.station;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fil.l3.coo.vehicule.VehiculeComponent;
import fil.l3.coo.vehicule.velo.VeloClassique;
import fil.l3.coo.vehicule.velo.VeloElectrique;
import fil.l3.coo.station.exceptions.VehiculeNotFoundException;
import fil.l3.coo.station.exceptions.NullVehiculeException;
import fil.l3.coo.station.exceptions.StationFullException;

public class StationTest {
    
    private Station<VehiculeComponent> station;
    private static final int VALID_CAPACITY = 15;
    
    @BeforeEach
    public void setUp() {
        station = new Station<>(VALID_CAPACITY);
    }
    
    @Test
    public void testParkBike() throws NullVehiculeException, StationFullException {
        VehiculeComponent velo = new VeloClassique();
        assertFalse(station.getParkedVehicules().contains(velo));
        station.parkVehicule(velo);
        assertTrue(station.getParkedVehicules().contains(velo));
        assertEquals(1, station.getOccupiedSpaces());
        assertEquals(VALID_CAPACITY - 1, station.getAvailableSpaces());
        assertFalse(station.isEmpty());
    }
    
    @Test
    public void testRemoveBike() throws NullVehiculeException, StationFullException, VehiculeNotFoundException {
        VehiculeComponent velo = new VeloClassique();
        station.parkVehicule(velo);
        
        VehiculeComponent removed = station.removeVehicule(velo);
        assertNotNull(removed);
        assertEquals(velo, removed);
        assertEquals(0, station.getOccupiedSpaces());
        assertFalse(removed.isAvailable());
    }
    
    @Test
    public void testRemoveBikeNotInStation() throws NullVehiculeException, StationFullException {
        VehiculeComponent veloInStation = new VeloClassique();
        VehiculeComponent veloNotInStation = new VeloElectrique();
        station.parkVehicule(veloInStation);
        
        assertThrows(VehiculeNotFoundException.class, () -> {
            station.removeVehicule(veloNotInStation);
        });
        assertEquals(1, station.getOccupiedSpaces());
    }
    
    @Test
    public void testRemoveBikeNull() {
        assertThrows(NullVehiculeException.class, () -> {
            station.removeVehicule(null);
        });
    }
    
    @Test
    public void testParkBikeNull() {
        assertThrows(NullVehiculeException.class, () -> {
            station.parkVehicule(null);
        });
    }
    
    @Test
    public void testParkBikeInFullStation() throws NullVehiculeException, StationFullException {
        for (int i = 0; i < VALID_CAPACITY; i++) {
            VehiculeComponent velo = new VeloClassique();
            station.parkVehicule(velo);
        }
        assertTrue(station.isFull());
        assertEquals(0, station.getAvailableSpaces());
        VehiculeComponent extraVelo = new VeloElectrique();
        assertThrows(StationFullException.class, () -> {
            station.parkVehicule(extraVelo);
        });
    }

    @Test
    public void testObserversReceiveNotifications() throws NullVehiculeException, StationFullException, VehiculeNotFoundException {
        RecordingObserver observer = new RecordingObserver();
        station.addObserver(observer);

        VehiculeComponent velo = new VeloClassique();
        station.parkVehicule(velo);
        station.removeVehicule(velo);

        assertEquals(2, observer.events.size());
        assertTrue(observer.events.get(0).contains("parked"));
        assertTrue(observer.events.get(1).contains("removed"));
    }

    private static class RecordingObserver implements StationObserver {
        private final List<String> events = new ArrayList<>();

        @Override
        public void onVehicleParked(Station<?> station, VehiculeComponent vehicule) {
            events.add("parked-" + station.getId() + "-" + vehicule.getDescription());
        }

        @Override
        public void onVehicleRemoved(Station<?> station, VehiculeComponent vehicule) {
            events.add("removed-" + station.getId() + "-" + vehicule.getDescription());
        }
    }
    
}
