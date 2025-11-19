package fil.l3.coo.station;

import fil.l3.coo.vehicule.VehiculeComponent;

/**
 * Observer interface used to get notified when vehicles are parked or removed
 * from a station. Allows the ControlCenter (and future observers) to react in
 * real-time to station events.
 */
public interface StationObserver {

    /**
     * Called when a vehicle has been successfully parked in the station.
     *
     * @param station  the station where the vehicle was parked
     * @param vehicule the parked vehicle
     */
    void onVehicleParked(Station<?> station, VehiculeComponent vehicule);

    /**
     * Called when a vehicle has been removed from the station.
     *
     * @param station  the station where the vehicle was removed
     * @param vehicule the removed vehicle
     */
    void onVehicleRemoved(Station<?> station, VehiculeComponent vehicule);
}
