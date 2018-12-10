package com.trafficmon;

import java.math.BigDecimal;
import java.util.*;

public class CongestionChargeSystem {

    Log log = new TrafficLog();

    private PenaltiesService penaltiesService = OperationsTeam.getInstance();
    private AccountsService accountsService = RegisteredCustomerAccountsService.getInstance();
    private Calculator calculator = new ChargeCalculator();

    public CongestionChargeSystem() {}

    public CongestionChargeSystem(PenaltiesService penaltiesService, AccountsService accountsService) {
        this.penaltiesService = penaltiesService;
        this.accountsService = accountsService;
        this.calculator = new ChargeCalculator();
    }

    public void vehicleEnteringZone(Vehicle vehicle) {
        log.addEntry(vehicle);
    }

    public void vehicleLeavingZone(Vehicle vehicle) {
        log.addExit(vehicle);
    }

    public void calculateCharges() {

        for (Vehicle vehicle : log.getVehicles())
        {
            List<Crossing> crossings = log.getCrossingsFor(vehicle);

            if (!log.isOrdered(vehicle)) {
                penaltiesService.triggerInvestigationInto(vehicle);
            }
            else {
                BigDecimal charge = calculator.calculateCharge(crossings);

                try {
                    accountsService.accountFor(vehicle).deduct(charge);
                } catch (InsufficientCreditException ice) {
                    penaltiesService.issuePenaltyNotice(vehicle, charge);
                } catch (AccountNotRegisteredException e) {
                    penaltiesService.issuePenaltyNotice(vehicle, charge);
                }
            }
        }
    }
}

