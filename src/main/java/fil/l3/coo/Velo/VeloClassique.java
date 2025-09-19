package fil.l3.coo.Velo;

public class VeloClassique extends Velo {
    private static final double BASE_PRICE = 1.0;

    public VeloClassique() {
        super();
    }

    public VeloClassique(boolean hasBasket, boolean hasBaggage) {
        super(hasBasket, hasBaggage);
    }

    @Override
    public double getPrice() {
        return BASE_PRICE + getAccessoriesPrice();
    }

    @Override
    public String getType() {
        return "VeloClassique";
    }
}