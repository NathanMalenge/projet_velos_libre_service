package fil.l3.coo.control;

import java.util.List;

import fil.l3.coo.station.Station;
import fil.l3.coo.vehicule.VehiculeComponent;

/**
 * Strategy interface to redistribute vehicles across stations when
 * supervision rules are triggered (e.g., stations too empty or too full).
 */
public interface RedistributionStrategy {

    /**
     * Applies a redistribution plan across the provided stations. Implementations
     * are free to decide how many moves to perform, but should remain fast and
     * simple as this may be called at each simulation tick.
     *
     * @param stations the stations to balance
     */
    void redistribute(List<Station<VehiculeComponent>> stations);
}
