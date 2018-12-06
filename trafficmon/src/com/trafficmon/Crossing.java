package com.trafficmon;

public class Crossing {

    private final String type;
    private final Vehicle vehicle;
    private final long time;

    public Crossing(Vehicle vehicle, String type) {
        if (type.equals("entry") || type.equals("exit")) {
            this.type = type;
        }
        else {
            throw new IllegalArgumentException("Crossing can only be of type entry or exit");
        }
        this.vehicle = vehicle;
        this.time = System.currentTimeMillis();
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public boolean isEntry()
    {
        return type.equals("entry");
    }

    public boolean isExit()
    {
        return type.equals("exit");
    }

    public long timestamp() {
        return time;
    }
}
