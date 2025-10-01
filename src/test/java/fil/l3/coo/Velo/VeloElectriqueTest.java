package fil.l3.coo.Velo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VeloElectriqueTest extends VeloTest {

    @Override
    protected Velo createVelo() {
        return new VeloElectrique();
    }

    @Test
    public void testPriceDifferenceWithClassique() {
        VeloElectrique veloElectrique = new VeloElectrique();
        VeloClassique veloClassique = new VeloClassique();
        
        assertTrue(veloElectrique.getPrice() > veloClassique.getPrice());
    }
}