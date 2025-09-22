package fil.l3.coo.Velo;

public class VeloElectrique extends Velo {
    private static final double BASE_PRICE = 2.0;

    /**
     * Creates a new electric bike without accessories.
     */
    public VeloElectrique() {
        super();
    }

    /**
     * Creates a new electric bike with specified accessories.
     * 
     * @param hasBasket true if the bike should have a basket
     * @param hasBaggage true if the bike should have a baggage rack
     */
    public VeloElectrique(boolean hasBasket, boolean hasBaggage) {
        super(hasBasket, hasBaggage);
    }

    /**
     * Gets the rental price of this electric bike.
     * Base price is 2.0 (higher than classic bikes), plus additional cost for accessories.
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
     * @return "VeloElectrique"
     */
    @Override
    public String getType() {
        return "VeloElectrique";
    }
}