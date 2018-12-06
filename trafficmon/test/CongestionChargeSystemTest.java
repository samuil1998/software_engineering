import com.trafficmon.*;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;

import java.math.BigDecimal;

public class CongestionChargeSystemTest {

    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    PenaltiesService mockPenaltyService = context.mock(PenaltiesService.class);
    AccountsService mockAccountsService = context.mock(AccountsService.class);

    CongestionChargeSystem system = new CongestionChargeSystem(mockPenaltyService, mockAccountsService);
    Vehicle vehicle = Vehicle.withRegistration("A123 XYZ");
    OldCharger charger = new OldCharger();

    @Before
    public void setUp()
    {
        system.setCharger(charger);
    }

    //Tests for the old behaviour

    @Test
    public void noPenaltyOrInvestigationForRegisteredVehiclesWithCredit() throws Exception
    {
        Account ACCOUNT = new Account("John Doe", vehicle, new BigDecimal(5));

        context.checking(new Expectations()
        {{
            exactly(1).of(mockAccountsService).accountFor(vehicle);
            will(returnValue(ACCOUNT));
            exactly(0).of(mockPenaltyService).issuePenaltyNotice(with(vehicle), with(any(BigDecimal.class)));
            exactly(0).of(mockPenaltyService).triggerInvestigationInto(vehicle);
        }});

        system.vehicleEnteringZone(vehicle);
        Thread.sleep(5);
        system.vehicleLeavingZone(vehicle);

        system.calculateCharges();
    }

    @Test
    public void triggersInvestigationIfEnteringTwiceInARow() throws Exception
    {
        context.checking(new Expectations()
        {{
            exactly(1).of(mockPenaltyService).triggerInvestigationInto(vehicle);
        }});

        system.vehicleEnteringZone(vehicle);
        Thread.sleep(5);
        system.vehicleEnteringZone(vehicle);

        system.calculateCharges();
    }

    @Test
    public void issuesPenaltyNoticeForUnregisteredVehicles() throws Exception
    {
        context.checking(new Expectations()
        {{
            exactly(1).of(mockAccountsService).accountFor(vehicle);
            will(throwException(new AccountNotRegisteredException(vehicle)));
            exactly(1).of(mockPenaltyService).issuePenaltyNotice(with(vehicle), with(any(BigDecimal.class)));

        }});

        system.vehicleEnteringZone(vehicle);
        Thread.sleep(5);
        system.vehicleLeavingZone(vehicle);

        system.calculateCharges();
    }

    @Test
    public void issuesPenaltyNoticeForInsufficientCredit() throws Exception
    {
        Account ACCOUNT = new Account("John Doe", vehicle, new BigDecimal(0));

        context.checking(new Expectations()
        {{
            exactly(1).of(mockAccountsService).accountFor(vehicle);
            will(returnValue(ACCOUNT));
            exactly(1).of(mockPenaltyService).issuePenaltyNotice(with(vehicle), with(any(BigDecimal.class)));
        }});

        system.vehicleEnteringZone(vehicle);
        Thread.sleep(5);
        system.vehicleLeavingZone(vehicle);

        system.calculateCharges();
    }

    @Test
    public void leavingVehiclesAreNotRegistered() {
        system.vehicleLeavingZone(vehicle);
        assertThat(system.getCrossings().size(), is(0));
    }

    @Test
    public void registersVehiclesSeparately() {
        Vehicle vehicle1 = Vehicle.withRegistration("1234 567");

        system.vehicleEnteringZone(vehicle1);
        system.vehicleEnteringZone(vehicle);
        system.vehicleLeavingZone(vehicle);
        system.vehicleLeavingZone(vehicle1);
        assertThat(system.getCrossings().size(), CoreMatchers.is(2));
    }

    @Test
    public void doesntRegisterTheSameVehicleTwice() {
        system.vehicleEnteringZone(vehicle);
        system.vehicleLeavingZone(vehicle);
        system.vehicleEnteringZone(vehicle);
        system.vehicleLeavingZone(vehicle);
        assertThat(system.getCrossings().size(), CoreMatchers.is(1));
    }

    //TODO: Review tests + add tests for the NEW behaviour
}
