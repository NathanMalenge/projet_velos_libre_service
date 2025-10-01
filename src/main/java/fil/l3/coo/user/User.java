package fil.l3.coo.user;

public class User {

    private double wallet = 0.0;

    /**
     * Creates a new user with an empty wallet.
     */
    public User() {
    }

    /**
     * Creates a new user with the specified initial wallet amount.
     * 
     * @param initialAmount the initial wallet balance (must be positive)
     */
    public User(double initialAmount) {
        if (initialAmount > 0) {
            this.wallet = initialAmount;
        }
    }

    /**
     * Gets the current amount of money in the user's wallet.
     * 
     * @return the wallet balance
     */
    public double getWallet() {
        return wallet;
    }

    /**
     * Adds money to the user's wallet.
     * Only positive amounts are accepted; negative or zero amounts are ignored.
     * 
     * @param amount the amount of money to add (must be positive)
     */
    public void addMoney(double amount) {
        if (amount > 0) {
            wallet += amount;
        }
    }

    /**
     * Deducts money from the user's wallet.
     * The deduction only succeeds if the amount is positive and the user has sufficient funds.
     * 
     * @param amount the amount of money to deduct (must be positive)
     * @throws InsufficientFundsException if the amount is negative or zero, or if there are insufficient funds
     */
    public void deductMoney(double amount) throws InsufficientFundsException {
        if (amount <= 0) {
            throw new InsufficientFundsException("Le montant doit être positif");
        }
        if (wallet < amount) {
            throw new InsufficientFundsException("Solde insuffisant");
        }
        wallet -= amount;
    }

    /**
     * Returns a string representation of this user.
     * 
     * @return a string containing the wallet balance
     */
    public String toString() {
        return "User{wallet=" + wallet + '}';
    }

    
//TODO enlever ca    
    /**
     * Checks if the user can afford a specific amount.
     * 
     * @param amount the amount to check
     * @return true if the user has sufficient funds, false otherwise
     */
    public boolean canAfford(double amount) {
        return amount >= 0 && wallet >= amount;
    }
}
