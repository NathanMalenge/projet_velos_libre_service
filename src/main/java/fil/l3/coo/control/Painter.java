package fil.l3.coo.control;

import fil.l3.coo.station.Station;
import fil.l3.coo.vehicule.Vehicule;
import fil.l3.coo.vehicule.VehiculeComponent;

/**
 * Simple example of an additional service interacting with vehicles.
 * <p>
 * The painter represents campaigns where the fleet is repainted for
 * special events (e.g. JO, marketing). For the purposes of the project
 * this service only records an event in the control center via its
 * logical type, without modifying the vehicle state.
 */
public class Painter implements VehicleService {

    @Override
    public boolean service(Station<?> station, VehiculeComponent vehicule) {
        // unwrap decorators if necessary
        Vehicule base = vehicule instanceof Vehicule ? (Vehicule) vehicule : null;
        if (base == null) {
            return false;
        }
        // In a real system we might change some visual attribute; here we
        // only signal that the service was successfully applied.
        return true;
    }

    @Override
    public String getServiceType() {
        return "PAINT";
    }
}
