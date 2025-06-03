package com.interviewproject.cryptocurrency_platform.models.trade;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Transaction {
    private Long id;
    private Long userId;
    private String symbol;
    private String type;
    private BigDecimal price;
    private BigDecimal quantity;
    private Timestamp timestamp;

    public Transaction() {}
    public Transaction(Long userId, String symbol, String type, BigDecimal price, BigDecimal quantity, Timestamp timestamp) {
        this.userId = userId;
        this.symbol = symbol;
        this.type = type;
        this.price = price;
        this.quantity = quantity;
        this.timestamp = timestamp;
    }
    public Transaction(Long id, Long userId, String symbol, String type, BigDecimal price, BigDecimal quantity, Timestamp timestamp) {
        this.id = id;
        this.userId = userId;
        this.symbol = symbol;
        this.type = type;
        this.price = price;
        this.quantity = quantity;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
