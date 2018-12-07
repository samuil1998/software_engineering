package com.trafficmon;

import java.util.List;
import java.util.Map;
import java.math.BigDecimal;

public class OldCharger implements Charger {

    public static final BigDecimal CHARGE_RATE_POUNDS_PER_MINUTE = new BigDecimal(0.05);

    private PenaltiesService penaltiesService;
    private AccountsService accountsService;

    @Override
    public void setPenaltiesService(PenaltiesService penaltiesService) {
        this.penaltiesService = penaltiesService;
    }

    @Override
    public void setAccountsService(AccountsService accountsService) {
        this.accountsService = accountsService;
    }

    @Override
    public void charge(Log mapping) {
        for (Vehicle vehicle : mapping.getVehicles())
        {
            List<Crossing> crossings = mapping.getCrossingsFor(vehicle);

            if (!mapping.isOrdered(vehicle)) {
                penaltiesService.triggerInvestigationInto(vehicle);
            } else {

                BigDecimal charge = calculateChargeForTimeInZone(crossings);

                try {
                    accountsService.accountFor(vehicle).deduct(charge);
                } catch (InsufficientCreditException ice) {
                    penaltiesService.issuePenaltyNotice(vehicle, charge);
                } catch (AccountNotRegisteredException e) {
                    penaltiesService.issuePenaltyNotice(vehicle, charge);
                }
            }
        }
    }

    private BigDecimal calculateChargeForTimeInZone(List<Crossing> crossings) {

        BigDecimal charge = new BigDecimal(0);

        Crossing lastEvent = crossings.get(0);

        for (Crossing crossing : crossings.subList(1, crossings.size())) {

            if (crossing.isExit()) {
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
