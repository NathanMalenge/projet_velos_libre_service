package fil.l3.coo.control;

import fil.l3.coo.station.Station;
import fil.l3.coo.vehicule.VehiculeComponent;

/**
 * Interface abstraite pour tous les services/métiers qui peuvent intervenir
 * sur les véhicules (réparateurs, peintres, nettoyeurs, etc.).
 * 
 * Permet de respecter l'Open/Closed Principle : on peut ajouter de nouveaux
 * métiers sans modifier le code existant.
 */
public interface VehicleService {
    
    /**
     * Intervient sur un véhicule dans une station donnée.
     * 
     * @param station la station où se trouve le véhicule
     * @param vehicule le véhicule sur lequel intervenir
     * @return true si l'intervention a réussi, false sinon
     */
    boolean service(Station<?> station, VehiculeComponent vehicule);
    
    /**
     * Retourne le nom/type de ce service.
     * 
     * @return le nom du service
     */
    String getServiceType();
}
