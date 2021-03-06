import com.trafficmon.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import org.joda.time.*;
import org.junit.*;
import java.util.*;
import java.math.BigDecimal;


public class ChargeCalculatorTest {

    private ChargeCalculator newCharger = new ChargeCalculator();
    private Vehicle vehicle = Vehicle.withRegistration("1234 567");
    private List<Crossing> crossings = new ArrayList<Crossing>();

    private void entry()
    {
        crossings.add(new Crossing(vehicle, "entry"));
    }

    private void exit()
    {
        crossings.add(new Crossing(vehicle, "exit"));
    }

    private void setTimeTo(int hours, int minutes, int seconds)
    {
        DateTime dt = new DateTime(2018, 12, 10, hours, minutes, seconds, 0);
        DateTimeUtils.setCurrentMillisFixed(dt.getMillis());
    }

    @Test
    public void chargesTwelvePoundsForStayingOverFourHours()
    {
        setTimeTo(6,0,0);
        entry();
        setTimeTo(8,0,0);
        exit();
        setTimeTo(15,0,0);
        entry();
        setTimeTo(18,0,0);
        exit();

        assertThat(newCharger.calculateCharge(crossings), is(new BigDecimal(12)));
    }

    @Test
    public void doesntChargeOvernightStay()
    {
        setTimeTo(22,0,0);
        entry();

        assertThat(newCharger.calculateCharge(crossings), is(new BigDecimal(0)));
    }

    @Test
    public void chargesSixPoundsForEnteringBeforeFourteen()
    {
        setTimeTo(8,0, 0);
        entry();
        setTimeTo(10,0, 0);
        exit();

        assertThat(newCharger.calculateCharge(crossings), is(new BigDecimal(6)));
    }

    @Test
    public void chargesFourPoundsForEnteringAfterFourteen()
    {
        setTimeTo(15,0, 0);
        entry();
        setTimeTo(17,0, 0);
        exit();

        assertThat(newCharger.calculateCharge(crossings), is(new BigDecimal(4)));
    }

    @Test
    public void doesntChargeTwiceIfReturnWithinFourHours()
    {
        setTimeTo(11,00, 0);
        entry();
        setTimeTo(12,00, 0);
        exit();
        setTimeTo(14,30, 0);
        entry();
        setTimeTo(15,30, 0);
        exit();

        assertThat(newCharger.calculateCharge(crossings), is(new BigDecimal(6)));
    }

}
