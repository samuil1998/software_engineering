import com.trafficmon.*;
import org.junit.Test;

import static org.hamcrest.core.Is.is;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;

import java.math.BigDecimal;

public class TestExample{

    public BigDecimal CHARGE_RATE_POUNDS_PER_MINUTE = new BigDecimal(0.05);

    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    PenaltiesService mockPenaltyService = context.mock(PenaltiesService.class);
    AccountsService mockAccountsService = context.mock(AccountsService.class);
    CongestionChargeSystem system = new CongestionChargeSystem(mockPenaltyService, mockAccountsService);

    Vehicle vehicle = Vehicle.withRegistration("A123 XYZ");

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
    public void triggersInvestigationIfEnteringTwice() throws Exception
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
}
