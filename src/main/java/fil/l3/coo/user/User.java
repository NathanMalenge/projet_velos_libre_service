package fil.l3.coo.user;

public class User {

    private static int nextId = 1;
    private int id;
    private String name;
    private int wallet = 0;

    public User(String name) {
        this.id = nextId++;
        this.name = name;
    }

    public int getId() {
        return id;
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
    public boolean deductMoney(int amount) {
        if (amount > 0 && wallet >= amount) {
            wallet -= amount;
            return true;
        }
        return false;
    }

    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", wallet=" + wallet +
                '}';
    }   

}
