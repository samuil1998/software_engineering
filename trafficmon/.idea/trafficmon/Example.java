package com.trafficmon;

public class Example {
    public static void main(String[] args) throws Exception {


        CongestionChargeSystem congestionChargeSystem = new CongestionChargeSystem();

        /*
        //penalty notice expected (depends the credit)
        for (int i = 0; i < 2000; i++)
        {
            congestionChargeSystem.vehicleEnteringZone(Vehicle.withRegistration("A123 XYZ"));
            Thread.sleep(1);
            congestionChargeSystem.vehicleLeavingZone(Vehicle.withRegistration("A123 XYZ"));
            Thread.sleep(1);
        }

        congestionChargeSystem.calculateCharges();
        */

        congestionChargeSystem.vehicleEnteringZone(Vehicle.withRegistration("A123 XYZ"));
        delaySeconds(1);
        congestionChargeSystem.vehicleEnteringZone(Vehicle.withRegistration("J091 4PY"));
        delaySeconds(1);
        congestionChargeSystem.vehicleLeavingZone(Vehicle.withRegistration("A123 XYZ"));
        delaySeconds(1);
        congestionChargeSystem.vehicleLeavingZone(Vehicle.withRegistration("J091 4PY"));
        delaySeconds(1);
        congestionChargeSystem.vehicleEnteringZone(Vehicle.withRegistration("J091 4PY"));

        congestionChargeSystem.calculateCharges();

    }
    private static void delayMinutes(int mins) throws InterruptedException {
        delaySeconds(mins * 60);
    }
    private static void delaySeconds(int secs) throws InterruptedException {
        Thread.sleep(secs * 1000);
    }
}
