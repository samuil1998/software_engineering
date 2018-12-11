import com.trafficmon.Log;
import com.trafficmon.Vehicle;
import org.joda.time.DateTimeZone;
import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class LogTest {
    Log testLog = new Log();

  @Test
    public void addsVehicleEntryToLog() {
        Vehicle vehicle = Vehicle.withRegistration("TEST 001");
        testLog.addEntry(vehicle);
        assertThat(testLog.size(),is(1));
    }

    @Test
    public void addsVehicleExitToLogHashMap(){
        Vehicle vehicle = Vehicle.withRegistration("TEST 001");
         testLog.addEntry(vehicle);
        DateTimeZone.forOffsetMillis(1000 * 60);
        testLog.addExit(vehicle);
        assertThat(testLog.size(), is(1));
        assertThat(testLog.getCrossingsFor(vehicle).size(),is(2));
    }

    @Test
    public void addsMultipleVehicles(){
        Vehicle vehicle0 = Vehicle.withRegistration("TEST 001");
        Vehicle vehicle1 = Vehicle.withRegistration("TEST 002");
        Vehicle vehicle2 = Vehicle.withRegistration("TEST 003");
        testLog.addEntry(vehicle0);
        testLog.addEntry(vehicle1);
        testLog.addEntry(vehicle2);
        assertThat(testLog.getVehicles().size(), is(3));
    }

    @Test
    public void checksValidEntryOrdering(){
        Vehicle vehicle = Vehicle.withRegistration("TEST 001");
        testLog.addEntry(vehicle);
        testLog.addExit(vehicle);
        assertThat(testLog.isOrdered(vehicle),is(true));
    }

    @Test
    public void checksInvalidEntryOrdering(){
        Vehicle vehicle = Vehicle.withRegistration("TEST 001");
        testLog.addExit(vehicle);
        testLog.addEntry(vehicle);
        assertThat(testLog.isOrdered(vehicle),is(true));
    }

    @Test
    public void checkrepeatedEntries(){
        Vehicle vehicle = Vehicle.withRegistration("TEST 001");
        testLog.addEntry(vehicle);
        testLog.addEntry(vehicle);
        assertThat(testLog.isOrdered(vehicle),is(false));
    }
}
