package fil.l3.coo.Velo;

public abstract class Velo {
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

    // Méthodes abstraites à implémenter par les sous-classes
    public abstract double getPrice();
    public abstract String getType();
    
    @Override
    public String toString() {
        return getType() + "{" +
                "id='" + id + '\'' +
                ", isAvailable=" + isAvailable +
                ", price=" + getPrice() +
                '}';
    }
}
