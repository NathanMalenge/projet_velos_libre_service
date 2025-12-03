package fil.l3.coo.control;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import fil.l3.coo.station.Station;
import fil.l3.coo.station.exceptions.NullVehiculeException;
import fil.l3.coo.station.exceptions.StationFullException;
import fil.l3.coo.station.exceptions.VehiculeNotFoundException;
import fil.l3.coo.vehicule.VehiculeComponent;

/**
 * Simple redistribution that repeatedly moves one vehicle from the most loaded
 * station to the least loaded one until the distribution stabilizes or a small
 * iteration budget is reached.
 */
public class RoundRobinRedistribution implements RedistributionStrategy {

    private static final int MAX_MOVES_PER_CALL = 20;

    @Override
    public void redistribute(List<Station<VehiculeComponent>> stations) {
        if (stations == null || stations.size() < 2) return;

        List<Station<VehiculeComponent>> tmp = new ArrayList<>(stations);

        int moves = 0;
        while (moves < MAX_MOVES_PER_CALL) {
            tmp.sort(Comparator.comparingInt(Station::getOccupiedSpaces));
            Station<VehiculeComponent> least = tmp.get(0);
            Station<VehiculeComponent> most = tmp.get(tmp.size() - 1);

            int diff = most.getOccupiedSpaces() - least.getOccupiedSpaces();
            if (diff <= 1) break; 

            if (!most.hasAvailableVehicules() || !least.hasAvailableSpace()) break;

            List<VehiculeComponent> parked = most.getParkedVehicules();
            if (parked.isEmpty()) break;
            VehiculeComponent v = parked.get(0);

            try {
                most.removeVehicule(v);
                least.parkVehicule(v);
                moves++;
            } catch (NullVehiculeException | VehiculeNotFoundException | StationFullException e) {
                break;
            }
        }
    }
}
