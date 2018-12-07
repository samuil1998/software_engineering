package com.trafficmon;

import java.util.*;

public class TrafficLog implements Log {

    private final Map<Vehicle, List<Crossing>> vehicleCrossings = new HashMap<Vehicle, List<Crossing>>();

    @Override
    public void addEntry(Vehicle vehicle) {
        if (!vehicleCrossings.containsKey(vehicle)) {
            vehicleCrossings.put(vehicle, new ArrayList<Crossing>());
        }
        vehicleCrossings.get(vehicle).add(new Crossing(vehicle, "entry"));
    }

    @Override
    public void addExit(Vehicle vehicle) {
        if (!vehicleCrossings.containsKey(vehicle)) {
            return;
        }
        vehicleCrossings.get(vehicle).add(new Crossing(vehicle, "exit"));
    }

    @Override
    public boolean isOrdered(Vehicle vehicle) {

        List<Crossing> crossings = getCrossingsFor(vehicle);

        for (int i = 0; i < crossings.size() - 1; i++) {
            Crossing previousCrossing = crossings.get(i);
            Crossing nextCrossing = crossings.get(i+1);

            if (previousCrossing.getTimestamp() > nextCrossing.getTimestamp()) {return false;}
            if (previousCrossing.getType().equals(nextCrossing.getType())) {return false;}
        }
        return true;
    }

    @Override
    public List<Vehicle> getVehicles() {
        return new ArrayList<Vehicle>(vehicleCrossings.keySet());
    }

    @Override
    public List<Crossing> getCrossingsFor(Vehicle vehicle)
    {
        return vehicleCrossings.get(vehicle);
    }

    @Override
    public int size()
    {
        return vehicleCrossings.size();
    }

    public static void main(String args[])
    {
        //TODO: Delete method before submission - main method just for manual testing

        Log log = new TrafficLog();
        log.addEntry(Vehicle.withRegistration("1234 568"));
        log.addEntry(Vehicle.withRegistration("1234 568"));
        log.addEntry(Vehicle.withRegistration("1234 567"));

        log.addExit(Vehicle.withRegistration("1234 567"));

        System.out.println(log.getVehicles());
        System.out.println(log.getCrossingsFor(Vehicle.withRegistration("1234 567")));
        System.out.println(log.isOrdered(Vehicle.withRegistration("1234 567")));

    }
}
