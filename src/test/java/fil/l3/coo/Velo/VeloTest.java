package fil.l3.coo.Velo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public abstract class VeloTest {


    protected abstract Velo createVelo();
    protected abstract Velo createVeloWithAccessories();

    @Test
    public void testUniqueIds() {
        Velo velo1 = createVelo();
        Velo velo2 = createVelo();
        Velo velo3 = createVelo();
        
        assertNotEquals(velo1.getId(), velo2.getId());
        assertNotEquals(velo1.getId(), velo3.getId());
        assertNotEquals(velo2.getId(), velo3.getId());
        
        assertTrue(velo1.getId().matches("VELO_\\d+"));
        assertTrue(velo2.getId().matches("VELO_\\d+"));
        assertTrue(velo3.getId().matches("VELO_\\d+"));
    }

    @Test
    public void testAvailability() {
        Velo velo = createVelo();
        
        assertTrue(velo.isAvailable());
        
        velo.setAvailable(false);
        assertFalse(velo.isAvailable());
        
        velo.setAvailable(true);
        assertTrue(velo.isAvailable());
    }

    @Test
    public void testVeloStateChanges() {
        Velo velo = createVelo();
        String originalId = velo.getId();
        double originalPrice = velo.getPrice();
        
        velo.setAvailable(false);
        velo.setAvailable(true);
        velo.setAvailable(false);
        velo.setAvailable(true);
        
        assertEquals(originalId, velo.getId());
        
        assertEquals(originalPrice, velo.getPrice(), 0.01);
        
        assertTrue(velo.isAvailable());
    }
}