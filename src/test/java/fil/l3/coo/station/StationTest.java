package fil.l3.coo.station;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StationTest {
    
    private Station station;
    private static final int VALID_CAPACITY = 15;
    
    @BeforeEach
    public void setUp() {
        station = new Station(VALID_CAPACITY);
    }
    
    @Test
    public void testUniqueIds() {
        Station station1 = new Station(VALID_CAPACITY);
        Station station2 = new Station(VALID_CAPACITY);
        assertNotEquals(station1.getId(), station2.getId());
    }
    
    @Test
    public void testMinimumCapacity() {
        Station smallStation = new Station(5);
        assertEquals(10, smallStation.getCapacity());
    }
    
    @Test
    public void testMaximumCapacity() {
        Station largeStation = new Station(25);
        assertEquals(20, largeStation.getCapacity());
    }
    
    @Test
    public void testInitialState() {
        assertEquals(VALID_CAPACITY, station.getCapacity());
        assertEquals(0, station.getOccupiedSpaces());
        assertEquals(VALID_CAPACITY, station.getAvailableSpaces());
        assertTrue(station.isEmpty());
        assertFalse(station.isFull());
        assertTrue(station.hasAvailableSpace());
        assertFalse(station.hasBikes());
    }
    
    @Test
    public void testParkBike() {
        assertTrue(station.parkBike());
        assertEquals(1, station.getOccupiedSpaces());
        assertEquals(VALID_CAPACITY - 1, station.getAvailableSpaces());
        assertFalse(station.isEmpty());
        assertTrue(station.hasBikes());
    }
    
    @Test
    public void testRemoveBike() {
        station.parkBike();
        assertTrue(station.removeBike());
        assertEquals(0, station.getOccupiedSpaces());
        assertEquals(VALID_CAPACITY, station.getAvailableSpaces());
        assertTrue(station.isEmpty());
        assertFalse(station.hasBikes());
    }
    
    @Test
    public void testRemoveBikeFromEmptyStation() {
        assertFalse(station.removeBike());
        assertTrue(station.isEmpty());
    }
    
    @Test
    public void testFillStation() {
        for (int i = 0; i < VALID_CAPACITY; i++) {
            assertTrue(station.parkBike());
        }
        assertTrue(station.isFull());
        assertFalse(station.hasAvailableSpace());
        assertEquals(VALID_CAPACITY, station.getOccupiedSpaces());
        assertEquals(0, station.getAvailableSpaces());
    }
    
    @Test
    public void testParkBikeInFullStation() {
        for (int i = 0; i < VALID_CAPACITY; i++) {
            station.parkBike();
        }
        assertFalse(station.parkBike());
        assertEquals(VALID_CAPACITY, station.getOccupiedSpaces());
    }
    
    @Test
    public void testToString() {
        String stationString = station.toString();
        assertTrue(stationString.contains("Station"));
        assertTrue(stationString.contains(String.valueOf(station.getId())));
        assertTrue(stationString.contains(String.valueOf(station.getOccupiedSpaces())));
        assertTrue(stationString.contains(String.valueOf(station.getCapacity())));
    }
}