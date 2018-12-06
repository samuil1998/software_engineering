package com.trafficmon;

import java.math.BigDecimal;
import java.util.*;

public interface Charger {

    public void charge(Map<Vehicle, List<ZoneBoundaryCrossing>> vehicleCrossings);

    public void setPenaltiesService(PenaltiesService penaltiesService);
    public void setAccountsService(AccountsService accountsService);


}
