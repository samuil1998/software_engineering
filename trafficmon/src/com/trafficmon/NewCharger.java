package com.trafficmon;

import java.math.BigDecimal;
import java.util.List;

public class NewCharger {
    public BigDecimal calculateCharge(List<Crossing> crossings)
    {
        BigDecimal charge = new BigDecimal(0);

        Crossing firstEntryCrossing = crossings.get(0);
        Crossing lastCrossing = crossings.get(crossings.size() - 1);
        if (hasStayedTooLong(firstEntryCrossing, lastCrossing)) {
            charge = charge.add(new BigDecimal(12.0));
        }
        else {
            if (isEarlyEntry(firstEntryCrossing)) {
                charge = charge.add(new BigDecimal(6.0));
            }
            charge = charge.add(new BigDecimal(4.0));
        }
        return charge;
    }

    private double convertToHours(long time) {
        return (double) time / (1000 * 60 * 60);
    }

    private double hoursBetween(Crossing firstCrossing, Crossing lastCrossing) {
        double startTime = convertToHours(firstCrossing.getTimestamp());
        double endTime = convertToHours(lastCrossing.getTimestamp());
        return endTime - startTime;
    }

    private boolean isEarlyEntry(Crossing entryToBeChecked) {
        double convertedTimeOfEntry = convertToHours(entryToBeChecked.getTimestamp());
        if (convertedTimeOfEntry <= 14.0) {
            return true;
        }
        return false;
    }

    private boolean hasStayedTooLong(Crossing firstCrossing, Crossing lastCrossing) {
        if (hoursBetween(firstCrossing, lastCrossing) > 4.0) {
            return true;
        }
        return false;
    }
}
