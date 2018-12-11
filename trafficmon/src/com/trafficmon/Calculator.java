package com.trafficmon;

import java.math.BigDecimal;
import java.util.List;

public interface Calculator {

    BigDecimal calculateCharge(List<Crossing> crossings);

}
