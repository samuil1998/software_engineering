import com.trafficmon.Crossing;
import com.trafficmon.Vehicle;
import org.junit.Test;

import static org.junit.Assert.*;

public class CrossingTest {

    @Test
    public void getVehicleTesting() {
        Vehicle vehicle = Vehicle.withRegistration("TEST 003");
        Crossing testCrossing = new Crossing(vehicle, "entry");
        assertEquals(vehicle, testCrossing.getVehicle());
    }
}