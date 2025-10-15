package fil.l3.coo.vehicule.velo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VeloClassiqueTest extends VeloTest {

    @Override
    protected Velo createVelo() {
        return new VeloClassique();
    }

}