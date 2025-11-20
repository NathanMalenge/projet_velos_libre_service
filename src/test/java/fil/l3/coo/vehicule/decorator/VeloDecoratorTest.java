package fil.l3.coo.vehicule.decorator;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import fil.l3.coo.vehicule.VehiculeComponent;
import fil.l3.coo.vehicule.velo.VeloClassique;

/**
 * Tests du Pattern Decorator pour les véhicules.
 * On teste uniquement les comportements essentiels du décorateur :
 * - Un décorateur simple 
 * - L'empilement de décorateurs fonctionne correctement
 * - Les états (availability) se propagent à travers les décorateurs
 * - Le type de base est préservé malgré les décorateurs
 */
public class VeloDecoratorTest {
    
    /**
     * Factory method to create the base vehicle for decorator tests.
     * Can be overridden to test decorators with other vehicle types.
     */
    protected VehiculeComponent createBaseVehicule() {
        return new VeloClassique();
    }
    
    @Test
    public void testSingleDecorator() {
        VehiculeComponent velo = createBaseVehicule();
        velo = new BasketDecorator(velo);
        
        assertEquals(1.5, velo.getPrice(), 0.01); // 1.0 + 0.5
        assertEquals("VeloClassique + Basket", velo.getDescription());
        assertTrue(velo.isAvailable());
    }
    
    @Test
    public void testMultipleDecorators() {
        VehiculeComponent velo = createBaseVehicule();
        velo = new BasketDecorator(velo);
        velo = new BaggageDecorator(velo);
        
        assertEquals(1.8, velo.getPrice(), 0.01); // 1.0 + 0.5 + 0.3
        assertEquals("VeloClassique + Basket + Baggage", velo.getDescription());
    }
    
    @Test
    public void testAvailabilityPropagation() {
        VehiculeComponent velo = createBaseVehicule();
        velo = new BasketDecorator(velo);
        velo = new BaggageDecorator(velo);
        
        assertTrue(velo.isAvailable());
        
        velo.setAvailable(false);
        assertFalse(velo.isAvailable());
        
        velo.setAvailable(true);
        assertTrue(velo.isAvailable());
    }
    
}
