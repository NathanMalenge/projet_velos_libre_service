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

    private final List<Station<?>> stations;
    private final Map<Integer, List<String>> stationEvents;

    public ControlCenter() {
        this.stations = new ArrayList<>();
        this.stationEvents = new HashMap<>();
    }

    /**
     * Registers a station to be supervised. The control center subscribes to
     * station events and starts tracking its metrics.
     */
    public void registerStation(Station<?> station) {
        if (station != null && !stations.contains(station)) {
            stations.add(station);
            station.addObserver(this);
            stationEvents.putIfAbsent(station.getId(), new ArrayList<>());
        }
    }

    /**
     * Unregisters a station. Observers are detached and events history is
     * dropped.
     */
    public void unregisterStation(Station<?> station) {
        if (station != null && stations.remove(station)) {
            station.removeObserver(this);
            stationEvents.remove(station.getId());
        }
    }

    /**
     * @return a defensive copy of the registered stations list
     */
    public List<Station<?>> getStations() {
        return new ArrayList<>(stations);
    }

    /**
     * Computes the total number of vehicles currently parked in all stations.
     */
    public int getTotalVehicles() {
        return stations.stream()
                .mapToInt(Station::getOccupiedSpaces)
                .sum();
    }

    /**
     * Computes the total capacity across all stations.
     */
    public int getTotalCapacity() {
        return stations.stream()
                .mapToInt(Station::getCapacity)
                .sum();
    }

    /**
     * Returns the recorded events for a station, ordered chronologically.
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

    
}
