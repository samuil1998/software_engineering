import com.trafficmon.Vehicle;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.is;

public class VehicleTest {

    @Test
    public void toStringReturnsTheCorrectString() {
        Vehicle testVehicle = Vehicle.withRegistration("TEST 001");
        assertThat(testVehicle.toString(), is("Vehicle [TEST 001]"));
    }

    @Test
    public void equalsMethodReturnsTrueForCarsWithTheSameRegistration() {
        Vehicle testV1 = Vehicle.withRegistration("TEST 002");
        Vehicle testV2 = Vehicle.withRegistration("TEST 002");
        assertTrue(testV1.equals(testV2));
    }

    @Test
    public void equalsReturnsTrueForTwoNullRegistrations() {
        Vehicle testV1 = Vehicle.withRegistration(null);
        Vehicle testV2 = Vehicle.withRegistration(null);
        assertTrue(testV1.equals(testV2));

    }

    @Test
    public void hashCodeReturnsZeroForNullRegistration() {
        Vehicle testVehicle = Vehicle.withRegistration(null);
        assertThat(testVehicle.hashCode(), is(0));
    }
}
