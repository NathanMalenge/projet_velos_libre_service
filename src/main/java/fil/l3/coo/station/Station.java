package fil.l3.coo.station;

public class Station {

    private static int idCounter = 0;
    private int id;
    private int nbPlaces;

    /**
     * Creates a new station with the specified number of places.
     * The number of places is automatically constrained between 10 and 20.
     * The station is automatically assigned a unique ID.
     * 
     * @param nbPlaces the desired number of places (between 10 and 20)
     */
    public Station(int nbPlaces) {
        this.id = idCounter++;
        this.nbPlaces = nbPlaces;
        if (nbPlaces < 10) {
            this.nbPlaces = 10;
        }
        if (nbPlaces > 20) {
            this.nbPlaces = 20;
        }
    }

    /**
     * Gets the current number of available places in the station.
     * 
     * @return the number of available places
     */
    public int getNbPlaces() {
        return nbPlaces;
    }

    /**
     * Sets the number of available places in the station.
     * 
     * @param nbPlaces the new number of available places
     */
    public void setNbPlaces(int nbPlaces) {
        this.nbPlaces = nbPlaces;
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
     * Occupies one place in the station (when a bike is parked).
     * If no places are available, this method has no effect.
     */
    public void occupyPlace() {
        if (nbPlaces > 0) {
            nbPlaces--;
        }
    }

    /**
     * Frees one place in the station (when a bike is removed).
     */
    public void freePlace() {
        nbPlaces++;
    }   

    /**
     * Checks if there are available places in the station.
     * 
     * @return true if at least one place is available, false otherwise
     */
    public boolean emplacementAvailable() {
        return nbPlaces > 0;
    }

    /**
     * Returns a string representation of this station.
     * 
     * @return a string containing the station's ID and number of places
     */
    public String toString() {
        return "Station{" +
                "id=" + id +
                ", nbPlaces=" + nbPlaces +
                '}';
    }
}
