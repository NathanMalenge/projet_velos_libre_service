package fil.l3.coo;

/**
 * Point d'entrée principal. Initialise et lance la simulation.
 */
public class Main {

    public static void main(String[] args) {
        Simulation sim = new Simulation(4, 5, 100, 42L, 10);
        sim.run();
    }
}
