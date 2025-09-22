package fil.l3.coo.Velo;

public class VeloClassique extends Velo {
    private static final double BASE_PRICE = 1.0;

    /**
     * Creates a new classic bike without accessories.
     */
    public VeloClassique() {
        super();
    }

    /**
     * Creates a new classic bike with specified accessories.
     * 
     * @param hasBasket true if the bike should have a basket
     * @param hasBaggage true if the bike should have a baggage rack
     */
    public VeloClassique(boolean hasBasket, boolean hasBaggage) {
        super(hasBasket, hasBaggage);
    }

    /**
     * Gets the rental price of this classic bike.
     * Base price is 1.0, plus additional cost for accessories.
     * 
     * @return the total rental price
     */
    @Override
    public double getPrice() {
        return BASE_PRICE + getAccessoriesPrice();
    }

    /**
     * Gets the type of this bike.
     * 
     * @return "VeloClassique"
     */
    @Override
    public String getType() {
        return "VeloClassique";
    }
}