package fil.l3.coo.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fil.l3.coo.station.Station;
import fil.l3.coo.station.StationObserver;
import fil.l3.coo.vehicule.VehiculeComponent;

/**
 * Supervision component that observes all registered stations and keeps a
 * global view on the fleet. It receives notifications for every vehicle
 * deposit/removal and exposes helper methods for statistics and auditing. This
 * class will be extended later with redistribution/maintenance logic.
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
     * Enregistre un nouveau service métier (réparateur, peintre, etc.).
     * 
     * @param service le service à enregistrer
     */
    public void registerService(VehicleService service) {
        if (service != null && !services.contains(service)) {
            services.add(service);
        }
    }
    
    /**
     * Récupère tous les services enregistrés.
     * 
     * @return la liste des services
     */
    public List<VehicleService> getServices() {
        return new ArrayList<>(services);
    }
    
    /**
     * Récupère un service par son type.
     * 
     * @param serviceType le type de service (ex: "REPAIR", "PAINT")
     * @return le service, ou null si non trouvé
     */
    public VehicleService getService(String serviceType) {
        return services.stream()
                .filter(s -> s.getServiceType().equals(serviceType))
                .findFirst()
                .orElse(null);
    }

    /**
     * Registers a station to be supervised. The control center subscribes to
     * station events and starts tracking its metrics.
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
     * Unregisters a station. Observers are detached and events history is
     * dropped.
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
        if (vehicule.needsMaintenance() || "EN_MAINTENANCE".equals(vehicule.getStateName())) {
            VehicleService repairService = getService("REPAIR");
            if (repairService != null) {
                boolean serviced = repairService.service(station, vehicule);
                
                if (serviced) {
                    String maintenanceEvent = String.format("Vehicle repaired: %s", vehicule.getDescription());
                    stationEvents.get(stationId).add(maintenanceEvent);
                }
            }
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

            if (s.isEmpty()) {
                empty++;
            } else {
                empty = 0;
            }

            if (s.isFull()) {
                full++;
            } else {
                full = 0;
            }

            emptyStreaks.put(id, empty);
            fullStreaks.put(id, full);

            if (empty >= 2 || full >= 2) {
                needRedistribution = true;
            }
        }

        if (needRedistribution && redistributionStrategy != null) {
            redistributionStrategy.redistribute(stations);
            for (Station<VehiculeComponent> s : stations) {
                emptyStreaks.put(s.getId(), 0);
                fullStreaks.put(s.getId(), 0);
            }
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
