package fil.l3.coo.station;

import java.util.ArrayList;
import java.util.List;
import fil.l3.coo.vehicule.VehiculeComponent;
import fil.l3.coo.station.exceptions.BikeNotFoundException;
import fil.l3.coo.station.exceptions.NullBikeException;
import fil.l3.coo.station.exceptions.StationFullException;

public class Station {

    private final int capacity;
    private List<VehiculeComponent> parkedBikes;

    /**
     * Creates a new station with the specified capacity.
     * The capacity is automatically constrained between 10 and 20.
     * The station starts empty with no bikes parked.
     * 
     * @param capacity the desired capacity (will be clamped between 10 and 20)
     */
    public Station(int capacity) {
        this.capacity = Math.max(10, Math.min(20, capacity));
        this.parkedBikes = new ArrayList<>();
    }

    /**
     * Gets the number of bikes currently parked in this station.
     * 
     * @return the number of parked bikes
     */
    public int getOccupiedSpaces() {
        return parkedBikes.size();
    }

    /**
     * Gets the number of available spaces in this station.
     * 
     * @return the number of free spaces for parking bikes
     */
    public int getAvailableSpaces() {
        return capacity - parkedBikes.size();
    }

    /**
     * Checks if the station has available spaces for parking a bike.
     * 
     * @return true if at least one space is available, false otherwise
     */
    public boolean hasAvailableSpace() {
        return parkedBikes.size() < capacity;
    }

    public boolean hasAvailableBikes(){
        return !parkedBikes.isEmpty();
    }

    /**
     * Checks if the station has bikes available for removal.
     * 
     * @return true if at least one bike is parked, false otherwise
     */
    public boolean hasBikes() {
        return !parkedBikes.isEmpty();
    }

    /**
     * Checks if the station is empty (no bikes parked).
     * 
     * @return true if no bikes are parked, false otherwise
     */
    public boolean isEmpty() {
        return parkedBikes.isEmpty();
    }

    /**
     * Checks if the station is full (no available spaces).
     * 
     * @return true if the station is at full capacity, false otherwise
     */
    public boolean isFull() {
        return parkedBikes.size() == capacity;
    }

    /**
     * Parks a bike in this station.
     * Only succeeds if there is an available space and the bike is not null.
     * 
     * @param velo the bike to park
     * @throws NullBikeException if the bike is null
     * @throws StationFullException if the station is full
     */
    public void parkBike(VehiculeComponent velo) throws NullBikeException, StationFullException {
        if (velo == null) {
            throw new NullBikeException();
        }
        
        if (!hasAvailableSpace()) {
            throw new StationFullException("La station est pleine (capacité: " + capacity + ")");
        }
        
        parkedBikes.add(velo);
        velo.setAvailable(true);
    }

    /**
     * Removes a specific bike from this station.
     * The bike must exist in the station to be removed.
     * 
     * @param velo the bike to remove
     * @return the removed bike if it was found and removed
     * @throws NullBikeException if the bike is null
     * @throws BikeNotFoundException if the bike doesn't exist in this station
     */
    public VehiculeComponent removeBike(VehiculeComponent velo) throws NullBikeException, BikeNotFoundException { //TODO changer la maniere dont on choisit les velo a rent
        if (velo == null) {
            throw new NullBikeException();
        }
        
        if (!parkedBikes.contains(velo)) {
            throw new BikeNotFoundException("Ce vélo n'est pas dans cette station");
        }
        
        parkedBikes.remove(velo);
        velo.setAvailable(false);
        return velo;
    }

    /**
     * Gets all parked bikes in this station.
     * 
     * @return a copy of the list of parked bikes
     */
    public List<VehiculeComponent> getParkedBikes() {
        return new ArrayList<>(parkedBikes);
    }

    /**
     * Returns a string representation of this station.
     * 
     * @return a string containing the occupied spaces and capacity
     */
    @Override
    public String toString() {
        return "Station{" +
                "occupiedSpaces=" + parkedBikes.size() +
                ", capacity=" + capacity +
                '}';
    }
}
