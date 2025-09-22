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
    public void testPriceDifferenceWithClassique() {
        VeloElectrique veloElectrique = new VeloElectrique();
        VeloClassique veloClassique = new VeloClassique();
        
        assertTrue(veloElectrique.getPrice() > veloClassique.getPrice());
    }
}