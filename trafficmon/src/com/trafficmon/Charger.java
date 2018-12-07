package com.trafficmon;

import java.math.BigDecimal;
import java.util.*;

public interface Charger {

    BigDecimal calculateCharge(List<Crossing> crossings);

    void setPenaltiesService(PenaltiesService penaltiesService);

    void setAccountsService(AccountsService accountsService);

}
