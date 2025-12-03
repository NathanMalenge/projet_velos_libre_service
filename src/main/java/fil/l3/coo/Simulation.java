package fil.l3.coo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fil.l3.coo.control.ControlCenter;
import fil.l3.coo.station.Station;
import fil.l3.coo.station.exceptions.NullVehiculeException;
import fil.l3.coo.station.exceptions.StationFullException;
import fil.l3.coo.station.exceptions.VehiculeNotFoundException;
import fil.l3.coo.vehicule.VehiculeComponent;
import fil.l3.coo.vehicule.velo.VeloClassique;
import fil.l3.coo.vehicule.velo.VeloElectrique;

/**
 * Orchestrates the time-based simulation: setup, ticks, random moves,
 * supervision and summaries. Designed to be extended later with rentals/returns.
 */
public class Simulation {

    private final int stationCount;
    private final int initialPerStation;
    private final int ticks;
    private final long seed;

    private final ControlCenter controlCenter;
    private final Random rnd;
    private final List<Station<VehiculeComponent>> stations;

    public Simulation(int stationCount, int initialPerStation, int ticks, long seed) {
        this.stationCount = stationCount;
        this.initialPerStation = initialPerStation;
        this.ticks = ticks;
        this.seed = seed;
        this.controlCenter = new ControlCenter();
        this.rnd = new Random(seed);
        this.stations = new ArrayList<>();
    }

    /** Sets up stations and registers them with the control center. */
    public void setupStations() {
        stations.clear();
        for (int i = 0; i < stationCount; i++) {
            Station<VehiculeComponent> s = new Station<>(15);
            controlCenter.registerStation(s);
            stations.add(s);
        }
    }

    /** Populates each station with initial vehicles (classic/electric alternating). */
    public void populateInitialFleet() {
        for (Station<VehiculeComponent> s : stations) {
            for (int j = 0; j < initialPerStation; j++) {
                VehiculeComponent v = (j % 2 == 0) ? new VeloClassique() : new VeloElectrique();
                try {
                    s.parkVehicule(v);
                } catch (NullVehiculeException | StationFullException e) {
                    
                }
            }
        }
    }

    /** Runs the full simulation for the configured number of ticks. */
    public void run() {
        setupStations();
        populateInitialFleet();

        for (int t = 1; t <= ticks; t++) { //TODO gerer les intervalles pour les voleurs et les maintenance
            runTick(t);
        }       //TODO utiliser location ?
    }

    /** Executes a single tick: random moves, supervision, summary. */
    public void runTick(int tickIndex) {
        int movesThisTick = 1 + rnd.nextInt(Math.max(1, stationCount));
        randomMoves(movesThisTick);
        applySupervision();
        printSummary(tickIndex);
    }

    /** Performs a number of random moves between stations. */
    public void randomMoves(int count) {
        for (int m = 0; m < count; m++) {
            Station<VehiculeComponent> source = pickSourceWithVehicles();
            if (source == null) break;

            VehiculeComponent selected = pickRandomVehicle(source);
            Station<VehiculeComponent> dest = pickDestinationWithSpace();
            if (dest == null || selected == null) continue;

            try {
                source.removeVehicule(selected);
                dest.parkVehicule(selected);
            } catch (NullVehiculeException | VehiculeNotFoundException | StationFullException e) {
                
            }
        }
    }

    private Station<VehiculeComponent> pickSourceWithVehicles() {
        Station<VehiculeComponent> source = null;
        for (int attempts = 0; attempts < 10 && source == null; attempts++) {
            Station<VehiculeComponent> candidate = stations.get(rnd.nextInt(stations.size()));
            if (candidate.hasAvailableVehicules()) source = candidate;
        }
        return source;
    }

    private VehiculeComponent pickRandomVehicle(Station<VehiculeComponent> source) {
        List<VehiculeComponent> parked = source.getParkedVehicules();
        if (parked.isEmpty()) return null;
        return parked.get(rnd.nextInt(parked.size()));
    }

    private Station<VehiculeComponent> pickDestinationWithSpace() {
        Station<VehiculeComponent> dest = null;
        for (int attempts = 0; attempts < 10 && dest == null; attempts++) {
            Station<VehiculeComponent> candidate = stations.get(rnd.nextInt(stations.size()));
            if (candidate.hasAvailableSpace()) dest = candidate;
        }
        return dest;
    }

    /** Applies supervision rules once per tick (empty/full streaks + redistribution). */
    public void applySupervision() {
        controlCenter.onTick();
    }

    /** Prints a short summary for the current tick. */
    public void printSummary(int tickIndex) {
        System.out.printf("\n[Tick %d]\n", tickIndex);
        controlCenter.printFleetSummary();
    }
}
