package fil.l3.coo.vehicule.velo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VeloClassiqueTest extends VeloTest {

    @Override
    protected Velo createVelo() {
        return new VeloClassique();
    }
    
    @Test
    public void testSetAvailability() {
        VeloClassique velo = new VeloClassique();
        
        assertTrue(velo.isAvailable());
        
        velo.setAvailable(false);
        assertFalse(velo.isAvailable());
        
        velo.setAvailable(true);
        assertTrue(velo.isAvailable());
    }
    
    @Test
    public void testPrice() {
        VeloClassique velo = new VeloClassique();
        assertEquals(1.0, velo.getPrice(), 0.001);
    }
    
    @Test
    public void testType() {
        VeloClassique velo = new VeloClassique();
        assertEquals("VeloClassique", velo.getType());
    }
    
    @Test
    public void testDescription() {
        VeloClassique velo = new VeloClassique();
        assertTrue(velo.getDescription().contains("VeloClassique"));
    }

}