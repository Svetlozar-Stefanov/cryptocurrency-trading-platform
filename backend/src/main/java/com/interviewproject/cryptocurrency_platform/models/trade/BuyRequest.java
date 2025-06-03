package com.interviewproject.cryptocurrency_platform.models.trade;

import java.math.BigDecimal;

public record BuyRequest(String symbol, BigDecimal price, BigDecimal quantity) {
}
