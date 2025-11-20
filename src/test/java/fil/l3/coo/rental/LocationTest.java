package fil.l3.coo.rental;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import fil.l3.coo.user.User;
import fil.l3.coo.vehicule.VehiculeComponent;
import fil.l3.coo.vehicule.velo.VeloClassique;
import fil.l3.coo.vehicule.decorator.BasketDecorator;

/**
 * Test class for Location.
 */
public class LocationTest {
    
    private User user;
    private VehiculeComponent vehicule;
    private Location location;
    
    @BeforeEach
    public void setUp() {
        user = new User(20.0);
        vehicule = new VeloClassique();
        location = new Location(user, vehicule);
    }
    
    @Test
    public void testGetUser() {
        assertEquals(user, location.getUser());
    }
    
    @Test
    public void testGetVehicule() {
        assertEquals(vehicule, location.getVehicule());
    }
    
    @Test
    public void testGetCost() {
        assertEquals(vehicule.getPrice(), location.getCost());
    }
    
    @Test
    public void testGetCostWithDecorator() {
        VehiculeComponent decoratedVehicule = new BasketDecorator(new VeloClassique());
        Location decoratedLocation = new Location(user, decoratedVehicule);
        
        assertEquals(decoratedVehicule.getPrice(), decoratedLocation.getCost());
        assertTrue(decoratedLocation.getCost() > 1.0);
    }
    
    @Test
    public void testToString() {
        String str = location.toString();
        
        assertNotNull(str);
        assertTrue(str.contains("Location"));
        assertTrue(str.contains("user="));
        assertTrue(str.contains("vehicule="));
    }
}
