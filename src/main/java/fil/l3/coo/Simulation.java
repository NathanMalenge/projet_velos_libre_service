package fil.l3.coo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fil.l3.coo.control.ControlCenter;
import fil.l3.coo.rental.Location;
import fil.l3.coo.rental.RentalSystem;
import fil.l3.coo.station.Station;
import fil.l3.coo.station.exceptions.NullVehiculeException;
import fil.l3.coo.station.exceptions.StationFullException;
import fil.l3.coo.user.User;
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

    private final ControlCenter controlCenter;
    private final Random rnd;
    private int userCount;
    private final List<Station<VehiculeComponent>> stations;
    private final List<User> users;
    private final List<Location> activeLocations;
    private final RentalSystem rentalSystem;

    public Simulation(int stationCount, int initialPerStation, int ticks, long seed, int userCount) {
        this.stationCount = stationCount;
        this.userCount = userCount;
        this.initialPerStation = initialPerStation;
        this.ticks = ticks;
        this.controlCenter = new ControlCenter();
        this.rnd = new Random(seed);
        this.stations = new ArrayList<>();
        this.users = new ArrayList<>();
        this.activeLocations = new ArrayList<>();
        this.rentalSystem = new RentalSystem();
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

    /** Creates a small pool of users with initial wallets. */
    private void setupUsers() {
        users.clear();
        for (int i = 0; i < userCount; i++) {
            double wallet = 5.0 + rnd.nextInt(11);
            users.add(new User(wallet));
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
        setupUsers();

        for (int t = 1; t <= ticks; t++) { 
            runTick(t);
        }      
    }

    /** Executes a single tick: random moves, supervision, summary. */
    public void runTick(int tickIndex) {
        creditUsers();
        int movesThisTick = 1 + rnd.nextInt(Math.max(1, stationCount));
        performRandomRentals(movesThisTick);
        performRandomReturns();
        applySupervision();
        printSummary(tickIndex);
    }

    /**
     * Credits all users with a small amount each tick to keep
     * the simulation lively and avoid getting stuck once wallets
     * are empty. This has no impact on the core design, it only
     * makes the demo more interesting.
     */
    private void creditUsers() {
        for (User user : users) {
            try {
                user.addMoney(1.0);
            } catch (Exception e) {
            }
        }
    }

    /** Performs random rentals using users and the RentalSystem. */
    private void performRandomRentals(int count) {
        for (int m = 0; m < count; m++) {
            if (users.isEmpty()) return;

            Station<VehiculeComponent> source = pickSourceWithVehicles();
            if (source == null) break;

            int availableIndex = 0;
            List<VehiculeComponent> parked = source.getParkedVehicules();
            long availableCount = parked.stream().filter(VehiculeComponent::isAvailable).count();
            if (availableCount == 0) continue;
            if (availableCount > 1) {
                availableIndex = rnd.nextInt((int) availableCount);
            }

            VehiculeComponent selected = pickIthAvailableVehicle(source, availableIndex);
            if (selected == null) continue;

            User user = users.get(rnd.nextInt(users.size()));

            try {
                Location location = rentalSystem.rentVehicule(user, source, selected);
                activeLocations.add(location);
            } catch (Exception e) {
            }
        }
    }

    /** Performs random returns of some active locations to random stations. */
    private void performRandomReturns() {
        if (activeLocations.isEmpty()) return;

        // try to return up to a small number of vehicles each tick
        int returns = 1 + rnd.nextInt(Math.min(3, activeLocations.size()));
        for (int i = 0; i < returns && !activeLocations.isEmpty(); i++) {
            Location location = activeLocations.remove(0);
            Station<VehiculeComponent> dest = pickDestinationWithSpace();
            if (dest == null) {
                activeLocations.add(location);
                break;
            }
            rentalSystem.returnVehicule(location, dest);
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

    /**
     * Picks the i-th available vehicle in the given station. If the index
     * is out of bounds, the first available vehicle is returned instead.
     * Returns {@code null} if there is no available vehicle.
     */
    private VehiculeComponent pickIthAvailableVehicle(Station<VehiculeComponent> source, int index) {
        List<VehiculeComponent> parked = source.getParkedVehicules();
        List<VehiculeComponent> available = new ArrayList<>();
        for (VehiculeComponent v : parked) {
            if (v.isAvailable()) {
                available.add(v);
            }
        }
        if (available.isEmpty()) {
            return null;
        }
        if (index < 0 || index >= available.size()) {
            return available.get(0);
        }
        return available.get(index);
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
        printEventsForTick();
    }

    /**
     * Prints a few recent events for each station to illustrate
     * maintenance, thefts and movements during the tick.
     */
    private void printEventsForTick() {
        for (Station<VehiculeComponent> station : stations) {
            List<String> events = controlCenter.getStationEvents(station.getId());
            if (events.isEmpty()) {
                continue;
            }
            System.out.printf("  Station %d events:%n", station.getId());
            int from = Math.max(0, events.size() - 3);
            for (int i = from; i < events.size(); i++) {
                System.out.printf("    - %s%n", events.get(i));
            }
        }
    }
}
