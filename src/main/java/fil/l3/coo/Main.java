package fil.l3.coo;

/**
 * Point d'entrée principal. Initialise et lance la simulation.
 *
 * Arguments possibles (soit aucun, soit exactement 5, dans cet ordre) :
 * <pre>
 *  args[0] = nombre de stations            (par défaut 4)
 *  args[1] = véhicules initiaux/station    (par défaut 5)
 *  args[2] = nombre de ticks               (par défaut 100)
 *  args[3] = nombre d'utilisateurs         (par défaut 10)
 *  args[4] = graine aléatoire (long)       (par défaut 42)
 * </pre>
 */
public class Main {

    public static void main(String[] args) {
        int stationCount = 4;
        int initialPerStation = 5;
        int ticks = 100;
        int userCount = 10;
        long seed = 42L;

        try {
            if (args.length == 5) {
                stationCount = Integer.parseInt(args[0]);
                initialPerStation = Integer.parseInt(args[1]);
                ticks = Integer.parseInt(args[2]);
                userCount = Integer.parseInt(args[3]);
                seed = Long.parseLong(args[4]);
            } else if (args.length != 0) {
                System.err.println("Vous devez fournir soit aucun argument, soit exactement 5 arguments :");
                System.err.println("<stations> <vehiculesParStation> <ticks> <utilisateurs> <graine>");
                return;
            }
        } catch (NumberFormatException e) {
            System.err.println("Arguments invalides, utilisation des valeurs par défaut.");
        }

        Simulation sim = new Simulation(stationCount, initialPerStation, ticks, seed, userCount);
        sim.run();
    }
}
