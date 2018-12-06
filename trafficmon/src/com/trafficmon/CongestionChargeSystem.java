package com.trafficmon;

import java.math.BigDecimal;
import java.util.*;

public class CongestionChargeSystem {

    private final Map<Vehicle, List<ZoneBoundaryCrossing>> vehicleCrossings = new HashMap<Vehicle, List<ZoneBoundaryCrossing>>();

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

    public void setCharger(Charger charger)
    {
        charger.setAccountsService(accountsService);
        charger.setPenaltiesService(penaltiesService);
        this.charger = charger;
    }

    public void vehicleEnteringZone(Vehicle vehicle) {
        if (!vehicleCrossings.containsKey(vehicle)) {
            vehicleCrossings.put(vehicle, new ArrayList<ZoneBoundaryCrossing>());
        }
        vehicleCrossings.get(vehicle).add(new EntryEvent(vehicle));
    }

    public void vehicleLeavingZone(Vehicle vehicle) {

        if (!vehicleCrossings.containsKey(vehicle)) {
            return;
        }
        vehicleCrossings.get(vehicle).add(new ExitEvent(vehicle));
    }

    public void calculateCharges() {

        charger.charge(vehicleCrossings);
    }

    //for test purposes
    public Map<Vehicle, List<ZoneBoundaryCrossing>> getCrossings() {
        return vehicleCrossings;
    }
}

