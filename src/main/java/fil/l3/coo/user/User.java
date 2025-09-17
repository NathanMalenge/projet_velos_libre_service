package fil.l3.coo.user;

public class User {

    private int id ;
    private String name;
    private int wallet = 0;

    public User(String name) {
        this.id = id++;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getWallet() {
        return wallet;
    }

    public void addMoney(int amount) {
        if (amount > 0) {
            wallet += amount;
        }
    }

}
