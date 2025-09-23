package fil.l3.coo.user;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTest {
    
    private User user;
    private static final String TEST_NAME = "TestUser";
    
    @BeforeEach
    public void setUp() {
        user = new User(TEST_NAME);
    }
    
    @Test
    public void testUniqueIds() {
        User user1 = new User("User1");
        User user2 = new User("User2");
        assertNotEquals(user1.getId(), user2.getId());
    }
    
    @Test
    public void testInitialWallet() {
        assertEquals(0, user.getWallet());
    }
    
    @Test
    public void testAddMoney() {
        user.addMoney(100);
        assertEquals(100, user.getWallet());
        
        user.addMoney(50);
        assertEquals(150, user.getWallet());
    }
    
    @Test
    public void testAddNegativeMoney() {
        user.addMoney(-100);
        assertEquals(0, user.getWallet());
    }
    
    @Test
    public void testDeductMoneySuccess() {
        user.addMoney(100);
        user.deductMoney(50);
        assertEquals(50, user.getWallet());
    }
    
    @Test
    public void testDeductMoneyFailureInsufficientFunds() {
        user.addMoney(30);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            user.deductMoney(50);
        });
        assertEquals("Solde insuffisant", exception.getMessage());
        assertEquals(30, user.getWallet());
    }
    
    @Test
    public void testDeductNegativeMoney() {
        user.addMoney(100);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            user.deductMoney(-50);
        });
        assertEquals("Le montant doit être positif", exception.getMessage());
        assertEquals(100, user.getWallet());
    }
    
    @Test
    public void testToString() {
        String expected = "User{id=" + user.getId() + ", name='" + TEST_NAME + "', wallet=0}";
        assertEquals(expected, user.toString());
    }
}