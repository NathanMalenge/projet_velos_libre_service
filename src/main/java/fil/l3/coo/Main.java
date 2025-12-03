package fil.l3.coo;

/**
 * Point d'entrée principal. Initialise et lance la simulation.
 */
public class Main {

    public static void main(String[] args) {
        // Paramètres par défaut : 4 stations, 5 vélos/station, 5 ticks, graine fixe
        Simulation sim = new Simulation(4, 5, 5, 42L);
        sim.run();
    }
}
