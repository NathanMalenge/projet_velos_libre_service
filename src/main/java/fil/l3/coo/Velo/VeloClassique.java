package fil.l3.coo.Velo;

public class VeloClassique extends Velo {
    private static final double BASE_PRICE = 1.0;
    private boolean hasBasket;
    private boolean hasBaggage;

    public VeloClassique() {
        super();
        this.hasBasket = false;
        this.hasBaggage = false;
    }

    public VeloClassique(boolean hasBasket, boolean hasBaggage) {
        super();
        this.hasBasket = hasBasket;
        this.hasBaggage = hasBaggage;
    }

    @Override
    public double getPrice() {
        double price = BASE_PRICE;
        if (hasBasket) {
            price += 0.5;
        }
        if (hasBaggage) {
            price += 0.3;
        }
        return price;
    }

    @Override
    public String getType() {
        return "VeloClassique";
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
}