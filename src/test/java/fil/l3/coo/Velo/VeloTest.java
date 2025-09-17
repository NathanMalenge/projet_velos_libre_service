package fil.l3.coo.Velo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public abstract class VeloTest {

    // Factory method à implémenter par les sous-classes
    protected abstract Velo createVelo();
    protected abstract Velo createVeloWithAccessories();

    @Test
    public void testVeloCreation() {
        Velo velo = createVelo();
        
        assertNotNull(velo);
        assertNotNull(velo.getId());
        assertTrue(velo.getId().startsWith("VELO_"));
        assertTrue(velo.isAvailable());
        assertTrue(velo.getPrice() > 0);
        assertNotNull(velo.getType());
    }

    @Test
    public void testUniqueIds() {
        Velo velo1 = createVelo();
        Velo velo2 = createVelo();
        Velo velo3 = createVelo();
        
        // Vérifier que tous les IDs sont différents
        assertNotEquals(velo1.getId(), velo2.getId());
        assertNotEquals(velo1.getId(), velo3.getId());
        assertNotEquals(velo2.getId(), velo3.getId());
        
        // Vérifier le format des IDs
        assertTrue(velo1.getId().matches("VELO_\\d+"));
        assertTrue(velo2.getId().matches("VELO_\\d+"));
        assertTrue(velo3.getId().matches("VELO_\\d+"));
    }

    @Test
    public void testIdSequence() {
        Velo velo1 = createVelo();
        Velo velo2 = createVelo();
        
        // Extraire les numéros des IDs
        int num1 = Integer.parseInt(velo1.getId().substring(5)); // "VELO_".length() = 5
        int num2 = Integer.parseInt(velo2.getId().substring(5));
        
        // Vérifier que les numéros sont séquentiels
        assertEquals(num1 + 1, num2);
    }

    @Test
    public void testAvailability() {
        Velo velo = createVelo();
        
        // Vélo disponible par défaut
        assertTrue(velo.isAvailable());
        
        // Rendre indisponible
        velo.setAvailable(false);
        assertFalse(velo.isAvailable());
        
        // Rendre disponible à nouveau
        velo.setAvailable(true);
        assertTrue(velo.isAvailable());
    }

    @Test
    public void testMultipleVelos() {
        // Créer plusieurs vélos et vérifier leurs propriétés
        Velo[] velos = new Velo[5];
        
        for (int i = 0; i < velos.length; i++) {
            velos[i] = createVelo();
            assertTrue(velos[i].isAvailable());
            assertNotNull(velos[i].getId());
            assertTrue(velos[i].getPrice() > 0);
        }
        
        // Vérifier que tous les IDs sont uniques
        for (int i = 0; i < velos.length; i++) {
            for (int j = i + 1; j < velos.length; j++) {
                assertNotEquals(velos[i].getId(), velos[j].getId());
            }
        }
    }

    @Test
    public void testVeloStateChanges() {
        Velo velo = createVelo();
        String originalId = velo.getId();
        double originalPrice = velo.getPrice();
        
        // Changer l'état plusieurs fois
        velo.setAvailable(false);
        velo.setAvailable(true);
        velo.setAvailable(false);
        velo.setAvailable(true);
        
        // L'ID ne doit pas changer
        assertEquals(originalId, velo.getId());
        
        // Le prix de base ne doit pas changer
        assertEquals(originalPrice, velo.getPrice(), 0.01);
        
        // L'état final doit être correct
        assertTrue(velo.isAvailable());
    }

    @Test
    public void testToString() {
        Velo velo = createVelo();
        String veloString = velo.toString();
        
        assertNotNull(veloString);
        assertTrue(veloString.contains(velo.getId()));
        assertTrue(veloString.contains(velo.getType()));
        assertTrue(veloString.contains(String.valueOf(velo.getPrice())));
    }

    @Test
    public void testPriceWithAccessories() {
        Velo basicVelo = createVelo();
        Velo veloWithAccessories = createVeloWithAccessories();
        
        // Le vélo avec accessoires devrait coûter plus cher
        assertTrue(veloWithAccessories.getPrice() >= basicVelo.getPrice());
    }
}