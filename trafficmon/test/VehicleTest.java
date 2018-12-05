import com.trafficmon.Vehicle;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class VehicleTest {

    @Test
    public void toStringShouldPrintRegistration() {
        Vehicle testVehicle = Vehicle.withRegistration("TEST 001");
        assertEquals("Vehicle [TEST 001]", testVehicle.toString());
    }

    @Test
    public void equalsTesting() {
        Vehicle testV1 = Vehicle.withRegistration("TEST 002");
        Vehicle testV2 = Vehicle.withRegistration("TEST 002");
        assertTrue(testV1.equals(testV2));
    }

    @Test
    public void hashCodeTest() {
        Vehicle testVehicle = Vehicle.withRegistration(null);
        assertEquals(0, testVehicle.hashCode());
    }
}
