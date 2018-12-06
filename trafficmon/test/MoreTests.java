import com.trafficmon.CongestionChargeSystem;
import com.trafficmon.Vehicle;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

public class MoreTests {
    CongestionChargeSystem congestionChargeSystem = new CongestionChargeSystem();
    Vehicle vehicle = Vehicle.withRegistration("TEST 123");

    @Test
    public void vehicleShouldEnterZone() {
        congestionChargeSystem.vehicleEnteringZone(vehicle);
        assertThat(congestionChargeSystem.getEventLog().size(), is(1));
    }

    @Test
    public void vehicleShouldNotLeaveZoneIfNoteRegistered() {
        congestionChargeSystem.vehicleLeavingZone(vehicle);
        assertThat(congestionChargeSystem.getEventLog().size(), is(0));
    }

    @Test
    public void vehicleShouldLeaveZoneWhenRegistered() {
        congestionChargeSystem.vehicleEnteringZone(vehicle);
        congestionChargeSystem.vehicleLeavingZone(vehicle);
        assertThat(congestionChargeSystem.getEventLog().size(), is(2));
    }

    @Test
    public void eventLogSizeShouldIncrement() {
        congestionChargeSystem.vehicleEnteringZone(vehicle);
        congestionChargeSystem.vehicleLeavingZone(vehicle);
        congestionChargeSystem.vehicleEnteringZone(vehicle);
        assertThat(congestionChargeSystem.getEventLog().size(), is(3));
    }
}
