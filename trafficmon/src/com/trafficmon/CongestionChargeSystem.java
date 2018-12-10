package com.trafficmon;

import java.math.BigDecimal;
import java.util.*;

public class CongestionChargeSystem {

    Log log = new TrafficLog();

    private PenaltiesService penaltiesService;
    private AccountsService accountsService;
    private Calculator calculator;

    public CongestionChargeSystem() {
        this.penaltiesService = OperationsTeam.getInstance();
        this.accountsService = RegisteredCustomerAccountsService.getInstance();
        this.calculator = new ChargeCalculator();
    }

    public CongestionChargeSystem(PenaltiesService penaltiesService, AccountsService accountsService) {
        this.penaltiesService = penaltiesService;
        this.accountsService = accountsService;
    }

    public void setCalculator(Calculator calculator) {
        this.calculator = calculator;
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

    public Log getLog() {
        return log;
    }
}

