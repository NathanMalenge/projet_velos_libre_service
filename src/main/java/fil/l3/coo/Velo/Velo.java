package fil.l3.coo.Velo;

public abstract class Velo implements VeloComponent {

    private boolean isAvailable = true;

    /**
     * Creates a new bike.
     * The bike is set as available by default.
     */
    public Velo() {
    }

    /**
     * Checks if the bike is currently available for rental.
     * 
     * @return true if the bike is available, false otherwise
     */
    @Override
    public boolean isAvailable() {
        return isAvailable;
    }

    /**
     * Sets the availability status of the bike.
     * 
     * @param available true to make the bike available, false to make it unavailable
     */
    @Override
    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    /**
     * Gets the description of this bike.
     * Default implementation returns the type.
     * 
     * @return the bike description
     */
    @Override
    public String getDescription() {
        return getType();
    }

    /**
     * Gets the rental price of this bike.
     * 
     * @return the total price including base price and accessories
     */
    @Override
    public abstract double getPrice();
    
    /**
     * Gets the type of this bike.
     * 
     * @return the bike type as a string
     */
    @Override
    public abstract String getType();
    
    @Override
    public String toString() {
        return getDescription() + "{" +
                "isAvailable=" + isAvailable +
                ", price=" + getPrice() +
                '}';
    }
}
