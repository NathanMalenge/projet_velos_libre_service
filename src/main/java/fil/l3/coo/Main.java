package fil.l3.coo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fil.l3.coo.control.ControlCenter;
import fil.l3.coo.station.Station;
import fil.l3.coo.station.exceptions.NullVehiculeException;
import fil.l3.coo.station.exceptions.StationFullException;
import fil.l3.coo.station.exceptions.VehiculeNotFoundException;
import fil.l3.coo.vehicule.VehiculeComponent;
import fil.l3.coo.vehicule.velo.VeloClassique;
import fil.l3.coo.vehicule.velo.VeloElectrique;

/**
 * Point d'entrée principal de l'application.
 * Première étape : boucle de simulation simple par intervalles
 * avec dépôts/retraits aléatoires entre stations.
 */
public class Main {

    /**
     * Lance une simulation simplifiée.
     *
     * - Crée N stations
     * - Peuple chaque station avec des vélos initiaux
     * - À chaque tick, déplace aléatoirement des vélos d'une station à une autre
     *
     * @param stationCount nombre de stations
     * @param initialPerStation nombre de vélos initiaux par station
     * @param ticks nombre d'intervalles (tours) à exécuter
     * @param seed graine aléatoire pour reproductibilité
     */
    public static void runSimulation(int stationCount, int initialPerStation, int ticks, long seed) {
        ControlCenter controlCenter = new ControlCenter();
        Random rnd = new Random(seed);

        List<Station<VehiculeComponent>> stations = new ArrayList<>();
        for (int i = 0; i < stationCount; i++) {
            Station<VehiculeComponent> s = new Station<>(15);
            controlCenter.registerStation(s);
            stations.add(s);
        }

        for (Station<VehiculeComponent> s : stations) {
            for (int j = 0; j < initialPerStation; j++) {
                VehiculeComponent v = (j % 2 == 0) ? new VeloClassique() : new VeloElectrique();
                try {
                    s.parkVehicule(v);
                } catch (NullVehiculeException | StationFullException e) {
                }
            }
        }

        for (int t = 1; t <= ticks; t++) {
            int movesThisTick = 1 + rnd.nextInt(Math.max(1, stationCount));

            for (int m = 0; m < movesThisTick; m++) {
                Station<VehiculeComponent> source = null;
                for (int attempts = 0; attempts < 10 && source == null; attempts++) {
                    Station<VehiculeComponent> candidate = stations.get(rnd.nextInt(stations.size()));
                    if (candidate.hasVehicules()) {
                        source = candidate;
                    }
                }
                if (source == null) break; 

                List<VehiculeComponent> parked = source.getParkedVehicules();
                VehiculeComponent selected = parked.get(rnd.nextInt(parked.size()));

                Station<VehiculeComponent> dest = null;
                for (int attempts = 0; attempts < 10 && dest == null; attempts++) {
                    Station<VehiculeComponent> candidate = stations.get(rnd.nextInt(stations.size()));
                    if (candidate.hasAvailableSpace()) {
                        dest = candidate;
                    }
                }
                if (dest == null) continue; 

                try {
                    source.removeVehicule(selected);
                    dest.parkVehicule(selected);
                } catch (NullVehiculeException | VehiculeNotFoundException | StationFullException e) {
                }
            }

            // Mise à jour supervision (vide/plein) + redistribution éventuelle
            controlCenter.onTick();

            System.out.printf("\n[Tick %d]\n", t);
            controlCenter.printFleetSummary();
        }
    }

    /**
     * Méthode main : lance une simulation courte avec paramètres par défaut.
     */
    public static void main(String[] args) {
        runSimulation(4, 5, 5, 42L);
    }
}
