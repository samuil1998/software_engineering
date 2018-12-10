package com.trafficmon;

import java.math.BigDecimal;
import java.util.*;
import org.joda.time.*;

public class ChargeCalculator implements Calculator {

    private final long FOURHOURS = 14400000;
    private List<Crossing> crossings;

    public BigDecimal calculateCharge(List<Crossing> vehicleCrossings) {

        BigDecimal charge = new BigDecimal(0);
        if (vehicleCrossings.size() < 2) {
            return charge;
        }

        this.crossings = vehicleCrossings;
        //this.crossings = ignoreOvernight(vehicleCrossings);

        if (timeInZone() > FOURHOURS) {
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

    private long timeInZone() {
        long stay = 0;

        for(int i = 0; i < crossings.size(); i += 2) {
            Crossing entry = crossings.get(i);
            Crossing exit = crossings.get(i+1);
            stay += exit.getTimestamp() - entry.getTimestamp();
        }
        return stay;
    }
}