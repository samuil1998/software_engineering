package com.trafficmon;

import java.math.BigDecimal;
import java.util.*;
import org.joda.time.*;

public class NewCharger implements Charger{

    private final long FOURHOURS = 14400000;
    private List<Crossing> crossings;

    public BigDecimal calculateCharge(List<Crossing> argument) {

        if (argument.size() < 2) {
            return new BigDecimal(0);
        }

        BigDecimal charge = new BigDecimal(0);
        this.crossings = ignoreOvernight(argument);

        if (sumOfStays() > FOURHOURS) {
            return new BigDecimal(12);
        }

        Crossing currentEntry = crossings.get(0);

        while(currentEntry != null) {
            long entryTime = currentEntry.getTimestamp();
            int entryHour = new LocalTime(entryTime).getHourOfDay();

            int fee = (entryHour < 14) ? 6 : 4;
            charge = charge.add(new BigDecimal(fee));
            currentEntry = nextEntryAfter(entryTime + FOURHOURS);
        }

        return charge;
    }

    private Crossing nextEntryAfter(long after) {
        for (Crossing crossing : crossings) {
            if (crossing.getType().equals("entry") && crossing.getTimestamp() >= after) {
                return crossing;
            }
        }
        return null;
    }

    private long sumOfStays() {
        long stay = 0;

        for(int i = 0; i < crossings.size(); i += 2)
        {
            Crossing entry = crossings.get(i);
            Crossing exit = crossings.get(i+1);
            stay += exit.getTimestamp() - entry.getTimestamp();
        }
        return stay;
    }

    private List<Crossing> ignoreOvernight(List<Crossing> crossings) {
        int lastIndex = crossings.size() - 1;

        if (crossings.get(0).getType().equals("exit")) {
            crossings = crossings.subList(1, lastIndex);
        }
        if (crossings.get(lastIndex).getType().equals("entry")) {
            crossings = crossings.subList(0, lastIndex - 1);
        }
        return crossings;
    }

    private void setTimeTo(int hours, int minutes, int seconds)
    {
        DateTime dt = new DateTime(2018, 12, 10, hours, minutes, seconds, 0);
        DateTimeUtils.setCurrentMillisFixed(dt.getMillis());
    }

    public static void main(String args[]) throws Exception
    {

        NewCharger nc = new NewCharger();

        CongestionChargeSystem ccs = new CongestionChargeSystem();
        ccs.setCharger(new NewCharger());
        Vehicle vehicle = Vehicle.withRegistration("A123 XYZ");

        System.out.println(DateTimeUtils.currentTimeMillis());
        nc.setTimeTo(16, 0, 0);
        System.out.println(DateTimeUtils.currentTimeMillis());

        /*
        //DateTime dt = new DateTime(0,0,0,0,0,0,0);
        DateTimeUtils.setCurrentMillisFixed(0);
        ccs.vehicleEnteringZone(vehicle);
        DateTimeUtils.setCurrentMillisFixed(100);
        ccs.vehicleLeavingZone(vehicle);
        DateTimeUtils.setCurrentMillisFixed(14400100);
        ccs.vehicleEnteringZone(vehicle);
        DateTimeUtils.setCurrentMillisFixed(14400200);
        ccs.vehicleLeavingZone(vehicle);*/
        /*
        ccs.vehicleEnteringZone(vehicle);
        Thread.sleep(100);
        ccs.vehicleLeavingZone(vehicle);
        Thread.sleep(100);
        ccs.vehicleEnteringZone(vehicle);
        Thread.sleep(100);
        ccs.vehicleLeavingZone(vehicle);
        DateTimeUtils.setCurrentMillisOffset(14400000);
        ccs.vehicleEnteringZone(vehicle);
        Thread.sleep(100);
        ccs.vehicleLeavingZone(vehicle);
        DateTimeUtils.setCurrentMillisOffset(14400000);
        */

        ccs.calculateCharges();
    }
}