package fil.l3.coo.Velo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VeloClassiqueTest extends VeloTest {

    @Override
    protected Velo createVelo() {
        return new VeloClassique();
    }

    @Override
    protected Velo createVeloWithAccessories() {
        return new VeloClassique(true, true);
    }

    @Test
    public void testVeloClassiqueSpecifics() {
        VeloClassique velo = new VeloClassique();
        
        assertEquals("VeloClassique", velo.getType());
        assertEquals(1.0, velo.getPrice(), 0.01);
        assertFalse(velo.hasBasket());
        assertFalse(velo.hasBaggage());
    }

    @Test
    public void testVeloClassiqueWithBasket() {
        VeloClassique velo = new VeloClassique(true, false);
        
        assertTrue(velo.hasBasket());
        assertFalse(velo.hasBaggage());
        assertEquals(1.5, velo.getPrice(), 0.01);
    }

    @Test
    public void testVeloClassiqueWithBaggage() {
        VeloClassique velo = new VeloClassique(false, true);
        
        assertFalse(velo.hasBasket());
        assertTrue(velo.hasBaggage());
        assertEquals(1.3, velo.getPrice(), 0.01);
    }

    @Test
    public void testVeloClassiqueWithAllAccessories() {
        VeloClassique velo = new VeloClassique(true, true);
        
        assertTrue(velo.hasBasket());
        assertTrue(velo.hasBaggage());
        assertEquals(1.8, velo.getPrice(), 0.01);
    }

    @Test
    public void testSetAccessories() {
        VeloClassique velo = new VeloClassique();
        
        assertEquals(1.0, velo.getPrice(), 0.01);
        
        velo.setBasket(true);
        assertTrue(velo.hasBasket());
        assertEquals(1.5, velo.getPrice(), 0.01);
        
        velo.setBaggage(true);
        assertTrue(velo.hasBaggage());
        assertEquals(1.8, velo.getPrice(), 0.01);
        
        velo.setBasket(false);
        assertFalse(velo.hasBasket());
        assertEquals(1.3, velo.getPrice(), 0.01);
    }
}