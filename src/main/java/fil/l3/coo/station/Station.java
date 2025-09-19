package fil.l3.coo.station;

public class Station {

    private static int idCounter = 0;
    private int id;
    private int nbPlaces;

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

    public int getNbPlaces() {
        return nbPlaces;
    }

    public void setNbPlaces(int nbPlaces) {
        this.nbPlaces = nbPlaces;
    }

    public int getId() {
        return id;
    }

    public void occupyPlace() {
        if (nbPlaces > 0) {
            nbPlaces--;
        }
    }

    public void freePlace() {
        nbPlaces++;
    }   

    public boolean emplacementAvailable() {
        return nbPlaces > 0;
    }

    public String toString() {
        return "Station{" +
                "id=" + id +
                ", nbPlaces=" + nbPlaces +
                '}';
    }
}
