package com.trafficmon;

import java.util.*;

public class CongestionChargeSystem {

    private final Map<Vehicle, List<Crossing>> vehicleCrossings = new HashMap<Vehicle, List<Crossing>>();

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
        if (!vehicleCrossings.containsKey(vehicle)) {
            vehicleCrossings.put(vehicle, new ArrayList<Crossing>());
        }
        vehicleCrossings.get(vehicle).add(new Crossing(vehicle, "entry"));
    }

    public void vehicleLeavingZone(Vehicle vehicle) {
        if (!vehicleCrossings.containsKey(vehicle)) {
            return;
        }
        vehicleCrossings.get(vehicle).add(new Crossing(vehicle, "exit"));
    }

    public void calculateCharges() {
        charger.charge(vehicleCrossings);
    }

    //for test purposes
    public Map<Vehicle, List<Crossing>> getCrossings() {
        return vehicleCrossings;
    }
}

