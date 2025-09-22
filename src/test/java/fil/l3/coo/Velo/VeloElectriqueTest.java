package fil.l3.coo.Velo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VeloElectriqueTest extends VeloTest {

    @Override
    protected Velo createVelo() {
        return new VeloElectrique();
    }

    @Override
    protected Velo createVeloWithAccessories() {
        return new VeloElectrique(true, true);
    }

    @Test
    public void testVeloElectriqueSpecifics() {
        VeloElectrique velo = new VeloElectrique();
        
        assertEquals("VeloElectrique", velo.getType());
        assertEquals(2.0, velo.getPrice(), 0.01);
        assertEquals(100, velo.getBatteryLevel());
        assertFalse(velo.isLowBattery());
        assertFalse(velo.hasBasket());
        assertFalse(velo.hasBaggage());
    }

    @Test
    public void testVeloElectriqueWithAccessories() {
        VeloElectrique velo = new VeloElectrique(true, true);
        
        assertTrue(velo.hasBasket());
        assertTrue(velo.hasBaggage());
        assertEquals(2.8, velo.getPrice(), 0.01);
    }

    @Test
    public void testBatteryManagement() {
        VeloElectrique velo = new VeloElectrique();
        
        assertEquals(100, velo.getBatteryLevel());
        assertFalse(velo.isLowBattery());
        
        velo.useBattery(30);
        assertEquals(70, velo.getBatteryLevel());
        assertFalse(velo.isLowBattery());
        
        velo.useBattery(60);
        assertEquals(10, velo.getBatteryLevel());
        assertTrue(velo.isLowBattery());
        
        velo.chargeBattery();
        assertEquals(100, velo.getBatteryLevel());
        assertFalse(velo.isLowBattery());
    }

    @Test
    public void testBatteryLimits() {
        VeloElectrique velo = new VeloElectrique();
        
        velo.useBattery(150);
        assertEquals(0, velo.getBatteryLevel());
        
        velo.setBatteryLevel(-10);
        assertEquals(0, velo.getBatteryLevel());
        
        velo.setBatteryLevel(150);
        assertEquals(100, velo.getBatteryLevel());
    }

    @Test
    public void testPriceWithLowBattery() {
        VeloElectrique veloNormal = new VeloElectrique(100, false, false);
        VeloElectrique veloLowBattery = new VeloElectrique(15, false, false);
        
        assertEquals(2.0, veloNormal.getPrice(), 0.01);
        assertEquals(1.0, veloLowBattery.getPrice(), 0.01);
        
        VeloElectrique veloLowWithAccessories = new VeloElectrique(10, true, true);
        assertEquals(1.4, veloLowWithAccessories.getPrice(), 0.01);
    }

    @Test
    public void testCustomBatteryLevel() {
        VeloElectrique velo = new VeloElectrique(50, false, false);
        
        assertEquals(50, velo.getBatteryLevel());
        assertEquals(2.0, velo.getPrice(), 0.01);
        assertFalse(velo.isLowBattery());
    }

    @Test
    public void testSetAccessories() {
        VeloElectrique velo = new VeloElectrique();
        
        assertEquals(2.0, velo.getPrice(), 0.01);
        
        velo.setBasket(true);
        assertTrue(velo.hasBasket());
        assertEquals(2.5, velo.getPrice(), 0.01);
        
        velo.setBaggage(true);
        assertTrue(velo.hasBaggage());
        assertEquals(2.8, velo.getPrice(), 0.01);
        
        velo.setBasket(false);
        assertFalse(velo.hasBasket());
        assertEquals(2.3, velo.getPrice(), 0.01);
    }

    @Test
    public void testToStringWithBattery() {
        VeloElectrique velo = new VeloElectrique(75, false, false);
        String veloString = velo.toString();
        
        assertTrue(veloString.contains("VeloElectrique"));
        assertTrue(veloString.contains("75%"));
        assertTrue(veloString.contains(velo.getId()));
    }
}