package com.trafficmon;
import java.util.*;

public interface Log {

    void addEntry(Vehicle vehicle);
    void addExit(Vehicle vehicle);
    boolean isOrdered(Vehicle vehicle);
    List<Vehicle> getVehicles();
    List<Crossing> getCrossingsFor(Vehicle vehicle);
    int size();
}
