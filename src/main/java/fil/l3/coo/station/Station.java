package fil.l3.coo.station;

public class Station {

    private static int idCounter = 0;
    private int id;
    private final int capacity;
    private int occupiedSpaces;

    /**
     * Creates a new station with the specified capacity.
     * The capacity is automatically constrained between 10 and 20.
     * The station starts empty with no bikes parked.
     * 
     * @param capacity the desired capacity (will be clamped between 10 and 20)
     */
    public Station(int capacity) {
        this.id = idCounter++;
        this.capacity = Math.max(10, Math.min(20, capacity));
        this.occupiedSpaces = 0;
    }

    /**
     * Gets the unique identifier of this station.
     * 
     * @return the station's unique ID
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the total capacity of this station.
     * 
     * @return the maximum number of bikes this station can hold
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Gets the number of bikes currently parked in this station.
     * 
     * @return the number of occupied spaces
     */
    public int getOccupiedSpaces() {
        return occupiedSpaces;
    }

    /**
     * Gets the number of available spaces in this station.
     * 
     * @return the number of free spaces for parking bikes
     */
    public int getAvailableSpaces() {
        return capacity - occupiedSpaces;
    }

    /**
     * Checks if the station has available spaces for parking a bike.
     * 
     * @return true if at least one space is available, false otherwise
     */
    public boolean hasAvailableSpace() {
        return occupiedSpaces < capacity;
    }

    /**
     * Checks if the station has bikes available for removal.
     * 
     * @return true if at least one bike is parked, false otherwise
     */
    public boolean hasBikes() {
        return occupiedSpaces > 0;
    }

    /**
     * Checks if the station is empty (no bikes parked).
     * 
     * @return true if no bikes are parked, false otherwise
     */
    public boolean isEmpty() {
        return occupiedSpaces == 0;
    }

    /**
     * Checks if the station is full (no available spaces).
     * 
     * @return true if the station is at full capacity, false otherwise
     */
    public boolean isFull() {
        return occupiedSpaces == capacity;
    }

    /**
     * Parks a bike in this station.
     * Only succeeds if there is an available space.
     * 
     * @return true if the bike was successfully parked, false if station is full
     */
    public boolean parkBike() {
        if (hasAvailableSpace()) {
            occupiedSpaces++;
            return true;
        }
        return false;
    }

    /**
     * Removes a bike from this station.
     * Only succeeds if there is at least one bike parked.
     * 
     * @return true if a bike was successfully removed, false if station is empty
     */
    public boolean removeBike() {
        if (hasBikes()) {
            occupiedSpaces--;
            return true;
        }
        return false;
    }

    /**
     * Returns a string representation of this station.
     * 
     * @return a string containing the station's ID, occupied spaces, and capacity
     */
    @Override
    public String toString() {
        return "Station{" +
                "id=" + id +
                ", occupiedSpaces=" + occupiedSpaces +
                ", capacity=" + capacity +
                '}';
    }
}
