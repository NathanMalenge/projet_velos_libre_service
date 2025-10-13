package fil.l3.coo.station;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fil.l3.coo.Velo.VeloComponent;
import fil.l3.coo.Velo.VeloClassique;
import fil.l3.coo.Velo.VeloElectrique;
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
    public void testCapacity() {
        assertEquals(VALID_CAPACITY, station.getCapacity());
    }
    
    @Test
    public void testInitialState() {
        assertEquals(0, station.getOccupiedSpaces());
        assertEquals(VALID_CAPACITY, station.getAvailableSpaces());
        assertTrue(station.isEmpty());
        assertFalse(station.isFull());
    }
    
    @Test
    public void testParkBike() throws NullBikeException, StationFullException {
        VeloComponent velo = new VeloClassique();
        assertTrue(station.parkBike(velo));
        assertEquals(1, station.getOccupiedSpaces());
        assertEquals(VALID_CAPACITY - 1, station.getAvailableSpaces());
        assertFalse(station.isEmpty());
    }
    
    @Test
    public void testRemoveBike() throws NullBikeException, StationFullException, BikeNotFoundException {
        VeloComponent velo = new VeloClassique();
        station.parkBike(velo);
        
        VeloComponent removed = station.removeBike(velo);
        assertNotNull(removed);
        assertEquals(velo, removed);
        assertEquals(0, station.getOccupiedSpaces());
        assertFalse(removed.isAvailable());
    }
    
    @Test
    public void testRemoveBikeNotInStation() throws NullBikeException, StationFullException {
        VeloComponent veloInStation = new VeloClassique();
        VeloComponent veloNotInStation = new VeloElectrique();
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
    public void testFillStation() throws NullBikeException, StationFullException {
        for (int i = 0; i < VALID_CAPACITY; i++) {
            VeloComponent velo = new VeloClassique();
            assertTrue(station.parkBike(velo));
        }
        assertTrue(station.isFull());
        assertEquals(0, station.getAvailableSpaces());
    }
    
    @Test
    public void testParkBikeInFullStation() throws NullBikeException, StationFullException {
        // Fill the station
        for (int i = 0; i < VALID_CAPACITY; i++) {
            station.parkBike(new VeloClassique());
        }
        
        // Try to park one more
        VeloComponent extraVelo = new VeloElectrique();
        assertThrows(StationFullException.class, () -> {
            station.parkBike(extraVelo);
        });
    }
    
    @Test
    public void testHasAvailableBikes() throws NullBikeException, StationFullException {
        assertFalse(station.hasAvailableBikes());
        
        station.parkBike(new VeloClassique());
        assertTrue(station.hasAvailableBikes());
    }
    
    @Test
    public void testCanParkBike() throws NullBikeException, StationFullException {
        assertTrue(station.canParkBike());
        
        // Fill the station
        for (int i = 0; i < VALID_CAPACITY; i++) {
            station.parkBike(new VeloClassique());
        }
        
        assertFalse(station.canParkBike());
    }
}
