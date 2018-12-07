package com.trafficmon;

import java.util.*;

public interface Charger {

    public void charge(Log vehicleCrossings);

    public void setPenaltiesService(PenaltiesService penaltiesService);

    public void setAccountsService(AccountsService accountsService);

}
