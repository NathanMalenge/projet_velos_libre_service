package fil.l3.coo.station;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StationTest {
    
    private Station station;
    private static final int VALID_PLACES = 15;
    
    @BeforeEach
    public void setUp() {
        station = new Station(VALID_PLACES);
    }
    
    @Test
    public void testUniqueIds() {
        Station station1 = new Station(VALID_PLACES);
        Station station2 = new Station(VALID_PLACES);
        assertNotEquals(station1.getId(), station2.getId());
    }
    
    @Test
    public void testMinimumPlaces() {
        Station smallStation = new Station(5);
        assertEquals(10, smallStation.getNbPlaces(), "minimum 10 places");
    }
    
    @Test
    public void testMaximumPlaces() {
        Station largeStation = new Station(25);
        assertEquals(20, largeStation.getNbPlaces(), "maximum 20 places");
    }
    
    @Test
    public void testSetNbPlaces() {
        station.setNbPlaces(12);
        assertEquals(12, station.getNbPlaces());
    }
    
    @Test
    public void testOccupyPlace() {
        int initialPlaces = station.getNbPlaces();
        station.occupyPlace();
        assertEquals(initialPlaces - 1, station.getNbPlaces());
    }
    
    @Test
    public void testOccupyPlaceWhenFull() {
        for (int i = 0; i < VALID_PLACES; i++) {
            station.occupyPlace();
        }
        station.occupyPlace();
        assertEquals(0, station.getNbPlaces(), "Should not go below 0");
    }
    
    @Test
    public void testFreePlace() {
        station.occupyPlace();
        int placesAfterOccupation = station.getNbPlaces();
        station.freePlace();
        assertEquals(placesAfterOccupation + 1, station.getNbPlaces());
    }
    
    @Test
    public void testEmplacementAvailable() {
        assertTrue(station.emplacementAvailable());
        for (int i = 0; i < VALID_PLACES; i++) {
            station.occupyPlace();
        }
        assertFalse(station.emplacementAvailable());
    }
    
    @Test
    public void testToString() {
        String expected = "Station{" +
                "id=" + station.getId() +
                ", nbPlaces=" + VALID_PLACES +
                '}';
        assertEquals(expected, station.toString());
    }
}