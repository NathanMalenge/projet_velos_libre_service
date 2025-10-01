package fil.l3.coo.user;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fil.l3.coo.user.exceptions.InsufficientFundsException;

public class UserTest {
    
    private User user;
    private static final double INITIAL_WALLET = 5.0;
    
    @BeforeEach
    public void setUp() {
        user = new User(INITIAL_WALLET);
    }
    
    @Test
    public void testDefaultConstructor() {
        User defaultUser = new User();
        assertEquals(0.0, defaultUser.getWallet(), 0.01);
    }
    
    @Test
    public void testConstructorWithWallet() {
        User walletUser = new User(10.0);
        assertEquals(10.0, walletUser.getWallet(), 0.01);
    }
    
    @Test
    public void testInitialWallet() {
        assertEquals(INITIAL_WALLET, user.getWallet(), 0.01);
    }
    
    @Test
    public void testAddMoney() {
        user.addMoney(3.0);
        assertEquals(8.0, user.getWallet(), 0.01);
    }
    
    @Test
    public void testAddNegativeMoney() {
        user.addMoney(-2.0);
        assertEquals(INITIAL_WALLET, user.getWallet(), 0.01);
    }
    
    @Test
    public void testDeductMoneySuccess() throws InsufficientFundsException {
        user.deductMoney(2.0);
        assertEquals(3.0, user.getWallet(), 0.01);
    }
    
    @Test
    public void testDeductMoneyFailureInsufficientFunds() {
        assertThrows(InsufficientFundsException.class, () -> {
            user.deductMoney(10.0);
        });
        assertEquals(INITIAL_WALLET, user.getWallet(), 0.01);
    }
    
    @Test
    public void testDeductNegativeMoney() {
        assertThrows(InsufficientFundsException.class, () -> {
            user.deductMoney(-2.0);
        });
        assertEquals(INITIAL_WALLET, user.getWallet(), 0.01);
    }
    
    @Test
    public void testCanAfford() {
        assertTrue(user.canAfford(3.0));
        assertTrue(user.canAfford(5.0));
        assertFalse(user.canAfford(6.0));
    }
}
