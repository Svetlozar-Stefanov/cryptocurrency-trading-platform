package com.interviewproject.cryptocurrency_platform.models.trade;

import java.math.BigDecimal;

public record SellRequest(String symbol, BigDecimal price, BigDecimal quantity) {
}
