package fil.l3.coo.vehicule.velo;

public class VeloElectrique extends Velo {
    private static final double BASE_PRICE = 2.0;

    /**
     * Creates a new electric bike without accessories.
     */
    public VeloElectrique() {
        super();
    }

    /**
     * Gets the rental price of this electric bike.
     * Base price is 2.0 (higher than classic bikes).
     * 
     * @return the rental price
     */
    @Override
    public double getPrice() {
        return BASE_PRICE;
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
