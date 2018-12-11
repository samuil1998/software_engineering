package com.trafficmon;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Log {

    private final Map<Vehicle, List<Crossing>> vehicleCrossings = new HashMap<Vehicle, List<Crossing>>();

    public void addEntry(Vehicle vehicle) {
        if (!vehicleCrossings.containsKey(vehicle)) {
            vehicleCrossings.put(vehicle, new ArrayList<Crossing>());
        }
        vehicleCrossings.get(vehicle).add(new Crossing(vehicle, "entry"));
    }

    public void addExit(Vehicle vehicle) {
        if (!vehicleCrossings.containsKey(vehicle)) {
            return;
        }
        vehicleCrossings.get(vehicle).add(new Crossing(vehicle, "exit"));
    }

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

    public List<Vehicle> getVehicles() {
        return new ArrayList<Vehicle>(vehicleCrossings.keySet());
    }

    public List<Crossing> getCrossingsFor(Vehicle vehicle)
    {
        return vehicleCrossings.get(vehicle);
    }

    public int size()
    {
        return vehicleCrossings.size();
    }
}
