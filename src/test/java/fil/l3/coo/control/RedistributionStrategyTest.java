package fil.l3.coo.control;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fil.l3.coo.station.Station;
import fil.l3.coo.station.exceptions.NullVehiculeException;
import fil.l3.coo.station.exceptions.StationFullException;
import fil.l3.coo.vehicule.VehiculeComponent;
import fil.l3.coo.vehicule.velo.VeloClassique;

/**
 * Test class for RedistributionStrategy implementations.
 * Tests the RoundRobinRedistribution strategy.
 */
public class RedistributionStrategyTest {

    private RedistributionStrategy strategy;
    private List<Station<VehiculeComponent>> stations;

    @BeforeEach
    public void setUp() {
        strategy = new RoundRobinRedistribution();
        stations = new ArrayList<>();
    }

    @Test
    public void testRedistributeWithEmptyList() {
        assertDoesNotThrow(() -> strategy.redistribute(stations));
    }

    @Test
    public void testRedistributeWithSingleStation() {
        Station<VehiculeComponent> station = new Station<>(15);
        stations.add(station);
            assertDoesNotThrow(() -> strategy.redistribute(stations));
    }

    @Test
    public void testRedistributeBalancesStations() throws NullVehiculeException, StationFullException {
        Station<VehiculeComponent> station1 = new Station<>(15);
        Station<VehiculeComponent> station2 = new Station<>(15);
        Station<VehiculeComponent> station3 = new Station<>(15);
        for (int i = 0; i < 10; i++) {
            station1.parkVehicule(new VeloClassique());
        }
        for (int i = 0; i < 5; i++) {
            station2.parkVehicule(new VeloClassique());
        }
        stations.add(station1);
        stations.add(station2);
        stations.add(station3);
        int before1 = station1.getOccupiedSpaces();
        int before3 = station3.getOccupiedSpaces();
        strategy.redistribute(stations);

        int after1 = station1.getOccupiedSpaces();
        int after3 = station3.getOccupiedSpaces();

        assertTrue(after1 <= before1, "Most loaded station should have fewer or same vehicles");
        assertTrue(after3 >= before3, "Least loaded station should have more or same vehicles");
    }

    @Test
    public void testRedistributeWithBalancedStations() throws NullVehiculeException, StationFullException {

        Station<VehiculeComponent> station1 = new Station<>(15);
        Station<VehiculeComponent> station2 = new Station<>(15);

        for (int i = 0; i < 5; i++) {
            station1.parkVehicule(new VeloClassique());
            station2.parkVehicule(new VeloClassique());
        }

        stations.add(station1);
        stations.add(station2);

        strategy.redistribute(stations);

        int after1 = station1.getOccupiedSpaces();
        int after2 = station2.getOccupiedSpaces();
        assertTrue(Math.abs(after1 - after2) <= 1, "Balanced stations should remain balanced");
    }

    @Test
    public void testRedistributeWithFullAndEmptyStations() throws NullVehiculeException, StationFullException {
        Station<VehiculeComponent> fullStation = new Station<>(15);
        Station<VehiculeComponent> emptyStation = new Station<>(15);

        for (int i = 0; i < 15; i++) {
            fullStation.parkVehicule(new VeloClassique());
        }

        stations.add(fullStation);
        stations.add(emptyStation);
        strategy.redistribute(stations);

        assertTrue(emptyStation.getOccupiedSpaces() > 0, "Empty station should have received vehicles");
        assertTrue(fullStation.getOccupiedSpaces() < 15, "Full station should have given away vehicles");
    }

    @Test
    public void testRedistributeMultipleTimes() throws NullVehiculeException, StationFullException {
        Station<VehiculeComponent> station1 = new Station<>(15);
        Station<VehiculeComponent> station2 = new Station<>(15);

        for (int i = 0; i < 12; i++) {
            station1.parkVehicule(new VeloClassique());
        }

        for (int i = 0; i < 2; i++) {
            station2.parkVehicule(new VeloClassique());
        }

        stations.add(station1);
        stations.add(station2);

        strategy.redistribute(stations);
        strategy.redistribute(stations);

        int diff = Math.abs(station1.getOccupiedSpaces() - station2.getOccupiedSpaces());
        assertTrue(diff <= 2, "Multiple redistributions should balance the stations");
    }

    @Test
    public void testRedistributeWithThreeStations() throws NullVehiculeException, StationFullException {
        Station<VehiculeComponent> station1 = new Station<>(15);
        Station<VehiculeComponent> station2 = new Station<>(15);
        Station<VehiculeComponent> station3 = new Station<>(15);

        for (int i = 0; i < 14; i++) {
            station1.parkVehicule(new VeloClassique());
        }

        station2.parkVehicule(new VeloClassique());

        stations.add(station1);
        stations.add(station2);
        stations.add(station3);

        int totalBefore = station1.getOccupiedSpaces() + station2.getOccupiedSpaces() + station3.getOccupiedSpaces();

        strategy.redistribute(stations);

        int totalAfter = station1.getOccupiedSpaces() + station2.getOccupiedSpaces() + station3.getOccupiedSpaces();

        assertEquals(totalBefore, totalAfter, "Total vehicles should remain constant");
        
        int maxBefore = 14;
        int maxAfter = Math.max(station1.getOccupiedSpaces(), Math.max(station2.getOccupiedSpaces(), station3.getOccupiedSpaces()));
        assertTrue(maxAfter < maxBefore, "Max occupancy should decrease after redistribution");
    }
}
