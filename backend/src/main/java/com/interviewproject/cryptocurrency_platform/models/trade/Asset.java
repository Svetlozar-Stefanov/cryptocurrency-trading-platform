package com.interviewproject.cryptocurrency_platform.models.trade;

import com.interviewproject.cryptocurrency_platform.repositories.trade.TransactionRepository;

import java.math.BigDecimal;

public class Asset {
    private Long userId;
    private String symbol;
    private BigDecimal quantity;
    private BigDecimal avg_price;

    public Asset(Long userId, String symbol, BigDecimal quantity, BigDecimal avg_price) {
        this.userId = userId;
        this.symbol = symbol;
        this.quantity = quantity;
        this.avg_price = avg_price;
    }

    public BigDecimal getAvg_price() {
        return avg_price;
    }

    public void setAvg_price(BigDecimal avg_price) {
        this.avg_price = avg_price;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void addQuantity(BigDecimal quantity) {
        this.quantity = this.quantity.add(quantity);
    }

    public void subtractQuantity(BigDecimal quantity) {
        this.quantity = this.quantity.subtract(quantity);
    }

    public void updateAvgPrice(BigDecimal new_price) {
        this.avg_price = (this.avg_price.add(new_price)).divide(new BigDecimal(2));
    }
}
