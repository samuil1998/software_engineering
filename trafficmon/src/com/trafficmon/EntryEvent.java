package com.trafficmon;

public class EntryEvent extends Crossing {
    public EntryEvent(Vehicle vehicleRegistration) {
        super(vehicleRegistration, "entry");
    }
}
