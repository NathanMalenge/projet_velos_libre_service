package fil.l3.coo.Velo;

public class Velo {
    private static int nextId = 1;
    private String id;
    private boolean isAvailable;

    public Velo() {
        this.id = "VELO_" + nextId++;
        this.isAvailable = true;
    }

    public String getId() {
        return id;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
