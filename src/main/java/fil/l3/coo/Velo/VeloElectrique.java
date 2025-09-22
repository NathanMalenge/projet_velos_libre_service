package fil.l3.coo.Velo;

public class VeloElectrique extends Velo {
    private static final double BASE_PRICE = 2.0;
    private int batteryLevel;

    public VeloElectrique() {
        super();
        this.batteryLevel = 100;
    }

    public VeloElectrique(boolean hasBasket, boolean hasBaggage) {
        super(hasBasket, hasBaggage);
        this.batteryLevel = 100;
    }

    public VeloElectrique(int batteryLevel, boolean hasBasket, boolean hasBaggage) {
        super(hasBasket, hasBaggage);
        this.batteryLevel = Math.max(0, Math.min(100, batteryLevel));
    }

    @Override
    public double getPrice() {
        double price = BASE_PRICE + getAccessoriesPrice();
        
        if (batteryLevel < 20) {
            price *= 0.5;
        }
        
        return price;
    }

    @Override
    public String getType() {
        return "VeloElectrique";
    }

    public int getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(int batteryLevel) {
        this.batteryLevel = Math.max(0, Math.min(100, batteryLevel));
    }

    public void chargeBattery() {
        this.batteryLevel = 100;
    }

    public void useBattery(int usage) {
        this.batteryLevel = Math.max(0, this.batteryLevel - usage);
    }

    public boolean isLowBattery() {
        return batteryLevel < 20;
    }

    @Override
    public String toString() {
        return getType() + "{" +
                "id='" + getId() + '\'' +
                ", isAvailable=" + isAvailable() +
                ", price=" + getPrice() +
                ", batteryLevel=" + batteryLevel + "%" +
                '}';
    }
}