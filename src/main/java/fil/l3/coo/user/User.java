package fil.l3.coo.user;

public class User {

    private static int nextId = 1;
    private int id;
    private String name;
    private int wallet = 0;

    /**
     * Creates a new user with the specified name.
     * The user is automatically assigned a unique ID and starts with an empty wallet.
     * 
     * @param name the name of the user
     */
    public User(String name) {
        this.id = nextId++;
        this.name = name;
    }

    /**
     * Gets the unique identifier of this user.
     * 
     * @return the user's unique ID
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the name of this user.
     * 
     * @return the user's name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the current amount of money in the user's wallet.
     * 
     * @return the wallet balance
     */
    public int getWallet() {
        return wallet;
    }

    /**
     * Adds money to the user's wallet.
     * Only positive amounts are accepted; negative or zero amounts are ignored.
     * 
     * @param amount the amount of money to add (must be positive)
     */
    public void addMoney(int amount) {
        if (amount > 0) {
            wallet += amount;
        }
    }
<<<<<<< HEAD
    /**
     * Deducts money from the user's wallet.
     * The deduction only succeeds if the amount is positive and the user has sufficient funds.
     * 
     * @param amount the amount of money to deduct (must be positive)
     * @return true if the deduction was successful, false otherwise
     */
    public boolean deductMoney(int amount) {
        if (amount > 0 && wallet >= amount) {
            wallet -= amount;
            return true;
=======
    public void deductMoney(int amount) throws IllegalArgumentException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Le montant doit être positif");
>>>>>>> bf68de4 (class user test effectue)
        }
        if (wallet < amount) {
            throw new IllegalArgumentException("Solde insuffisant");
        }
        wallet -= amount;
    }

    /**
     * Returns a string representation of this user.
     * 
     * @return a string containing the user's ID, name, and wallet balance
     */
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", wallet=" + wallet +
                '}';
    }   

}
