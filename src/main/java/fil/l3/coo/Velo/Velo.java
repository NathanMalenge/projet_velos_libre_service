package fil.l3.coo.Velo;

public abstract class Velo {
    private static int nextId = 1;
    private String id;
    private boolean isAvailable;
    protected boolean hasBasket;
    protected boolean hasBaggage;

    /**
     * Creates a new bike without accessories.
     * The bike is automatically assigned a unique ID and set as available.
     */
    public Velo() {
        this.id = "VELO_" + nextId++;
        this.isAvailable = true;
        this.hasBasket = false;
        this.hasBaggage = false;
    }

    /**
     * Creates a new bike with specified accessories.
     * The bike is automatically assigned a unique ID and set as available.
     * 
     * @param hasBasket true if the bike should have a basket
     * @param hasBaggage true if the bike should have baggage rack
     */
    public Velo(boolean hasBasket, boolean hasBaggage) {
        this.id = "VELO_" + nextId++;
        this.isAvailable = true;
        this.hasBasket = hasBasket;
        this.hasBaggage = hasBaggage;
    }

    /**
     * Gets the unique identifier of this bike.
     * 
     * @return the bike's unique ID
     */
    public String getId() {
        return id;
    }

    /**
     * Checks if the bike is currently available for rental.
     * 
     * @return true if the bike is available, false otherwise
     */
    public boolean isAvailable() {
        return isAvailable;
    }

    /**
     * Sets the availability status of the bike.
     * 
     * @param available true to make the bike available, false to make it unavailable
     */
    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    /**
     * Checks if the bike has a basket.
     * 
     * @return true if the bike has a basket, false otherwise
     */
    public boolean hasBasket() {
        return hasBasket;
    }

    /**
     * Sets whether the bike has a basket.
     * 
     * @param hasBasket true to add a basket, false to remove it
     */
    public void setBasket(boolean hasBasket) {
        this.hasBasket = hasBasket;
    }

    /**
     * Checks if the bike has a baggage rack.
     * 
     * @return true if the bike has a baggage rack, false otherwise
     */
    public boolean hasBaggage() {
        return hasBaggage;
    }

    /**
     * Sets whether the bike has a baggage rack.
     * 
     * @param hasBaggage true to add a baggage rack, false to remove it
     */
    public void setBaggage(boolean hasBaggage) {
        this.hasBaggage = hasBaggage;
    }

    /**
     * Calculates the additional price for accessories.
     * Basket adds 0.5 to the price, baggage rack adds 0.3.
     * 
     * @return the total price of accessories
     */
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

    /**
     * Gets the rental price of this bike.
     * 
     * @return the total price including base price and accessories
     */
    public abstract double getPrice();
    
    /**
     * Gets the type of this bike.
     * 
     * @return the bike type as a string
     */
    public abstract String getType();
    
    public String toString() {
        return getType() + "{" +
                "id='" + id + '\'' +
                ", isAvailable=" + isAvailable +
                ", price=" + getPrice() +
                '}';
    }
}
