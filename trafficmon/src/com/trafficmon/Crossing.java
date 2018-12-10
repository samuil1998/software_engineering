package com.trafficmon;

import org.joda.time.DateTimeUtils;

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
        this.time = DateTimeUtils.currentTimeMillis();
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public long getTimestamp() {
        return time;
    }

    public String getType(){
        return type;
    }
}
