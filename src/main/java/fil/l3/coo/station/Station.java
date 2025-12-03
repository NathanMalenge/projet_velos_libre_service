package fil.l3.coo.station;

import java.util.ArrayList;
import java.util.List;
import fil.l3.coo.vehicule.VehiculeComponent;
import fil.l3.coo.station.exceptions.VehiculeNotFoundException;
import fil.l3.coo.station.exceptions.NullVehiculeException;
import fil.l3.coo.station.exceptions.StationFullException;

/**
 * Generic station that can park and manage vehicles of a specific type.
 * Type parameter T must extend VehiculeComponent to ensure type safety.
 * 
 * @param <T> the type of vehicle this station can handle
 */
public class Station<T extends VehiculeComponent> {

    private static int nextId = 1;

    private final int id;
    private final int capacity;
    private final List<T> parkedVehicules;
    private final List<StationObserver> observers;

    /**
     * Creates a new station with the specified capacity.
     * The capacity is automatically constrained between 10 and 20.
     * The station starts empty with no vehicles parked.
     * 
     * @param capacity the desired capacity (will be clamped between 10 and 20)
     */
    public Station(int capacity) {
        this.id = nextId++;
        this.capacity = Math.max(10, Math.min(20, capacity));
        this.parkedVehicules = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    /**
     * Gets the unique identifier of this station.
     * 
     * @return the station's ID
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the number of vehicles currently parked in this station.
     * 
     * @return the number of parked vehicles
     */
    public int getOccupiedSpaces() {
        return parkedVehicules.size();
    }

    /**
     * Gets the number of available spaces in this station.
     * 
     * @return the number of free spaces for parking vehicles
     */
    public int getAvailableSpaces() {
        return capacity - parkedVehicules.size();
    }

    /**
     * Gets this station's total capacity.
     * 
     * @return the capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Registers an observer to receive station events.
     * 
     * @param observer observer to add
     */
    public void addObserver(StationObserver observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }

    /**
     * Unregisters an observer.
     * 
     * @param observer observer to remove
     */
    public void removeObserver(StationObserver observer) {
        observers.remove(observer);
    }

    private void notifyVehicleParked(T vehicule) {
        for (StationObserver observer : observers) {
            observer.onVehicleParked(this, vehicule);
        }
    }

    private void notifyVehicleRemoved(T vehicule) {
        for (StationObserver observer : observers) {
            observer.onVehicleRemoved(this, vehicule);
        }
    }

    /**
     * Checks if the station has available spaces for parking a vehicle.
     * 
     * @return true if at least one space is available, false otherwise
     */
    public boolean hasAvailableSpace() {
        return parkedVehicules.size() < capacity;
    }

    /**
     * Checks if the station has vehicles available for removal.
     * 
     * @return true if at least one vehicle is parked, false otherwise
     */
    public boolean hasAvailableVehicules() {
        return !parkedVehicules.isEmpty();
    }

    /**
     * Checks if the station is empty (no vehicles parked).
     * 
     * @return true if no vehicles are parked, false otherwise
     */
    public boolean isEmpty() {
        return parkedVehicules.isEmpty();
    }

    /**
     * Checks if the station is full (no available spaces).
     * 
     * @return true if the station is at full capacity, false otherwise
     */
    public boolean isFull() {
        return parkedVehicules.size() == capacity;
    }

    /**
     * Parks a vehicle in this station.
     * Only succeeds if there is an available space and the vehicle is not null.
     * 
     * @param vehicule the vehicle to park
     * @throws NullVehiculeException if the vehicle is null
     * @throws StationFullException if the station is full
     */
    public void parkVehicule(T vehicule) throws NullVehiculeException, StationFullException {
        if (vehicule == null) {
            throw new NullVehiculeException();
        }
        
        if (!hasAvailableSpace()) {
            throw new StationFullException("La station est pleine (capacité: " + capacity + ")");
        }
        
        parkedVehicules.add(vehicule);
        vehicule.setAvailable(true);
        notifyVehicleParked(vehicule);
    }

    /**
     * Removes a specific vehicle from this station.
     * The vehicle must exist in the station to be removed.
     * 
     * @param vehicule the vehicle to remove
     * @return the removed vehicle if it was found and removed
     * @throws NullVehiculeException if the vehicle is null
     * @throws VehiculeNotFoundException if the vehicle doesn't exist in this station
     */
    public T removeVehicule(T vehicule) throws NullVehiculeException, VehiculeNotFoundException {
        if (vehicule == null) {
            throw new NullVehiculeException();
        }
        
        if (!parkedVehicules.contains(vehicule)) {
            throw new VehiculeNotFoundException("Ce véhicule n'est pas dans cette station");
        }
        
        parkedVehicules.remove(vehicule);
        vehicule.setAvailable(false);
        notifyVehicleRemoved(vehicule);
        return vehicule;
    }

    /**
     * Gets all parked vehicles in this station.
     * 
     * @return a copy of the list of parked vehicles
     */
    public List<T> getParkedVehicules() {
        return new ArrayList<>(parkedVehicules);
    }

    /**
     * Returns a string representation of this station.
     * 
     * @return a string containing the occupied spaces and capacity
     */
    @Override
    public String toString() {
        return "Station{" +
            "id=" + id +
            ", occupiedSpaces=" + parkedVehicules.size() +
            ", capacity=" + capacity +
            '}';
    }
}
