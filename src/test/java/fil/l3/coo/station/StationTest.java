package fil.l3.coo.station;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fil.l3.coo.vehicule.VehiculeComponent;
import fil.l3.coo.vehicule.velo.VeloClassique;
import fil.l3.coo.vehicule.velo.VeloElectrique;
import fil.l3.coo.station.exceptions.BikeNotFoundException;
import fil.l3.coo.station.exceptions.NullBikeException;
import fil.l3.coo.station.exceptions.StationFullException;

public class StationTest {
    
    private Station station;
    private static final int VALID_CAPACITY = 15;
    
    @BeforeEach
    public void setUp() {
        station = new Station(VALID_CAPACITY);
    }
    
    @Test
    public void testParkBike() throws NullBikeException, StationFullException {
        VehiculeComponent velo = new VeloClassique();
        assertFalse(station.getParkedBikes().contains(velo));
        station.parkBike(velo);
        assertTrue(station.getParkedBikes().contains(velo));
        assertEquals(1, station.getOccupiedSpaces());
        assertEquals(VALID_CAPACITY - 1, station.getAvailableSpaces());
        assertFalse(station.isEmpty());
    }
    
    @Test
    public void testRemoveBike() throws NullBikeException, StationFullException, BikeNotFoundException {
        VehiculeComponent velo = new VeloClassique();
        station.parkBike(velo);
        
        VehiculeComponent removed = station.removeBike(velo);
        assertNotNull(removed);
        assertEquals(velo, removed);
        assertEquals(0, station.getOccupiedSpaces());
        assertFalse(removed.isAvailable());
    }
    
    @Test
    public void testRemoveBikeNotInStation() throws NullBikeException, StationFullException {
        VehiculeComponent veloInStation = new VeloClassique();
        VehiculeComponent veloNotInStation = new VeloElectrique();
        station.parkBike(veloInStation);
        
        assertThrows(BikeNotFoundException.class, () -> {
            station.removeBike(veloNotInStation);
        });
        assertEquals(1, station.getOccupiedSpaces());
    }
    
    @Test
    public void testRemoveBikeNull() {
        assertThrows(NullBikeException.class, () -> {
            station.removeBike(null);
        });
    }
    
    @Test
    public void testParkBikeNull() {
        assertThrows(NullBikeException.class, () -> {
            station.parkBike(null);
        });
    }
    
    @Test
    public void testParkBikeInFullStation() throws NullBikeException, StationFullException {
        for (int i = 0; i < VALID_CAPACITY; i++) {
            VehiculeComponent velo = new VeloClassique();
            station.parkBike(velo);
        }
        assertTrue(station.isFull());
        assertEquals(0, station.getAvailableSpaces());
        VehiculeComponent extraVelo = new VeloElectrique();
        assertThrows(StationFullException.class, () -> {
            station.parkBike(extraVelo);
        });
    }
    
}
