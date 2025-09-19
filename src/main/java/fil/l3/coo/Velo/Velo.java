package fil.l3.coo.Velo;

public abstract class Velo {
    private static int nextId = 1;
    private String id;
    private boolean isAvailable;
    protected boolean hasBasket;
    protected boolean hasBaggage;

    public Velo() {
        this.id = "VELO_" + nextId++;
        this.isAvailable = true;
        this.hasBasket = false;
        this.hasBaggage = false;
    }

    public Velo(boolean hasBasket, boolean hasBaggage) {
        this.id = "VELO_" + nextId++;
        this.isAvailable = true;
        this.hasBasket = hasBasket;
        this.hasBaggage = hasBaggage;
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

    public boolean hasBasket() {
        return hasBasket;
    }

    public void setBasket(boolean hasBasket) {
        this.hasBasket = hasBasket;
    }

    public boolean hasBaggage() {
        return hasBaggage;
    }

    public void setBaggage(boolean hasBaggage) {
        this.hasBaggage = hasBaggage;
    }

    protected double getAccessoriesPrice() {
        double price = 0;
        if (hasBasket) {
            price += 0.5;
        }
        if (hasBaggage) {
            price += 0.3;
        }
        return price;
    }

    public abstract double getPrice();
    public abstract String getType();
    
    public String toString() {
        return getType() + "{" +
                "id='" + id + '\'' +
                ", isAvailable=" + isAvailable +
                ", price=" + getPrice() +
                '}';
    }
}
