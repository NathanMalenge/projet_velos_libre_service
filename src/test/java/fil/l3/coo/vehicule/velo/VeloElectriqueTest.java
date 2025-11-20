package fil.l3.coo.vehicule.velo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VeloElectriqueTest extends VeloTest {

    @Override
    protected Velo createVelo() {
        return new VeloElectrique();
    }
    
    @Test
    public void testSetAvailability() {
        VeloElectrique velo = new VeloElectrique();
        
        assertTrue(velo.isAvailable());
        
        velo.setAvailable(false);
        assertFalse(velo.isAvailable());
        
        velo.setAvailable(true);
        assertTrue(velo.isAvailable());
    }
    
    @Test
    public void testPrice() {
        VeloElectrique velo = new VeloElectrique();
        assertEquals(2.0, velo.getPrice(), 0.001);
    }
    
    @Test
    public void testType() {
        VeloElectrique velo = new VeloElectrique();
        assertEquals("VeloElectrique", velo.getType());
    }
    
    @Test
    public void testDescription() {
        VeloElectrique velo = new VeloElectrique();
        assertTrue(velo.getDescription().contains("VeloElectrique"));
    }
    
    @Test
    public void testPriceIsHigherThanClassique() {
        VeloElectrique electrique = new VeloElectrique();
        VeloClassique classique = new VeloClassique();
        
        assertTrue(electrique.getPrice() > classique.getPrice());
    }
}