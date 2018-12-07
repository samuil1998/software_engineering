package com.trafficmon;

import java.util.*;

public class CongestionChargeSystem {

    Log log = new TrafficLog();

    private PenaltiesService penaltiesService;
    private AccountsService accountsService;
    private Charger charger;

    public CongestionChargeSystem() {
        this.penaltiesService = OperationsTeam.getInstance();
        this.accountsService = RegisteredCustomerAccountsService.getInstance();
    }

    public CongestionChargeSystem(PenaltiesService penaltiesService, AccountsService accountsService) {
        this.penaltiesService = penaltiesService;
        this.accountsService = accountsService;
    }

    public void setCharger(Charger charger) {
        charger.setAccountsService(accountsService);
        charger.setPenaltiesService(penaltiesService);
        this.charger = charger;
    }

    public void vehicleEnteringZone(Vehicle vehicle) {
        log.addEntry(vehicle);
    }

    public void vehicleLeavingZone(Vehicle vehicle) {
        log.addExit(vehicle);
    }

    public void calculateCharges() {
        charger.charge(log);
    }

    public Log getLog() {
        return log;
    }
}

