package fil.l3.coo.rental;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fil.l3.coo.user.User;
import fil.l3.coo.vehicule.VehiculeComponent;
import fil.l3.coo.vehicule.velo.VeloClassique;
import fil.l3.coo.vehicule.velo.VeloElectrique;
import fil.l3.coo.vehicule.decorator.BasketDecorator;
import fil.l3.coo.station.Station;
import fil.l3.coo.station.exceptions.NullVehiculeException;
import fil.l3.coo.station.exceptions.StationFullException;
import fil.l3.coo.rental.exceptions.*;

public class RentalSystemTest {
    
    private RentalSystem rentalSystem;
    private User user;
    private Station<VehiculeComponent> station;
    private VehiculeComponent velo1;
    private VehiculeComponent velo2;
    private VehiculeComponent velo3;
    
    @BeforeEach
    public void setUp() throws NullVehiculeException, StationFullException {
        rentalSystem = new RentalSystem();
        user = new User(10.0); 
        station = new Station<>(15);
        
        velo1 = new VeloClassique(); 
        velo2 = new VeloElectrique();
        velo3 = new BasketDecorator(new VeloClassique()); 
        
        station.parkVehicule(velo1);
        station.parkVehicule(velo2);
        station.parkVehicule(velo3);
    }
    
    @Test
    public void testSuccessfulRental() throws VehiculeNotAvailableException, CannotAffordRentalException {
        assertEquals(3, station.getOccupiedSpaces());
        
        Location rental = rentalSystem.rentVehicule(user, station, velo1);
        
        assertNotNull(rental);
        assertEquals(user, rental.getUser());
        assertEquals(velo1, rental.getVehicule());
        assertEquals(2, station.getOccupiedSpaces()); 
        assertEquals(9.0, user.getWallet(), 0.01); 
    }
    
    @Test
    public void testRentalBikeNotInStation() {
        VehiculeComponent otherVelo = new VeloClassique();
        
        assertThrows(VehiculeNotAvailableException.class, () -> {
            rentalSystem.rentVehicule(user, station, otherVelo);
        });
        
        assertEquals(10.0, user.getWallet(), 0.01); 
        assertEquals(3, station.getOccupiedSpaces()); 
    }
    
    @Test
    public void testRentalWithInsufficientFunds() {
        User poorUser = new User(0.5); 
        
        assertThrows(CannotAffordRentalException.class, () -> {
            rentalSystem.rentVehicule(poorUser, station, velo2);
        });
        
        assertEquals(0.5, poorUser.getWallet(), 0.01); 
        assertEquals(3, station.getOccupiedSpaces());
    }
    
    @Test
    public void testRentalWithNullBike() {
        assertThrows(VehiculeNotAvailableException.class, () -> {
            rentalSystem.rentVehicule(user, station, null);
        });
        
        assertEquals(10.0, user.getWallet(), 0.01);
    }
    
    @Test
    public void testReturnBike() throws VehiculeNotAvailableException, CannotAffordRentalException {
        Location rental = rentalSystem.rentVehicule(user, station, velo1);
        assertNotNull(rental);
        
        Station<VehiculeComponent> returnStation = new Station<>(10);
        boolean success = rentalSystem.returnVehicule(rental, returnStation);
        
        assertTrue(success);
        assertEquals(1, returnStation.getOccupiedSpaces());
        assertTrue(rental.getVehicule().isAvailable());
    }
    
    @Test
    public void testMultipleRentals() throws VehiculeNotAvailableException, CannotAffordRentalException {
        Location rental1 = rentalSystem.rentVehicule(user, station, velo1);
        assertEquals(9.0, user.getWallet(), 0.01);
        assertEquals(2, station.getOccupiedSpaces());
        
        Location rental2 = rentalSystem.rentVehicule(user, station, velo2);
        assertEquals(7.0, user.getWallet(), 0.01);
        assertEquals(1, station.getOccupiedSpaces());
        
        assertNotNull(rental1);
        assertNotNull(rental2);
        assertNotEquals(rental1.getVehicule(), rental2.getVehicule());
    }
    
    @Test
    public void testRentalWithDecoratedBike() throws VehiculeNotAvailableException, CannotAffordRentalException {
        Location rental = rentalSystem.rentVehicule(user, station, velo3);
        
        assertNotNull(rental);
        assertEquals(1.5, rental.getCost(), 0.01);
        assertEquals(8.5, user.getWallet(), 0.01);
    }
}
