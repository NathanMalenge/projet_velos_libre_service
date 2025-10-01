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
     * Gets the rental price of this classic bike.
     * Base price is 1.0.
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
     * @return "VeloClassique"
     */
    @Override
    public String getType() {
        return "VeloClassique";
    }
}