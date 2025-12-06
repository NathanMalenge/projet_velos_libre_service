package fil.l3.coo.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fil.l3.coo.station.Station;
import fil.l3.coo.station.StationObserver;
import fil.l3.coo.vehicule.VehiculeComponent;
import fil.l3.coo.vehicule.state.EnMaintenanceState;

/**
 * Central supervision component for the simulation.
 * <p>
 * The control center observes all registered stations, records events,
 * schedules maintenance, detects theft situations and triggers vehicle
 * redistribution when stations stay empty or full for too long.
 */
public class ControlCenter implements StationObserver {

    private final List<Station<VehiculeComponent>> stations;
    private final Map<Integer, List<String>> stationEvents;
    private final List<VehicleService> services;
    private final Map<Integer, Integer> emptyStreaks; 
    private final Map<Integer, Integer> fullStreaks; 
    private RedistributionStrategy redistributionStrategy;

    public ControlCenter() {
        this.stations = new ArrayList<>();
        this.stationEvents = new HashMap<>();
        this.services = new ArrayList<>();
        this.emptyStreaks = new HashMap<>();
        this.fullStreaks = new HashMap<>();
        this.redistributionStrategy = new RoundRobinRedistribution();
        this.services.add(new Repairer());
    }
    
    /**
        * Registers a new vehicle service (repair, painting, etc.).
        *
        * @param service the service to register
     */
    public void registerService(VehicleService service) {
        if (service != null && !services.contains(service)) {
            services.add(service);
        }
    }
    
    /**
        * Returns all registered services.
        *
        * @return a defensive copy of the services list
     */
    public List<VehicleService> getServices() {
        return new ArrayList<>(services);
    }
    
    /**
        * Returns a service by its logical type.
        *
        * @param serviceType the type of service (for example "REPAIR" or "PAINT")
        * @return the service instance, or {@code null} if none matches
     */
    public VehicleService getService(String serviceType) {
        return services.stream()
                .filter(s -> s.getServiceType().equals(serviceType))
                .findFirst()
                .orElse(null);
    }

    /**
        * Registers a station to be supervised.
        * <p>
        * The control center subscribes to station events and starts tracking its
        * metrics (capacity, events, empty/full streaks).
     * 
     * @param station the station to register
     */
    public void registerStation(Station<VehiculeComponent> station) {
        if (station != null && !stations.contains(station)) {
            stations.add(station);
            station.addObserver(this);
            stationEvents.putIfAbsent(station.getId(), new ArrayList<>());
            emptyStreaks.put(station.getId(), 0);
            fullStreaks.put(station.getId(), 0);
        }
    }

    /**
        * Unregisters a station.
        * <p>
        * The control center stops observing the station and discards its history
        * and streak counters.
     * 
     * @param station the station to unregister
     */
    public void unregisterStation(Station<VehiculeComponent> station) {
        if (station != null && stations.remove(station)) {
            station.removeObserver(this);
            stationEvents.remove(station.getId());
            emptyStreaks.remove(station.getId());
            fullStreaks.remove(station.getId());
        }
    }

    /**
        * Returns all supervised stations.
        *
        * @return a defensive copy of the registered stations list
     */
    public List<Station<VehiculeComponent>> getStations() {
        return new ArrayList<>(stations);
    }

    /**
     * Computes the total number of vehicles currently parked in all stations.
     * 
     * @return the total number of parked vehicles
     */
    public int getTotalVehicles() {
        return stations.stream()
                .mapToInt(Station::getOccupiedSpaces)
                .sum();
    }

    /**
     * Computes the total capacity across all stations.
     * 
     * @return the total capacity of all stations
     */
    public int getTotalCapacity() {
        return stations.stream()
                .mapToInt(Station::getCapacity)
                .sum();
    }

    /**
        * Returns the recorded events for a station, ordered chronologically.
     * 
     * @param stationId the ID of the station
     * @return the list of events for the station
     */
    public List<String> getStationEvents(int stationId) {
        return new ArrayList<>(stationEvents.getOrDefault(stationId, List.of()));
    }

    @Override
    public void onVehicleParked(Station<?> station, VehiculeComponent vehicule) {
        String event = String.format("Vehicle parked: %s (%.2f €)",
                vehicule.getDescription(),
                vehicule.getPrice());
        
        int stationId = station.getId();
        if (!stationEvents.containsKey(stationId)) {
            stationEvents.put(stationId, new ArrayList<>());
        }
        
        stationEvents.get(stationId).add(event);
        if (vehicule.needsMaintenance()) {
            vehicule.setState(new EnMaintenanceState());
            String maintenanceEvent = String.format("Vehicle needs maintenance: %s", vehicule.getDescription());
            stationEvents.get(stationId).add(maintenanceEvent);
        }
    }

    @Override
    public void onVehicleRemoved(Station<?> station, VehiculeComponent vehicule) {
        String event = String.format("Vehicle removed: %s (%.2f €)",
                vehicule.getDescription(),
                vehicule.getPrice());
        
        int stationId = station.getId();
        if (!stationEvents.containsKey(stationId)) {
            stationEvents.put(stationId, new ArrayList<>());
        }
        
        stationEvents.get(stationId).add(event);
    }

    /**
     * Called once per simulation tick to update supervision rules and possibly
     * trigger a redistribution if stations stay empty or full for too long.
     */
    public void onTick() {
        boolean needRedistribution = false;
        for (Station<VehiculeComponent> s : stations) {
            int id = s.getId();
            int empty = emptyStreaks.getOrDefault(id, 0);
            int full = fullStreaks.getOrDefault(id, 0);
            List<VehiculeComponent> vehicules = s.getParkedVehicules();
            handleMaintenanceForStation(s, vehicules);
            handleTheftForStation(s, vehicules, id);
            needRedistribution = updateStreaksAndCheckRedistribution(s, id, empty, full) || needRedistribution;
        }

        if (needRedistribution && redistributionStrategy != null) {
            redistributeAndResetStreaks();
        }
    }

    /**
     * Updates maintenance status for all vehicles parked in a station.
     * <p>
     * Vehicles that have just entered maintenance stay unavailable for one
     * tick before the registered repair service is invoked.
     *
     * @param station   the station being processed
     * @param vehicules currently parked vehicles in this station
     */
    private void handleMaintenanceForStation(Station<VehiculeComponent> station, List<VehiculeComponent> vehicules) {
        for (VehiculeComponent v : vehicules) {
            if ("EN_MAINTENANCE".equals(v.getStateName())) {
                if (v.isInMaintenanceSinceOneTick()) {
                    VehicleService repairService = getService("REPAIR");
                    if (repairService != null) {
                        boolean serviced = repairService.service(station, v);

                        if (serviced) {
                            String maintenanceEvent = String.format("Vehicle repaired: %s", v.getDescription());
                            stationEvents.get(station.getId()).add(maintenanceEvent);
                        }
                    }
                    v.resetMaintenanceTick();
                } else {
                    v.addTickToMaintenance();
                }
            }
        }
    }

    /**
     * Updates idle time and possibly removes stolen vehicles for a station.
     * <p>
     * A vehicle can be stolen only if it is the single available vehicle in
     * the station and has remained idle long enough. Theft is applied with a
     * fixed probability.
     *
     * @param station    the station being processed
     * @param vehicules  currently parked vehicles in this station
     * @param stationId  identifier of the station
     */
    private void handleTheftForStation(Station<VehiculeComponent> station, List<VehiculeComponent> vehicules, int stationId) {
        if (vehicules.size() == 1) {
            VehiculeComponent v = vehicules.get(0);
            if (v.isAvailable()) {
                v.incrementIdleTime();

                if (v.isAtRiskOfTheft() && Math.random() < 0.5) {
                    try {
                        station.removeVehicule(v);
                        stationEvents.get(stationId).add(String.format("Vehicle stolen: %s", v.getDescription()));
                    } catch (Exception e) {}
                }
            }
        } else {
            for (VehiculeComponent v : vehicules) {
                v.resetIdleTime();
            }
        }
    }

    /**
     * Updates empty/full streak counters for one station and decides if a
     * redistribution should be scheduled.
     *
     * @param station   the station being processed
     * @param stationId identifier of the station
     * @param empty     current empty-streak value
     * @param full      current full-streak value
     * @return {@code true} if redistribution should be triggered based on this station
     */
    private boolean updateStreaksAndCheckRedistribution(Station<VehiculeComponent> station, int stationId, int empty, int full) {
        if (station.isEmpty()) {
            empty++;
        } else {
            empty = 0;
        }

        if (station.isFull()) {
            full++;
        } else {
            full = 0;
        }

        emptyStreaks.put(stationId, empty);
        fullStreaks.put(stationId, full);

        return empty >= 2 || full >= 2;
    }

    /**
     * Applies the current redistribution strategy and resets all streak
     * counters.
     */
    private void redistributeAndResetStreaks() {
        redistributionStrategy.redistribute(stations);
        for (Station<VehiculeComponent> s : stations) {
            emptyStreaks.put(s.getId(), 0);
            fullStreaks.put(s.getId(), 0);
        }
    }

    /**
     * Sets the redistribution strategy.
     */
    public void setRedistributionStrategy(RedistributionStrategy strategy) {
        if (strategy != null) this.redistributionStrategy = strategy;
    }

    /**
     * Prints a fleet summary (for quick manual demos). Purely informational.
     */
    public void printFleetSummary() {
        System.out.println("\n=== Control Center Summary ===");
        System.out.println("Stations: " + stations.size());
        System.out.println("Total vehicles: " + getTotalVehicles() + " / " + getTotalCapacity());
        System.out.println("\nActive services:");
        for (VehicleService service : services) {
            System.out.printf(" - %s%n", service.getServiceType());
        }
        
        System.out.println("\nStations:");
        for (Station<?> station : stations) {
            System.out.printf(" - Station %d: %d/%d occupied%n",
                    station.getId(),
                    station.getOccupiedSpaces(),
                    station.getCapacity());
        }
        System.out.println("==============================\n");
    }

    
}
