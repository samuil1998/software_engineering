import com.trafficmon.EntryEvent;
import com.trafficmon.Vehicle;
import com.trafficmon.ZoneBoundaryCrossing;
import org.junit.Test;

import static org.junit.Assert.*;

public class ZoneBoundaryCrossingTest {

    @Test
    public void getVehicleTesting() {
        Vehicle testVehicle = Vehicle.withRegistration("TEST 003");
        ZoneBoundaryCrossing testCrossing = new EntryEvent(testVehicle);
        assertEquals(testVehicle, testCrossing.getVehicle());
    }
}