import com.trafficmon.CongestionChargeSystem;
import com.trafficmon.Vehicle;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestExample {
    CongestionChargeSystem testSystem = new CongestionChargeSystem();
    Vehicle vehicle = Vehicle.withRegistration("A123 XYZ");

    @Test
    public void vehicleShouldEnterZone() {
        testSystem.vehicleEnteringZone(vehicle);
        assertEquals(1, testSystem.getEventLog().size());
    }

    @Test
    public void vehicleShouldNotLeaveZoneIfNoteRegistered() {
        testSystem.vehicleLeavingZone(vehicle);
        assertEquals(0, testSystem.getEventLog().size());
    }

    @Test
    public void vehicleShouldLeaveZoneWhenRegistered() {
        testSystem.vehicleEnteringZone(vehicle);
        testSystem.vehicleLeavingZone(vehicle);
        assertEquals(2, testSystem.getEventLog().size());
    }

    @Test
    public void eventLogSizeShouldIncrement() {
        testSystem.vehicleEnteringZone(vehicle);
        testSystem.vehicleLeavingZone(vehicle);
        testSystem.vehicleEnteringZone(vehicle);
        assertEquals(3, testSystem.getEventLog().size());
    }
}
