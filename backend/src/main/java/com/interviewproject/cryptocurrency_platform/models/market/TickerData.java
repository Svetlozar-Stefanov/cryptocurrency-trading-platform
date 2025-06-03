package com.interviewproject.cryptocurrency_platform.models.market;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TickerData {
    public String symbol;
    public double last;
}