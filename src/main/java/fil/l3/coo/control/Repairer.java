package fil.l3.coo.control;

import fil.l3.coo.station.Station;
import fil.l3.coo.vehicule.Vehicule;
import fil.l3.coo.vehicule.VehiculeComponent;
import fil.l3.coo.vehicule.state.EnMaintenanceState;

/**
 * Service de réparation autonome qui peut intervenir sur n'importe quel véhicule
 * en maintenance, n'importe quand, sans limitation de capacité.
 * Représente une société de réparation avec des ressources illimitées.
 */
public class Repairer implements VehicleService {
    
    @Override
    public boolean service(Station<?> station, VehiculeComponent vehicule) {
        return repair(station, vehicule);
    }
    
    @Override
    public String getServiceType() {
        return "REPAIR";
    }
    
    /**
     * Répare un véhicule en maintenance dans une station donnée.
     * 
     * @param station la station où se trouve le véhicule
     * @param vehicule le véhicule à réparer
     * @return true si la réparation a réussi, false sinon
     */
    public boolean repair(Station<?> station, VehiculeComponent vehicule) {
        if (vehicule == null || station == null) {
            return false;
        }
        
        if (!"EN_MAINTENANCE".equals(vehicule.getStateName())) {
            return false;
        }
        
        try {
            EnMaintenanceState maintenanceState = (EnMaintenanceState) vehicule.getState();
            maintenanceState.completeMaintenance((Vehicule) vehicule);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
