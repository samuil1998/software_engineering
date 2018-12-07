package com.trafficmon;

import java.util.List;
import java.util.Map;
import java.math.BigDecimal;

public class OldCharger implements Charger {

    public static final BigDecimal CHARGE_RATE_POUNDS_PER_MINUTE = new BigDecimal(0.05);

    public BigDecimal calculateCharge(List<Crossing> crossings)
    {
        BigDecimal charge = new BigDecimal(0);

        Crossing lastEvent = crossings.get(0);

        for (Crossing crossing : crossings.subList(1, crossings.size())) {

            if (crossing.getType().equals("exit")) {
                charge = charge.add(
                        new BigDecimal(minutesBetween(lastEvent.getTimestamp(), crossing.getTimestamp()))
                                .multiply(CHARGE_RATE_POUNDS_PER_MINUTE));
            }

            lastEvent = crossing;
        }

        return charge;
    }

    private int minutesBetween(long startTimeMs, long endTimeMs) {
        return (int) Math.ceil((endTimeMs - startTimeMs) / (1000.0 * 60.0));
    }
}
