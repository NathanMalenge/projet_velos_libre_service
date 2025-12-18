package fil.l3.coo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * Test class for Simulation.
 * Tests the simulation setup, execution, and behavior over multiple ticks.
 */
public class SimulationTest {

    private Simulation simulation;

    @BeforeEach
    public void setUp() {
        simulation = new Simulation(3, 2, 5, 42L, 3);
    }


    @Test
    public void testRunTickDoesNotThrowException() {
        simulation.setupStations();
        simulation.populateInitialFleet();
        assertDoesNotThrow(() -> simulation.runTick(1));
    }

    @Test
    public void testRunCompleteSimulation() {
        assertDoesNotThrow(() -> simulation.run());
    }

    @Test
    public void testMultipleTicksExecution() {
        simulation.setupStations();
        simulation.populateInitialFleet();
        assertDoesNotThrow(() -> {
            for (int i = 1; i <= 5; i++) {
                simulation.runTick(i);
            }
        });
    }
}
